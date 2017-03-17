package com.klymenko.service;

import com.klymenko.core.VendingMachineCoreService;
import com.klymenko.exception.InsufficientCoinageException;
import com.klymenko.exception.UnchangeableCoinageException;
import com.klymenko.model.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Unicorn on 10.03.2017.
 */
@Service
public class VendingMachineService {

    private VendingMachineCoreService vendingMachineCoreService;

    private PropertiesService propertiesService;

    public final int cacheArraySize = 10_000;

    private TreeSet<List<Integer>> [] cachedPossibleCombinations = new TreeSet[cacheArraySize];;


    @Autowired
    public VendingMachineService(VendingMachineCoreService vendingMachineCoreService, PropertiesService propertiesService) {
        this.vendingMachineCoreService = vendingMachineCoreService;
        this.propertiesService = propertiesService;
    }


    public Collection<Coin> getOptimalChangeFor(int pence) {
        updateCachedCombinations(pence);
        return cachedPossibleCombinations[pence].stream()
                                                .findFirst()
                                                .orElseThrow(() -> new UnchangeableCoinageException("Unchangeable coinage!"))
                                                .stream()
                                                .map(Coin::valueOf)
                                                .collect(Collectors.toList());
    }

    public synchronized Collection<Coin> getChangeFor(int pence) {
        updateCachedCombinations(pence);
        if(cachedPossibleCombinations[pence] != null) {
            Properties availableCoinsProperties = propertiesService.loadProperties();
            for (Collection<Integer> combination : cachedPossibleCombinations[pence]) {
                Properties availableCoinsPropertiesCopy = (Properties) availableCoinsProperties.clone();
                boolean isAccessibleCombination = true;
                for (Integer combinationValue : combination) {
                    String combinationValueStr = String.valueOf(combinationValue);
                    String coinAmountStringified = (String) availableCoinsPropertiesCopy.get(combinationValueStr);
                    if (coinAmountStringified != null && Integer.valueOf(coinAmountStringified) > 0) {
                        int coinAmount = Integer.valueOf(coinAmountStringified);
                        availableCoinsPropertiesCopy.put(combinationValueStr, String.valueOf(coinAmount - 1));
                    } else {
                        isAccessibleCombination = false;
                    }
                }
                if (isAccessibleCombination) {
                    propertiesService.updateProperties(availableCoinsPropertiesCopy);
                    return combination.stream()
                            .map(Coin::valueOf)
                            .collect(Collectors.toList());
                }
            }
            throw new InsufficientCoinageException("Insufficient coinage!");
        } else {
            throw new UnchangeableCoinageException("There's no coin combinations!");
        }
    }

    private synchronized void updateCachedCombinations(int pence) {
        if(pence < 0) {
            throw new UnchangeableCoinageException("Pence amount couldn't be negative!");
        }
        if(pence >= cachedPossibleCombinations.length) {
            throw new UnchangeableCoinageException("Pence amount couldn't be larger than "+ cacheArraySize +"!");
        }
        if(cachedPossibleCombinations[pence] == null) {
            cachedPossibleCombinations[pence] = vendingMachineCoreService.possibleCombinations(pence);
        }
    }
}

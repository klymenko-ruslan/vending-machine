package com.klymenko.service;

import com.klymenko.core.VendingMachineCoreService;
import com.klymenko.exception.InsufficientCoinageException;
import com.klymenko.exception.UnchangeableCoinageException;
import com.klymenko.model.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Unicorn on 10.03.2017.
 */
@Service
public class VendingMachineService {

    @Autowired
    private VendingMachineCoreService vendingMachineCoreService;

    public Collection<Coin> getOptimalChangeFor(int pence) {
        return vendingMachineCoreService.possibleCombinations(pence)
                                        .stream()
                                        .findFirst()
                                        .orElseThrow(() -> new UnchangeableCoinageException("Unchangeable coinage!"))
                                        .stream()
                                        .map(Coin::valueOf)
                                        .collect(Collectors.toList());
    }

    public synchronized Collection<Coin> getChangeFor(int pence) {
        Properties availableCoinsProperties = loadProperties();
        outer:
        for(Collection<Integer> combination : vendingMachineCoreService.possibleCombinations(pence)) {
            Properties availableCoinsPropertiesCopy = (Properties) availableCoinsProperties.clone();
            for(Integer combinationValue : combination) {
                String combinationValueStr = String.valueOf(combinationValue);
                if(availableCoinsPropertiesCopy.get(combinationValueStr) != null &&
                        Integer.valueOf((String)availableCoinsPropertiesCopy.get(combinationValueStr)) >= 1) {
                    String newValue = String.valueOf(Integer.valueOf((String)availableCoinsPropertiesCopy.get(combinationValueStr)) - 1);
                    availableCoinsPropertiesCopy.put(combinationValueStr, newValue);
                } else {
                    continue outer;
                }
            }
            updateProperties(availableCoinsPropertiesCopy);
            return combination.stream()
                              .map(Coin::valueOf)
                              .collect(Collectors.toList());
        }
        throw new InsufficientCoinageException("Insufficient coinage!");
    }

    private void updateProperties(Properties availableCoinsPropertiesCopy) {
        String filePath = this.getClass().getClassLoader().getResource("coin-inventory.properties").getFile();
        try(FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            availableCoinsPropertiesCopy.store(fos, "");
        } catch(IOException e) {
            throw new RuntimeException();
        }
    }

    private Properties loadProperties() {
        try(InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("coin-inventory.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

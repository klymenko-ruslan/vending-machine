package com.klymenko.service;

import com.klymenko.exception.InsufficientCoinageException;
import com.klymenko.exception.UnchangeableCoinageException;
import com.klymenko.model.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Created by Unicorn on 10.03.2017.
 */
@Service
public class VendingMachineService {

    private PropertiesService propertiesService;

    @Autowired
    public VendingMachineService(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }


    public Collection<Coin> getOptimalChangeFor(int pence) {
        if(pence <= 0) {
            throw new UnchangeableCoinageException("Pence amount should be positive!");
        }
        Collection<Coin> optimalChange = new ArrayList<>();
        for(Coin coin : Coin.values()) {
            while(pence - coin.getDenomination() >= 0) {
                pence -= coin.getDenomination();
                optimalChange.add(coin);
            }
        }
        return optimalChange;
    }

    public synchronized Collection<Coin> getChangeFor(int pence) {
        if(pence <= 0) {
            throw new UnchangeableCoinageException("Pence amount should be positive!");
        }
        Properties availableCoinsProperties = (Properties) propertiesService.loadProperties().clone();
        Collection<Coin> change = new ArrayList<>();
        for(Coin coin : Coin.values()) {
            String coinValueStr = String.valueOf(coin.getDenomination());
            String coinsAmountStr = (String) availableCoinsProperties.get(coinValueStr);
            int coinsAmount = Integer.valueOf(coinsAmountStr);
            while(coinsAmount > 0 && pence - coin.getDenomination() >= 0) {
                pence -= coin.getDenomination();
                coinsAmount--;
                change.add(coin);
            }
            availableCoinsProperties.put(coinValueStr, String.valueOf(coinsAmount));
        }
        if(pence > 0) {
            throw new InsufficientCoinageException("Insufficient coinage!");
        }
        propertiesService.updateProperties(availableCoinsProperties);
        return change;
    }
}

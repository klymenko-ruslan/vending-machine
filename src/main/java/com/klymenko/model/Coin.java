package com.klymenko.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Unicorn on 10.03.2017.
 */
public enum Coin {
    ONE_POUND(100),
    FIFTY_PENCE(50),
    TWENTY_PENCE(20),
    TEN_PENCE(10),
    FIVE_PENCE(5),
    TWO_PENCE(2),
    ONE_PENNY(1);

    private int denomination;

    private static Map<Integer, Coin> coinByInt = new HashMap<>();
    static {
        for(Coin coin : Coin.values()) {
            coinByInt.put(coin.getDenomination(), coin);
        }
    }
    Coin(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }

    public static Coin valueOf(int val) {
        return coinByInt.get(val);
    }
}

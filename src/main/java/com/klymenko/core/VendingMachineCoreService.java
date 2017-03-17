package com.klymenko.core;

import com.klymenko.model.Coin;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Unicorn on 10.03.2017.
 */
@Service
public class VendingMachineCoreService {

    public TreeSet<List<Integer>> possibleCombinations(int totalAmount) {
        TreeSet<List<Integer>> possibleCombinations = new TreeSet<>((thisObj, thatObj) -> Integer.compare(thisObj.size(), thatObj.size()));
        calculatePossibleCombinations(Coin.values(), new int[Coin.values().length], 0, totalAmount, possibleCombinations);
        return possibleCombinations;
    }

    private void calculatePossibleCombinations(Coin[] coins,
                                               int[] counts,
                                               int startIndex,
                                               int totalAmount,
                                               TreeSet<List<Integer>> possibleCombinations) {
        if(startIndex >= coins.length) {
            ArrayList<Integer> combination = new ArrayList<>();
            for(int i=0; i < coins.length; i++) {
                for(int j = 0; j < counts[i]; j++) {
                    combination.add(coins[i].getDenomination());
                }
            }
            if(!combination.isEmpty()) {
                possibleCombinations.add(combination);
            }
        } else if(startIndex == coins.length - 1) {
            if(totalAmount % coins[startIndex].getDenomination() == 0) {
                counts[startIndex] = totalAmount / coins[startIndex].getDenomination();
                calculatePossibleCombinations(coins, counts, startIndex + 1, 0, possibleCombinations);
            }
        } else {
            for(int i = 0; i <= totalAmount / coins[startIndex].getDenomination(); i++) {
                counts[startIndex] = i;
                calculatePossibleCombinations(coins, counts, startIndex + 1, totalAmount-coins[startIndex].getDenomination()*i, possibleCombinations);
            }
        }
    }
}

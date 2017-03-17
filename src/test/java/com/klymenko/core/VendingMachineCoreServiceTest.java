package com.klymenko.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Unicorn on 16.03.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VendingMachineCoreServiceTest {

    @Autowired
    private VendingMachineCoreService vendingMachineCoreService;

    @Test
    public void testPossibleCombinations() {
        Assert.assertEquals(new TreeSet<List<Integer>>((thisObj, thatObj) -> Integer.compare(thisObj.size(), thatObj.size())){
            {
                add(new ArrayList<Integer>() {
                    {
                        add(1);
                    }
                });
            }
        },vendingMachineCoreService.possibleCombinations(1));

        Assert.assertEquals(new TreeSet<List<Integer>>((thisObj, thatObj) -> Integer.compare(thisObj.size(), thatObj.size())){
            {
                add(new ArrayList<Integer>() {
                    {
                        add(2);
                    }
                });
                add(new ArrayList<Integer>() {
                    {
                        add(1);
                        add(1);
                    }
                });
            }
        },vendingMachineCoreService.possibleCombinations(2));

        Assert.assertEquals(new TreeSet<List<Integer>>((thisObj, thatObj) -> Integer.compare(thisObj.size(), thatObj.size())){
            {
                add(new ArrayList<Integer>() {
                    {
                        add(5);
                    }
                });
                add(new ArrayList<Integer>() {
                    {
                        add(2);
                        add(2);
                        add(1);
                    }
                });
                add(new ArrayList<Integer>() {
                    {
                        add(2);
                        add(1);
                        add(1);
                        add(1);
                    }
                });
                add(new ArrayList<Integer>() {
                    {
                        add(1);
                        add(1);
                        add(1);
                        add(1);
                        add(1);
                    }
                });
            }
        },vendingMachineCoreService.possibleCombinations(5));
    }

    @Test
    public void testPossibleCombinationsNull() {
        Assert.assertEquals(new TreeSet<List<Integer>>(), vendingMachineCoreService.possibleCombinations(0));
    }

    @Test
    public void testPossibleCombinationsNegative() {
        Assert.assertEquals(new TreeSet<List<Integer>>(), vendingMachineCoreService.possibleCombinations(-1));
    }

    @Test(expected = OutOfMemoryError.class)
    public void testPossibleCombinationsLargeValue() {
        Assert.assertEquals(new TreeSet<List<Integer>>(), vendingMachineCoreService.possibleCombinations(Integer.MAX_VALUE));
    }
}

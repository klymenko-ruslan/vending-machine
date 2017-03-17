package com.klymenko.service;

import com.klymenko.VendingMachineApplication;
import com.klymenko.core.VendingMachineCoreService;
import com.klymenko.exception.InsufficientCoinageException;
import com.klymenko.exception.UnchangeableCoinageException;
import com.klymenko.model.Coin;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

/**
 * Created by Unicorn on 16.03.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {VendingMachineApplication.class, VendingMachineServiceTest.class})
public class VendingMachineServiceTest {

    @Autowired
    private VendingMachineService vendingMachineService;

    @Autowired
    private VendingMachineCoreService vendingMachineCoreService;

    @Autowired
    private PropertiesService propertiesService;

    @Bean
    @Primary
    public VendingMachineCoreService vendingMachineCoreService() {
        return Mockito.mock(VendingMachineCoreService.class);
    }

    @Bean
    @Primary
    public PropertiesService propertiesService() {
        return Mockito.mock(PropertiesService.class);
    }

    @Test
    public void testOptimalChangeFor() {
        Mockito.when(vendingMachineCoreService.possibleCombinations(1))
                .thenReturn(new TreeSet<List<Integer>>((thisObj, thatObj) -> Integer.compare(thisObj.size(), thatObj.size())){
                    {
                        add(new ArrayList<Integer>(){
                            {
                                add(1);
                            }
                        });
                        add(new ArrayList<Integer>(){
                            {
                                add(2);
                            }
                        });
                        add(new ArrayList<Integer>(){
                            {
                                add(5);
                            }
                        });
                    }
                });

        List<Coin> onePenny = new ArrayList<Coin>() {
                                                        {
                                                            add(Coin.ONE_PENNY);
                                                        }
                                                    };
        Assert.assertEquals(onePenny, vendingMachineService.getOptimalChangeFor(1));
    }

    @Test(expected = UnchangeableCoinageException.class)
    public void testOptimalChangeForEmpty() {
        Mockito.when(vendingMachineCoreService.possibleCombinations(2))
                .thenReturn(new TreeSet<List<Integer>>((thisObj, thatObj) -> Integer.compare(thisObj.size(), thatObj.size())));
        vendingMachineService.getOptimalChangeFor(2);
    }

    @Test(expected = UnchangeableCoinageException.class)
    public void testOptimalChangeForOvercached() {
        vendingMachineService.getOptimalChangeFor(vendingMachineService.cacheArraySize);
    }

    @Test
    public void testChangeFor() {
        Mockito.when(vendingMachineCoreService.possibleCombinations(3))
               .thenReturn(new TreeSet<List<Integer>>((thisObj, thatObj) -> Integer.compare(thisObj.size(), thatObj.size())){
                   {
                       add(new ArrayList<Integer>(){
                           {
                               add(2);
                               add(1);
                           }
                       });
                   }
               });
        Mockito.when(propertiesService.loadProperties())
               .thenReturn(getProperties());
        ArrayList<Coin> coins = new ArrayList<Coin>(){
            {
                add(Coin.TWO_PENCE);
                add(Coin.ONE_PENNY);
            }
        };
        Assert.assertEquals(coins, vendingMachineService.getChangeFor(3));
    }

    @Test
    public void testChangeForSecondCombination() {
        Mockito.when(vendingMachineCoreService.possibleCombinations(4))
                .thenReturn(new TreeSet<List<Integer>>((thisObj, thatObj) -> Integer.compare(thisObj.size(), thatObj.size())){
                    {
                        add(new ArrayList<Integer>(){
                            {
                                add(2);
                                add(2);
                            }
                        });
                        add(new ArrayList<Integer>(){
                            {
                                add(1);
                                add(1);
                                add(1);
                                add(1);
                            }
                        });
                    }
                });
        Mockito.when(propertiesService.loadProperties())
                .thenReturn(getProperties());
        ArrayList<Coin> coins = new ArrayList<Coin>(){
            {
                add(Coin.ONE_PENNY);
                add(Coin.ONE_PENNY);
                add(Coin.ONE_PENNY);
                add(Coin.ONE_PENNY);
            }
        };
        Assert.assertEquals(coins, vendingMachineService.getChangeFor(4));
    }

    @Test(expected = InsufficientCoinageException.class)
    public void testChangeForInsufficientCoinage() {
        Mockito.when(vendingMachineCoreService.possibleCombinations(300))
                .thenReturn(new TreeSet<List<Integer>>((thisObj, thatObj) -> Integer.compare(thisObj.size(), thatObj.size())));
        Mockito.when(propertiesService.loadProperties())
                .thenReturn(getProperties());
        vendingMachineService.getChangeFor(300);
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put("100", "1");
        properties.put("50", "1");
        properties.put("20", "1");
        properties.put("10", "1");
        properties.put("5", "1");
        properties.put("2", "1");
        properties.put("1", "4");
        return properties;
    }
}

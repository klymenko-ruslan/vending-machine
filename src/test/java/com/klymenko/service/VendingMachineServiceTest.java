package com.klymenko.service;

import com.klymenko.VendingMachineApplication;
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
    private PropertiesService propertiesService;

    @Bean
    @Primary
    public PropertiesService propertiesService() {
        return Mockito.mock(PropertiesService.class);
    }

    @Test
    public void testOptimalChangeFor() {

        List<Coin> onePenny = new ArrayList<Coin>() {
                                                        {
                                                            add(Coin.ONE_PENNY);
                                                        }
                                                    };
        Assert.assertEquals(onePenny, vendingMachineService.getOptimalChangeFor(1));
    }

    @Test(expected = UnchangeableCoinageException.class)
    public void testOptimalChangeForEmpty() {
        vendingMachineService.getOptimalChangeFor(0);
    }


    @Test
    public void testChangeFor() {
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
        Mockito.when(propertiesService.loadProperties())
                .thenReturn(getProperties());
        ArrayList<Coin> coins = new ArrayList<Coin>(){
            {
                add(Coin.TWO_PENCE);
                add(Coin.ONE_PENNY);
                add(Coin.ONE_PENNY);
            }
        };
        Assert.assertEquals(coins, vendingMachineService.getChangeFor(4));
    }

    @Test(expected = InsufficientCoinageException.class)
    public void testChangeForInsufficientCoinage() {
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

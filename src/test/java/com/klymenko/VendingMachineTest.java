package com.klymenko;

import com.klymenko.exception.InsufficientCoinageException;
import com.klymenko.exception.UnchangeableCoinageException;
import com.klymenko.model.Coin;
import com.klymenko.service.VendingMachineService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VendingMachineTest {

	@Autowired
	private VendingMachineService vendingMachineService;


	@Test
	public void testGetChangeForReadsWritesToFilePound() {
		for(Coin coin : Coin.values()) {
			Assert.assertEquals(vendingMachineService.getChangeFor(coin.getDenomination()), Arrays.asList(new Coin[]{coin}));

			try {
				Assert.assertEquals(vendingMachineService.getChangeFor(coin.getDenomination()), Arrays.asList(new Coin[]{coin}));
				Assert.fail("InsufficientCoinageException should be thrown");
			} catch (InsufficientCoinageException e) {
			}
		}
	}

	@Test
	public void testGetOptimalChangeForPenny() {
		Assert.assertEquals(vendingMachineService.getOptimalChangeFor(1), Arrays.asList(new Coin[]{Coin.ONE_PENNY}));
	}

	@Test
	public void testGetOptimalChangeForTwoPence() {
		Assert.assertEquals(vendingMachineService.getOptimalChangeFor(2), Arrays.asList(new Coin[]{Coin.TWO_PENCE}));
	}

	@Test
	public void testGetOptimalChangeForThreePence() {
		Assert.assertEquals(vendingMachineService.getOptimalChangeFor(3), Arrays.asList(new Coin[]{Coin.TWO_PENCE, Coin.ONE_PENNY}));
	}

	@Test
	public void testGetOptimalChangeForNinePences() {
		Assert.assertEquals(vendingMachineService.getOptimalChangeFor(9), Arrays.asList(new Coin[]{Coin.FIVE_PENCE, Coin.TWO_PENCE, Coin.TWO_PENCE}));
	}

	@Test
	public void testGetOptimalChangeForNineteenPences() {
		Assert.assertEquals(vendingMachineService.getOptimalChangeFor(19), Arrays.asList(new Coin[]{Coin.TEN_PENCE, Coin.FIVE_PENCE, Coin.TWO_PENCE, Coin.TWO_PENCE}));
	}

	@Test
	public void testGetOptimalChangeForOneHundredThreePences() {
		Assert.assertEquals(vendingMachineService.getOptimalChangeFor(103), Arrays.asList(new Coin[]{Coin.ONE_POUND, Coin.TWO_PENCE, Coin.ONE_PENNY}));
	}

	@Test(expected = UnchangeableCoinageException.class)
	public void testGetOptimalChangeForNull() {
		vendingMachineService.getOptimalChangeFor(0);
	}

	@Test(expected = UnchangeableCoinageException.class)
	public void testGetOptimalChangeForNegative() {
		vendingMachineService.getOptimalChangeFor(-1);
	}
}

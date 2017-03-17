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

@RunWith(SpringRunner.class)
@SpringBootTest
public class VendingMachineTest {

	@Autowired
	private VendingMachineService vendingMachineService;


	@Test
	public void testGetChangeForReadsWritesToFile() {
		Assert.assertEquals(vendingMachineService.getChangeFor(100), new ArrayList<Coin>() {
			{
				add(Coin.ONE_POUND);
			}
		});
		Assert.assertEquals(vendingMachineService.getChangeFor(50), new ArrayList<Coin>() {
			{
				add(Coin.FIFTY_PENCE);
			}
		});
		Assert.assertEquals(vendingMachineService.getChangeFor(20), new ArrayList<Coin>() {
			{
				add(Coin.TWENTY_PENCE);
			}
		});
		Assert.assertEquals(vendingMachineService.getChangeFor(10), new ArrayList<Coin>() {
			{
				add(Coin.TEN_PENCE);
			}
		});
		Assert.assertEquals(vendingMachineService.getChangeFor(5), new ArrayList<Coin>() {
			{
				add(Coin.FIVE_PENCE);
			}
		});
		Assert.assertEquals(vendingMachineService.getChangeFor(2), new ArrayList<Coin>() {
			{
				add(Coin.TWO_PENCE);
			}
		});
		Assert.assertEquals(vendingMachineService.getChangeFor(1), new ArrayList<Coin>() {
			{
				add(Coin.ONE_PENNY);
			}
		});

		try {
			Assert.assertEquals(vendingMachineService.getChangeFor(100), new ArrayList<Coin>() {
				{
					add(Coin.ONE_POUND);
				}
			});
			Assert.fail("InsufficientCoinageException should be thrown");
		} catch(InsufficientCoinageException e) {}
		try {
			Assert.assertEquals(vendingMachineService.getChangeFor(50), new ArrayList<Coin>() {
				{
					add(Coin.FIFTY_PENCE);
				}
			});
			Assert.fail("InsufficientCoinageException should be thrown");
		} catch(InsufficientCoinageException e) {}
		try {
			Assert.assertEquals(vendingMachineService.getChangeFor(20), new ArrayList<Coin>() {
				{
					add(Coin.TWENTY_PENCE);
				}
			});
			Assert.fail("InsufficientCoinageException should be thrown");
		} catch(InsufficientCoinageException e) {}
		try {
			Assert.assertEquals(vendingMachineService.getChangeFor(10), new ArrayList<Coin>() {
				{
					add(Coin.TEN_PENCE);
				}
			});
			Assert.fail("InsufficientCoinageException should be thrown");
		} catch(InsufficientCoinageException e) {}
		try {
			Assert.assertEquals(vendingMachineService.getChangeFor(5), new ArrayList<Coin>() {
				{
					add(Coin.FIVE_PENCE);
				}
			});
			Assert.fail("InsufficientCoinageException should be thrown");
		} catch(InsufficientCoinageException e) {}
		try {
			Assert.assertEquals(vendingMachineService.getChangeFor(2), new ArrayList<Coin>() {
				{
					add(Coin.TWO_PENCE);
				}
			});
			Assert.fail("InsufficientCoinageException should be thrown");
		} catch(InsufficientCoinageException e) {}
		try {
			Assert.assertEquals(vendingMachineService.getChangeFor(1), new ArrayList<Coin>() {
				{
					add(Coin.ONE_PENNY);
				}
			});
			Assert.fail("InsufficientCoinageException should be thrown");
		} catch(InsufficientCoinageException e) {}
	}

	@Test
	public void testGetOptimalChangeFor() {
		Assert.assertEquals(vendingMachineService.getOptimalChangeFor(1), new ArrayList<Coin>() {
			{
				add(Coin.ONE_PENNY);
			}
		});
		Assert.assertEquals(vendingMachineService.getOptimalChangeFor(2), new ArrayList<Coin>() {
			{
				add(Coin.TWO_PENCE);
			}
		});
		Assert.assertEquals(vendingMachineService.getOptimalChangeFor(3), new ArrayList<Coin>() {
			{
				add(Coin.TWO_PENCE);
				add(Coin.ONE_PENNY);
			}
		});
		Assert.assertEquals(vendingMachineService.getOptimalChangeFor(9), new ArrayList<Coin>() {
			{
				add(Coin.FIVE_PENCE);
				add(Coin.TWO_PENCE);
				add(Coin.TWO_PENCE);
			}
		});
		Assert.assertEquals(vendingMachineService.getOptimalChangeFor(19), new ArrayList<Coin>() {
			{
				add(Coin.TEN_PENCE);
				add(Coin.FIVE_PENCE);
				add(Coin.TWO_PENCE);
				add(Coin.TWO_PENCE);
			}
		});
		Assert.assertEquals(vendingMachineService.getOptimalChangeFor(103), new ArrayList<Coin>() {
			{
				add(Coin.ONE_POUND);
				add(Coin.TWO_PENCE);
				add(Coin.ONE_PENNY);
			}
		});
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

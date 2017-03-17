package com.klymenko.rest;

import com.klymenko.exception.InsufficientCoinageException;
import com.klymenko.model.Coin;
import com.klymenko.service.VendingMachineService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Unicorn on 16.03.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VendingMachineRestServiceTest {

    private MockMvc mockMvc;

    @Mock
    private VendingMachineService vendingMachineService = Mockito.mock(VendingMachineService.class);

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new VendingMachineRestService(vendingMachineService))
                .build();
    }

    @Test
    public void testGetOptimalChangeFor() throws Exception {
        when(vendingMachineService.getOptimalChangeFor(1)).thenReturn(new ArrayList<Coin>(){
            {
                add(Coin.ONE_PENNY);
            }
        });

        String response = mockMvc.perform(MockMvcRequestBuilders.get(VendingMachineRestService.OPTIMAL_CHANGE_URL + "1"))
                                 .andExpect(status().isOk())
                                 .andReturn().getResponse().getContentAsString();


        Assert.assertEquals("[\"ONE_PENNY\"]", response);
    }

    @Test
    public void testGetOptimalChangeForException() throws Exception {
        when(vendingMachineService.getOptimalChangeFor(1)).thenThrow(InsufficientCoinageException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(VendingMachineRestService.OPTIMAL_CHANGE_URL + "1"))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetChangeFor() throws Exception {
        when(vendingMachineService.getChangeFor(1)).thenReturn(new ArrayList<Coin>(){
            {
                add(Coin.ONE_PENNY);
            }
        });

        String response = mockMvc.perform(MockMvcRequestBuilders.get(VendingMachineRestService.CHANGE_URL + "1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        Assert.assertEquals("[\"ONE_PENNY\"]", response);
    }

    @Test
    public void testGetChangeForException() throws Exception {
        when(vendingMachineService.getChangeFor(1)).thenThrow(InsufficientCoinageException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(VendingMachineRestService.CHANGE_URL + "1"))
                .andExpect(status().isBadRequest());
    }


}

package com.klymenko.rest;

import com.klymenko.exception.InsufficientCoinageException;
import com.klymenko.exception.UnchangeableCoinageException;
import com.klymenko.model.Coin;
import com.klymenko.service.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Unicorn on 10.03.2017.
 */
@RestController
public class VendingMachineRestService {

    @Autowired
    private VendingMachineService vendingMachineService;

    @RequestMapping("/optimal-change/{pence}")
    public Collection<Coin> getOptimalChangeFor(@PathVariable int pence) {
        return vendingMachineService.getOptimalChangeFor(pence);
    }

    @RequestMapping("/change/{pence}")
    public Collection<Coin> getChangeFor(@PathVariable int pence) {
        return vendingMachineService.getChangeFor(pence);
    }

    @ExceptionHandler({InsufficientCoinageException.class, UnchangeableCoinageException.class})
    public ResponseEntity<String> handleException(RuntimeException exception) {
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

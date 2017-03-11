package com.klymenko.exception;

/**
 * Created by Unicorn on 10.03.2017.
 */
public class InsufficientCoinageException extends RuntimeException {

    public InsufficientCoinageException(String message) {
        super(message);
    }
}

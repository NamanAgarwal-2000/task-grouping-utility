package com.naman.taskutility;

public class ValidationException
        extends RuntimeException {

    public ValidationException(
            String message) {

        super(message);
    }
}
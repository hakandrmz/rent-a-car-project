package com.turkcell.rentacar.core.exceptions;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }
}

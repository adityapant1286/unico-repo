package com.serviceapi.exceptions.checked;

public class ApplicationException extends Exception {

    public ApplicationException(String error) {
        super(error);
    }
}

package com.hospital.exception;

public class UserNotFoundException extends HospitalException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
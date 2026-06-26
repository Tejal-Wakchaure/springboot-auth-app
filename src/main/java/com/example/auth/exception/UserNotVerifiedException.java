package com.example.auth.exception;

public class UserNotVerifiedException extends RuntimeException {

    public UserNotVerifiedException() {
        super("Email verification pending");
    }
}
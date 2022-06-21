package com.example.springbootcrud.exception;

public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists(String exception) {
        super(exception);
    }
}

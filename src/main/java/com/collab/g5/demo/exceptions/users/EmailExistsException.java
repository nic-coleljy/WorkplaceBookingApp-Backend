package com.collab.g5.demo.exceptions.users;

public class EmailExistsException extends RuntimeException{
    public EmailExistsException(String s){
        super("Email address is already in use");
    }
}

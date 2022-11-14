package com.collab.g5.demo.exceptions.users;

public class InsertUserException extends RuntimeException{
    public InsertUserException(String email){
        super("email "+ email+ " already taken");
    }

}

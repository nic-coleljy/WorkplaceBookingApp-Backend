package com.collab.g5.demo.exceptions.users;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String email){
        super("Could not find User email: "+ email);
    }
    public UserNotFoundException(){
        super("No users found");
    }
}

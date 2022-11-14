package com.collab.g5.demo.exceptions.users;

import com.collab.g5.demo.users.User;

public class UserNotVaccinatedException extends RuntimeException {
    public UserNotVaccinatedException(User user) {
        super(user.getName() + " is not vaccinated");
    }
}

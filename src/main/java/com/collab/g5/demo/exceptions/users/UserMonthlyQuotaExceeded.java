package com.collab.g5.demo.exceptions.users;

import com.collab.g5.demo.users.User;

public class UserMonthlyQuotaExceeded extends RuntimeException {
    public UserMonthlyQuotaExceeded(User user) {
        super(user.getName() + " monthly quota exceeded");
    }
}

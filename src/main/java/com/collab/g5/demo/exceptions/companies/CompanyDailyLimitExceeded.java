package com.collab.g5.demo.exceptions.companies;

public class CompanyDailyLimitExceeded extends RuntimeException {
    public CompanyDailyLimitExceeded() {
        super("Company Daily Limit Exceeded");
    }
}

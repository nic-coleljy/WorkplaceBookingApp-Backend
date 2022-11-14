package com.collab.g5.demo.exceptions.dailyForm;

import java.time.LocalDate;

public class DailyFormNotFoundException extends RuntimeException {
    public DailyFormNotFoundException(){
        super("no daily form found");
    }
    public DailyFormNotFoundException(String user, LocalDate dateTime){
        super("no daily form submitted by "+ user + " at "+ dateTime);
    }
}

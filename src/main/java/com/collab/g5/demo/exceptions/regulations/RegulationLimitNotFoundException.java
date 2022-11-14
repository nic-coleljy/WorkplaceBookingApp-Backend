package com.collab.g5.demo.exceptions.regulations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Error

public class RegulationLimitNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public RegulationLimitNotFoundException(LocalDate startDate, int cid){
        super("No regulation limit found with Start Date " + startDate + "and cid " + cid);
    }
    public RegulationLimitNotFoundException(){
        super("No regulation found");
    }
}

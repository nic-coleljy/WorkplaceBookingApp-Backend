package com.collab.g5.demo.exceptions.regulations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.time.LocalDate;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Error
public class RegulationNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public RegulationNotFoundException(LocalDate startDate) {
        super("Could not find regulation with Start Date " + startDate);
    }

}
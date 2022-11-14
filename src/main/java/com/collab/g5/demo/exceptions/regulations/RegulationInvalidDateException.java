package com.collab.g5.demo.exceptions.regulations;

import com.collab.g5.demo.regulations.Regulation;

public class RegulationInvalidDateException extends RuntimeException {
    public RegulationInvalidDateException(Regulation r) {
        super("Your start date : " + r.getStartDate() + " and end date : " + r.getEndDate() + " is invalid");
    }
}
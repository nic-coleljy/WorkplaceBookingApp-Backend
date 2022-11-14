package com.collab.g5.demo.exceptions.regulations;

import com.collab.g5.demo.regulations.Regulation;

public class RegulationExistsException extends RuntimeException {
    public RegulationExistsException(Regulation r) {
        super("Your regulation on " + r.getStartDate() + " to " + r.getEndDate() + " clashed with an ongoing regulation");
    }
}

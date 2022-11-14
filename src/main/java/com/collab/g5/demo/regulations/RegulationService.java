package com.collab.g5.demo.regulations;

import java.time.LocalDate;
import java.util.List;

public interface RegulationService {
    //CREATE
    Regulation save(Regulation newRegulation);

    //READ
    List<Regulation> getAllRegulation();

    List<List<String>> getAllRegulationWithLimit(String userEmail);

    Regulation getRegulationById(LocalDate startDate);

    //UPDATE
    Regulation updateRegulation(LocalDate startDate, Regulation newRegulation);

    //DELETE
    void deleteRegulationById(LocalDate startDate);
}

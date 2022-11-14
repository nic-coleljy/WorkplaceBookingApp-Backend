package com.collab.g5.demo.regulations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RegulationLimitService {
    //CREATE
    RegulationLimit save(RegulationLimit newRegulationLimit);

    //READ
    List<RegulationLimit> getAllRegulationLimit();

    Optional<RegulationLimit> getRegulationLimitById(LocalDate startDate, int cid);

    RegulationLimit getCurrentRegulationLimitById(int cid);

    RegulationLimit getCurrentRegulationLimitByUser(String email);

    //UPDATE
    RegulationLimit updateRegulationLimit(LocalDate startDate, int cid, RegulationLimit newRegulationLimit);

    //DELETE
    void deleteRegulationLimitById(LocalDate startDate, int cid);


}

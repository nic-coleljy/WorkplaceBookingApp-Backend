package com.collab.g5.demo.regulations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RegulationServiceImpl implements RegulationService {
    //Declaration of variables
    private RegulationRepository regulationRepository;

    /**
     * Instantiating the variables for this class.
     *
     * @param regulationRepository
     */
    @Autowired
    public RegulationServiceImpl(RegulationRepository regulationRepository) {
        this.regulationRepository = regulationRepository;
    }

    /**
     * Persist a new regulation
     *
     * @param newRegulation
     * @return Regulation that is saved into the database.
     */
    @Override
    public Regulation save(Regulation newRegulation) {
        return regulationRepository.save(newRegulation);
    }

    /**
     * Returns all the regulations that is in the database
     *
     * @return List of regulations
     */
    @Override
    public List<Regulation> getAllRegulation() {
        return regulationRepository.findAll();
    }

    /**
     * Returns a nested list of strings which contains the details of the regulations and the daily limit implemented by the user's company
     * @param userEmail
     * @return regulations and the daily limit implemented by the user's company
     */
    @Override
    public List<List<String>> getAllRegulationWithLimit(String userEmail) {
        return regulationRepository.findAllRegulationWithLimit(userEmail);
    }

    /**
     * Finds a regulation based on its ID and returns the object if found in the database.
     *
     * @param startDate
     * @return A regulation if it exists or else it will return null.
     */
    @Override
    public Regulation getRegulationById(LocalDate startDate) {
        return regulationRepository.findById(startDate).map(regulation -> {
            return regulation;
        }).orElse(null);
    }

    /**
     * Finds a regulation based on the startDate and updates its contents with the data inside newRegulation
     * @param startDate
     * @param newRegulation
     * @return
     */
    @Override
    public Regulation updateRegulation(LocalDate startDate, Regulation newRegulation) {
        return regulationRepository.findById(startDate).map(regulation -> {
            regulation.setStartDate(newRegulation.getStartDate());
            regulation.setEndDate(newRegulation.getEndDate());
            regulation.setPercentage(newRegulation.getPercentage());
            return regulationRepository.save(regulation);
        }).orElse(null);
    }

    /**
     * Remove a regulation with the given startDate
     * Spring Data JPA does not return a value for delete operation
     * Cascading: removing a regulation will also remove all its associated information
     */
    @Override
    public void deleteRegulationById(LocalDate startDate) {
        regulationRepository.deleteById(startDate);
    }
}

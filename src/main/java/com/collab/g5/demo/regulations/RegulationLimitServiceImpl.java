package com.collab.g5.demo.regulations;

import com.collab.g5.demo.users.User;
import com.collab.g5.demo.users.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RegulationLimitServiceImpl implements RegulationLimitService {

    /**
     * Declaration of varaibles
     */
    private RegulationLimitRepository regulationLimitRepository;
    private UserServiceImpl userServiceImpl;


    /**
     * Instantiating the variables listed.
     *
     * @param regulationLimitRepository
     * @param userServiceImpl
     */
    @Autowired
    public RegulationLimitServiceImpl(RegulationLimitRepository regulationLimitRepository, UserServiceImpl userServiceImpl) {
        this.regulationLimitRepository = regulationLimitRepository;
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * Persist a new RegulationLimit based on a regulation limit object
     *
     * @param newRegulationLimit
     * @return A new regulation limit
     */
    @Override
    public RegulationLimit save(RegulationLimit newRegulationLimit) {
        return regulationLimitRepository.save(newRegulationLimit);
    }

    /**
     * Returns all the regulation limit entries in the database
     * @return a List of regulation Limit.
     */
    @Override
    public List<RegulationLimit> getAllRegulationLimit() {
        return regulationLimitRepository.findAll();
    }

    /**
     * Finding a regulation limit object based on the ID
     * @param startDate
     * @param cid
     * @return A regulation limit object if it can be found or else will return null.
     */
    @Override
    public Optional<RegulationLimit> getRegulationLimitById(LocalDate startDate, int cid) {
        Optional<RegulationLimit> regulationLimit = Optional.ofNullable(regulationLimitRepository.getById(new RegulationLimitKey(startDate, cid)));
        if (regulationLimit.isPresent()) {
            return regulationLimit;
        } else
            return null;
    }

    /**
     * Uniquely Identify a regulation Limit based on the start date and CID which forms a regulation limit key and
     * updates its values to the new Regulation Limit data
     * @param startDate
     * @param cid
     * @param newRegulationLimit
     * @return Updated Regulation Limit
     */
    @Override
    public RegulationLimit updateRegulationLimit(LocalDate startDate, int cid, RegulationLimit newRegulationLimit) {
        return regulationLimitRepository
                .findById(new RegulationLimitKey(startDate, cid))
                .map(regulationLimit -> {
                    regulationLimit.setRegulationLimitKey(newRegulationLimit.getRegulationLimitKey());
                    regulationLimit.setDailyLimit(newRegulationLimit.getDailyLimit());
                    return regulationLimitRepository.save(regulationLimit);
                }).orElse(null);
    }

    /**
     * Remove a regulation limit with the given cid
     * Spring Data JPA does not return a value for delete operation
     * Cascading: removing a regulation limit will also remove all its associated information
     */
    @Override
    public void deleteRegulationLimitById(LocalDate startDate, int cid) {
        regulationLimitRepository.deleteById(new RegulationLimitKey(startDate, cid));
    }

    /**
     * This will get the current(latest) regulation limit based on the cid
     * @param cid
     * @return corresponding regulation limit
     */
    @Override
    public RegulationLimit getCurrentRegulationLimitById(int cid) {
        List<RegulationLimit> list = regulationLimitRepository.findAll();
        Collections.sort(list, (x, y) -> x.getRegulation().getStartDate().compareTo(x.getRegulation().getStartDate()));
        if (list.size() == 0) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    /**
     * This will get the current regulation that is relevant to the specific user
     * based on their company
     * @param email
     * @return corresponding regulation limit
     */
    @Override
    public RegulationLimit getCurrentRegulationLimitByUser(String email) {
        User user = userServiceImpl.getUserByEmail(email);
        return getCurrentRegulationLimitById(user.getCompany().getCid());
    }
}

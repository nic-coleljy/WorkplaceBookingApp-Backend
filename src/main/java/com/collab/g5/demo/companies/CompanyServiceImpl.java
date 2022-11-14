package com.collab.g5.demo.companies;

import com.collab.g5.demo.regulations.RegulationLimitKey;
import com.collab.g5.demo.regulations.RegulationLimitRepository;
import com.collab.g5.demo.regulations.RegulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    //Declaring the variables.
    private CompanyRepository companyRepository;
    private RegulationRepository regulationRepository;
    private RegulationLimitRepository regulationLimitRepository;

    /**
     * Instantiating the variables by making use of constructor injection.
     *
     * @param companyRepository
     * @param regulationRepository
     * @param regulationLimitRepository
     */
    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, RegulationRepository regulationRepository, RegulationLimitRepository regulationLimitRepository) {
        this.companyRepository = companyRepository;
        this.regulationRepository = regulationRepository;
        this.regulationLimitRepository = regulationLimitRepository;
    }

    /**
     * Getting all companies
     *
     * @return a list which will contain the list of Companies.
     */
    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    /**
     * Retrieve a company based on Iits ID.
     *
     * @param cid
     * @return the company with cid as cid or return null if nothing is found.
     */
    @Override
    public Company getCompanyById(int cid) {
        return companyRepository.findById(cid).orElse(null);
    }

    /**
     * Checks if such company exists.
     * @param cid
     * @return either true or false depending if the company can be found.
     */
    @Override
    public boolean containsCompany(int cid) {
        return companyRepository.findById(cid).isPresent();
    }

    /**
     * Adding a new company.
     * @param newCompany
     */
    @Override
    public void addNewCompany(Company newCompany) {
        companyRepository.save(newCompany);
    }

    /**
     * Deletes a company based on the param.
     * @param company
     */
    @Override
    public void delete(Company company) {
        companyRepository.delete(company);
    }

    @Override
    public Company updateCompany(int cid, Company bookings) {
        return null;
    }

    /**
     * Remove a company with the given cid
     * Spring Data JPA does not return a value for delete operation
     * Cascading: removing a company will also remove all its associated reviews
     */
    @Override
    public void deleteById(int cid) {
        companyRepository.deleteById(cid);
    }

    //this is to retrieve the maximum number of users that can go back to the company at that particular date.

    /**
     * Given a specific date and the company ID, find out what is the daily limit
     *
     * @param cid
     * @param bookingsDate
     * @return the daily limit for that particular date and company.
     */
    @Override
    public int getCurrentQuota(int cid, LocalDate bookingsDate) {
        LocalDate startingDate = regulationRepository.findStartDateBasedCustomDate(bookingsDate);
        RegulationLimitKey tempLimitKey = new RegulationLimitKey(startingDate, cid);
        return regulationLimitRepository.getById(tempLimitKey).getDailyLimit();
    }
}

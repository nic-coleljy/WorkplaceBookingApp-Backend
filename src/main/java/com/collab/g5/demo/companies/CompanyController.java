package com.collab.g5.demo.companies;

import com.collab.g5.demo.exceptions.companies.CompaniesNotFoundException;
import com.collab.g5.demo.exceptions.companies.InsertCompanyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/com")
public class CompanyController {
    //declaring the variables needed for this class.
    private CompanyServiceImpl companyServiceImpl;

    /**
     * Instantiating the variables for this class.
     * @param companyServiceImpl
     */
    @Autowired
    public CompanyController(CompanyServiceImpl companyServiceImpl) {
        this.companyServiceImpl = companyServiceImpl;
    }

    /**
     * List all companies  in the system
     *
     * @return list of all companies
     */
    @GetMapping("/hr")
    public List<Company> getCompany() throws CompaniesNotFoundException {
        List<Company> toReturn = companyServiceImpl.getAllCompanies();
        if (toReturn.size() == 0) {
            throw new CompaniesNotFoundException();
        }
        return toReturn;
    }

    /**
     * Search for company with the given cid
     * If there is no company with the given "cid", throw a CompaniesNotFoundException
     *
     * @param cid
     * @return company with the given cid
     */
    @GetMapping("/hr/{cid}")
    public Company getCompanyById(@PathVariable int cid) throws CompaniesNotFoundException {
        Company company = companyServiceImpl.getCompanyById(cid);

        if (company == null) {
            // throw an exception
            throw new CompaniesNotFoundException(cid);
        }
        return company;
    }

    /**
     * Add a new company  with POST request to "/hr/{newsCompany}"
     * Note the use of @RequestBody
     *
     * @param newCompany
     * @return the newly added company
     */
    @PostMapping("/hr/{newCompany}")
    public void newCompany(@RequestBody Company newCompany) throws InsertCompanyException {
        int cid = newCompany.getCid();
        if (companyServiceImpl.containsCompany(cid)) {
            throw new InsertCompanyException(cid);
        }
        companyServiceImpl.addNewCompany(newCompany);
    }

    /**
     * Remove a company with the DELETE request to "/hr/{name}"
     * If there is no book with the given "cid", throw a CompaniesNotFoundException
     *
     * @param cid
     */
    @DeleteMapping("/hr/{name}")
    void deleteCompany(@PathVariable int cid) throws CompaniesNotFoundException {
        Company company = companyServiceImpl.getCompanyById(cid);
        if (company == null) {
            // throw an exception
            throw new CompaniesNotFoundException(cid);
        }
        companyServiceImpl.delete(company);
    }
}

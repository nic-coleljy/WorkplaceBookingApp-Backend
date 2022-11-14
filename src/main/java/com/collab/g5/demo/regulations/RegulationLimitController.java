package com.collab.g5.demo.regulations;

import com.collab.g5.demo.companies.CompanyServiceImpl;
import com.collab.g5.demo.exceptions.regulations.RegulationLimitNotFoundException;
import com.collab.g5.demo.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/regulationLimit")
public class RegulationLimitController {
    private RegulationLimitServiceImpl regulationLimitServiceImpl;
    private RegulationServiceImpl regulationServiceImpl;
    private CompanyServiceImpl companyServiceImpl;

    @Autowired
    public RegulationLimitController(RegulationLimitServiceImpl regulationLimitServiceImpl, RegulationServiceImpl regulationServiceImpl, CompanyServiceImpl companyServiceImpl) {
        this.regulationLimitServiceImpl = regulationLimitServiceImpl;
        this.regulationServiceImpl = regulationServiceImpl;
        this.companyServiceImpl = companyServiceImpl;
    }

    /**
     * List all regulation limits in the system
     * @return list of all regulation limits
     */
    @GetMapping("/emp")
    public List<RegulationLimit> getRegulationLimits() {
        return regulationLimitServiceImpl.getAllRegulationLimit();
    }

    /**
     * Search for regulation limit with the given startDate and cid
     * If there is no regulation limit with the given "startDate" and "cid", throw a RegulationLimitNotFoundException
     * @param startDate
     * @param cid
     * @return regulation limit with the given startDate and cid
     */
    @GetMapping("/emp/{startDate}/{cid}")
    public Optional<RegulationLimit> getRegulationLimitById(@PathVariable @DateTimeFormat(pattern = "uuuu-MM-dd") LocalDate startDate, @PathVariable int cid) throws RegulationLimitNotFoundException {

        Optional<RegulationLimit> getRegulationLimit = regulationLimitServiceImpl.getRegulationLimitById(startDate, cid);

        if (getRegulationLimit.isEmpty()) {
            // throw an exception
            throw new RegulationLimitNotFoundException(startDate, cid);
        }
        return getRegulationLimit;
    }

    /**
     * Search for current regulation limit for a specific company
     * @param cid
     * @return current regulation limit for specific company
     */
    @GetMapping("/emp/{cid}")
    public RegulationLimit getCurrentRegulationLimitById( @PathVariable int cid) throws RegulationLimitNotFoundException {

        RegulationLimit getRegulationLimit = regulationLimitServiceImpl.getCurrentRegulationLimitById( cid);

        if (getRegulationLimit==null) {
            // throw an exception
            throw new RegulationLimitNotFoundException();
        }
        return getRegulationLimit;
    }

    /**
     * Search for current regulation limit relevant to specific user
     * @param email
     * @return current regulation limit relevant to user
     */

    @GetMapping("/emp/num/{email}")
    public int getCurrentRegulationLimitByUser( @PathVariable String email) throws RegulationLimitNotFoundException {

        RegulationLimit getRegulationLimit = regulationLimitServiceImpl.getCurrentRegulationLimitByUser(email);

        if (getRegulationLimit==null) {
            // throw an exception
            throw new RegulationLimitNotFoundException();
        }
        return getRegulationLimit.getDailyLimit();
    }




    /**
     * Add a new regulation limit with POST request to "/hr"
     * @param regulationLimit
     * @return list of all regulation limits
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hr")
    public RegulationLimit addRegulationLimit(@RequestBody RegulationLimit regulationLimit) {

        System.out.println("Post request" + regulationLimit);

        RegulationLimitKey regulationLimitKey = regulationLimit.getRegulationLimitKey();
        regulationLimit.setRegulation(regulationServiceImpl.getRegulationById(regulationLimitKey.getStartDate()));

        regulationLimit.setCompany(companyServiceImpl.getCompanyById(regulationLimitKey.getCid()));

        return regulationLimitServiceImpl.save(regulationLimit);
    }

    /**
     * If there is no regulation limit with the given "startDate" and "cid", throw a RegulationLimitNotFoundException
     * @param startDate
     * @param cid
     * @param newRegulationLimit
     * @return the updated, or newly added regulation limit
     */
    @PutMapping("/hr/{startDate}/{cid}")
    public RegulationLimit updateRegulationLimit(@PathVariable @DateTimeFormat(pattern = "uuuu-MM-dd") LocalDate startDate, @PathVariable int cid, @RequestBody RegulationLimit newRegulationLimit) throws RegulationLimitNotFoundException {
        RegulationLimit regulationLimit = regulationLimitServiceImpl.updateRegulationLimit(startDate, cid, newRegulationLimit);

        if (regulationLimit == null) throw new RegulationLimitNotFoundException(startDate, cid);
        return regulationLimit;
    }

    /**
     * Remove a regulation limit with the DELETE request to "/hr/{startDate}/{cid}"
     * If there is no regulation limit with the given "startDate" and "cid", throw a RegulationLimtNotFoundException
     * @param startDate
     * @param cid
     */
    @DeleteMapping("/hr/{startDate}/{cid}")
    void deleteRegulationLimitById(@PathVariable @DateTimeFormat(pattern = "uuuu-MM-dd") LocalDate startDate, @PathVariable int cid) throws RegulationLimitNotFoundException {
        try {
            regulationLimitServiceImpl.deleteRegulationLimitById(startDate, cid);
        } catch (EmptyResultDataAccessException e) {
            throw new RegulationLimitNotFoundException(startDate, cid);
        }
    }
}

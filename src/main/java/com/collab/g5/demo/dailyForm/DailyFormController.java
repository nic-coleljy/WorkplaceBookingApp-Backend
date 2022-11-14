package com.collab.g5.demo.dailyForm;

import com.collab.g5.demo.exceptions.dailyForm.DailyFormNotFoundException;
import com.collab.g5.demo.exceptions.users.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/dailyForm")
public class DailyFormController {
    /**
     * Declaring the variables.
     */
    private DailyFormServiceImpl dailyFormServiceImpl;

    /**
     * Instantiaitng the variables by making use of Constructor based injection.
     *
     * @param dailyFormServiceImpl
     */
    @Autowired
    public DailyFormController(DailyFormServiceImpl dailyFormServiceImpl) {
        this.dailyFormServiceImpl = dailyFormServiceImpl;
    }

    /**
     * Adds a new daily checkin form to the user.
     *
     * @param newDailyForm
     * @throws EmailExistsException
     */
    @PostMapping("/emp/new")
    public void newDailyForm(@RequestBody DailyForm newDailyForm) throws EmailExistsException {
        dailyFormServiceImpl.addDailyForm(newDailyForm);
    }

    /**
     * Retrieves all the forms/
     *
     * @return
     * @throws DailyFormNotFoundException
     */
    @GetMapping("/hr")
    public List<DailyForm> getAllDailyForms() throws DailyFormNotFoundException {
        List<DailyForm> toReturn = dailyFormServiceImpl.getAllDailyForms();
        if (toReturn.size() == 0) {
            throw new DailyFormNotFoundException();
        }
        return toReturn;
    }

    /**
     *
     * @param email
     * @return
     * @throws DailyFormNotFoundException
     */
    @GetMapping("hr/user/{email}")
    public List<DailyForm> getDailyFormsByUser(@PathVariable String email) throws DailyFormNotFoundException {
        List<DailyForm> toReturn = dailyFormServiceImpl.getDailyFormByUser(email);
        if (toReturn.size() == 0) {
            throw new DailyFormNotFoundException();
        }
        return toReturn;
    }

    /**
     * Gets all the daily form that is on this date.
     * @param date
     * @return
     * @throws DailyFormNotFoundException
     */
    @GetMapping("hr/date/{date}")
    public List<DailyForm> getDailyFormsByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) throws DailyFormNotFoundException {

        List<DailyForm> toReturn = dailyFormServiceImpl.getDailyFormByDate(date);
        if (toReturn.size() == 0) {
            throw new DailyFormNotFoundException();
        }
        return toReturn;
    }

    /**
     * Get the total number of daily check-in form submitted with the GET request to "/date/num/{date}"
     *
     * @param date
     */


    @GetMapping("emp/date/num/{date}")

    public int getNumDailyFormsByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) throws DailyFormNotFoundException {

        int toReturn = dailyFormServiceImpl.getNumDailyFormByDate(date);
        return toReturn;
    }

    /**
     * Get the number of unique users that checked in  with the GET request to "/date/users/{date}"
     *
     * @param date
     */
    @GetMapping("emp/date/users/{date}")
    public int getUniqueNumDailyFormsByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) throws DailyFormNotFoundException {


        int toReturn = dailyFormServiceImpl.getUniqueNumDailyFormByDate(date);
        return toReturn;
    }

    /**
     *
     * @param date
     * @return
     * @throws DailyFormNotFoundException
     */
    @GetMapping("emp/date/users/week/{date}")
    public int[] getUniqueNumDailyFormsByWeek(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) throws DailyFormNotFoundException {
        System.out.println("Function Called");
        int[] toReturn = dailyFormServiceImpl.getUniqueNumDailyFormByWeek(date);
        return toReturn;
    }

    /**
     *
     * @param email
     * @return
     * @throws DailyFormNotFoundException
     */
    @GetMapping("emp/userToday/{email}")
    public Boolean getDailyFormsByUserToday(@PathVariable String email) throws DailyFormNotFoundException {
        return dailyFormServiceImpl.getDailyFormByUserToday(email);
    }


}

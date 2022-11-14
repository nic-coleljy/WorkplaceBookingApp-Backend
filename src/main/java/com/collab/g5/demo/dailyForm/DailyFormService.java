package com.collab.g5.demo.dailyForm;

import com.collab.g5.demo.users.User;

import java.time.LocalDate;
import java.util.List;


public interface DailyFormService {
    List<DailyForm> getAllDailyForms();

    List<DailyForm> getDailyFormByUser(String useremail);

    List<DailyForm> getDailyFormByUserAndDate(User user, LocalDate dateTime);

    void addDailyForm(DailyForm dailyForm);

    boolean getDailyFormByUserToday(String email);

    List<DailyForm> getDailyFormByDate(LocalDate dateTime);

    int getNumDailyFormByDate(LocalDate dateTime);

    int getUniqueNumDailyFormByDate(LocalDate date);

    int[] getUniqueNumDailyFormByWeek(LocalDate date);
}

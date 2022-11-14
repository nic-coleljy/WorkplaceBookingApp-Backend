package com.collab.g5.demo.dailyForm;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.collab.g5.demo.bookings.Bookings;
import com.collab.g5.demo.companies.Company;
import com.collab.g5.demo.exceptions.users.EmailExistsException;
import com.collab.g5.demo.news.News;
import com.collab.g5.demo.regulations.RegulationLimit;
import com.collab.g5.demo.users.User;
import com.collab.g5.demo.users.UserRole;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {DailyFormController.class})
@ExtendWith(SpringExtension.class)
class DailyFormControllerTest {
    @Autowired
    private DailyFormController dailyFormController;

    @MockBean
    private DailyFormServiceImpl dailyFormServiceImpl;

    @Test
    void testNewDailyForm() throws EmailExistsException {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setCompany(company);
        user.setNewsList(new ArrayList<News>());
        user.setFname("Fname");
        user.setLname("Lname");
        user.setEnabled(true);
        user.setBookings(new ArrayList<Bookings>());
        user.setLocked(true);
        user.setVaccinated(true);
        user.setUserRole(UserRole.EMPLOYEE);
        user.setDailyFormList(new ArrayList<DailyForm>());

        DailyForm dailyForm = new DailyForm();
        dailyForm.setTemperature(10.0f);
        dailyForm.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);
        DailyFormRepository dailyFormRepository = mock(DailyFormRepository.class);
        when(dailyFormRepository.save((DailyForm) any())).thenReturn(dailyForm);
        DailyFormController dailyFormController = new DailyFormController(new DailyFormServiceImpl(dailyFormRepository));

        Company company1 = new Company();
        company1.setQuota(1);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company1);
        user1.setNewsList(new ArrayList<News>());
        user1.setFname("Fname");
        user1.setLname("Lname");
        user1.setEnabled(true);
        user1.setBookings(new ArrayList<Bookings>());
        user1.setLocked(true);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(new ArrayList<DailyForm>());

        DailyForm dailyForm1 = new DailyForm();
        dailyForm1.setTemperature(10.0f);
        dailyForm1.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm1.setDateExactTime("2020-03-01");
        dailyForm1.setUser(user1);
        dailyForm1.setSymptoms(true);
        dailyForm1.setFID(1);
        dailyFormController.newDailyForm(dailyForm1);
        verify(dailyFormRepository).save((DailyForm) any());
    }

    @Test
    void testNewDailyForm2() throws EmailExistsException {
        DailyFormServiceImpl dailyFormServiceImpl = mock(DailyFormServiceImpl.class);
        doNothing().when(dailyFormServiceImpl).addDailyForm((DailyForm) any());
        DailyFormController dailyFormController = new DailyFormController(dailyFormServiceImpl);

        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setCompany(company);
        user.setNewsList(new ArrayList<News>());
        user.setFname("Fname");
        user.setLname("Lname");
        user.setEnabled(true);
        user.setBookings(new ArrayList<Bookings>());
        user.setLocked(true);
        user.setVaccinated(true);
        user.setUserRole(UserRole.EMPLOYEE);
        user.setDailyFormList(new ArrayList<DailyForm>());

        DailyForm dailyForm = new DailyForm();
        dailyForm.setTemperature(10.0f);
        dailyForm.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);
        dailyFormController.newDailyForm(dailyForm);
        verify(dailyFormServiceImpl).addDailyForm((DailyForm) any());
    }

    @Test
    void testGetAllDailyForms() throws Exception {
        Company company = new Company();
        company.setQuota(0);
        company.setUsers(new ArrayList<User>());
        company.setName("?");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setCompany(company);
        user.setNewsList(new ArrayList<News>());
        user.setFname("?");
        user.setLname("?");
        user.setEnabled(true);
        user.setBookings(new ArrayList<Bookings>());
        user.setLocked(true);
        user.setVaccinated(true);
        user.setUserRole(UserRole.EMPLOYEE);
        user.setDailyFormList(new ArrayList<DailyForm>());

        DailyForm dailyForm = new DailyForm();
        dailyForm.setTemperature(10.0f);
        dailyForm.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormServiceImpl.getAllDailyForms()).thenReturn(dailyFormList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/dailyForm/hr");
        MockMvcBuilders.standaloneSetup(this.dailyFormController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    void testGetDailyFormsByDate() throws Exception {
        Company company = new Company();
        company.setQuota(0);
        company.setUsers(new ArrayList<User>());
        company.setName("?");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setCompany(company);
        user.setNewsList(new ArrayList<News>());
        user.setFname("?");
        user.setLname("?");
        user.setEnabled(true);
        user.setBookings(new ArrayList<Bookings>());
        user.setLocked(true);
        user.setVaccinated(true);
        user.setUserRole(UserRole.EMPLOYEE);
        user.setDailyFormList(new ArrayList<DailyForm>());

        DailyForm dailyForm = new DailyForm();
        dailyForm.setTemperature(10.0f);
        dailyForm.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormServiceImpl.getDailyFormByDate((LocalDate) any())).thenReturn(dailyFormList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/dailyForm/hr/date/{date}",
                LocalDate.ofEpochDay(1L));
        MockMvcBuilders.standaloneSetup(this.dailyFormController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    void testGetDailyFormsByUser() throws Exception {
        Company company = new Company();
        company.setQuota(0);
        company.setUsers(new ArrayList<User>());
        company.setName("?");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setCompany(company);
        user.setNewsList(new ArrayList<News>());
        user.setFname("?");
        user.setLname("?");
        user.setEnabled(true);
        user.setBookings(new ArrayList<Bookings>());
        user.setLocked(true);
        user.setVaccinated(true);
        user.setUserRole(UserRole.EMPLOYEE);
        user.setDailyFormList(new ArrayList<DailyForm>());

        DailyForm dailyForm = new DailyForm();
        dailyForm.setTemperature(10.0f);
        dailyForm.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormServiceImpl.getDailyFormByUser((String) any())).thenReturn(dailyFormList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/dailyForm/hr/user/{email}",
                "jane.doe@example.org");
        MockMvcBuilders.standaloneSetup(this.dailyFormController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    void testGetDailyFormsByUserToday() throws Exception {
        when(this.dailyFormServiceImpl.getDailyFormByUserToday((String) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/dailyForm/emp/userToday/{email}",
                "jane.doe@example.org");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(this.dailyFormController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testGetDailyFormsByUserToday2() throws Exception {
        when(this.dailyFormServiceImpl.getDailyFormByUserToday((String) any())).thenReturn(true);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/dailyForm/emp/userToday/{email}",
                "jane.doe@example.org");
        getResult.contentType("Not all who wander are lost");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(this.dailyFormController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testGetNumDailyFormsByDate() throws Exception {
        when(this.dailyFormServiceImpl.getNumDailyFormByDate((LocalDate) any())).thenReturn(10);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/dailyForm/emp/date/num/{date}",
                LocalDate.ofEpochDay(1L));
        MockMvcBuilders.standaloneSetup(this.dailyFormController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10"));
    }

    @Test
    void testGetNumDailyFormsByDate2() throws Exception {
        when(this.dailyFormServiceImpl.getNumDailyFormByDate((LocalDate) any())).thenReturn(10);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/dailyForm/emp/date/num/{date}",
                LocalDate.ofEpochDay(1L));
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.dailyFormController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10"));
    }

    @Test
    void testGetUniqueNumDailyFormsByDate() throws Exception {
        when(this.dailyFormServiceImpl.getUniqueNumDailyFormByDate((LocalDate) any())).thenReturn(10);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/dailyForm/emp/date/users/{date}",
                LocalDate.ofEpochDay(1L));
        MockMvcBuilders.standaloneSetup(this.dailyFormController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10"));
    }

    @Test
    void testGetUniqueNumDailyFormsByDate2() throws Exception {
        when(this.dailyFormServiceImpl.getUniqueNumDailyFormByDate((LocalDate) any())).thenReturn(10);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/dailyForm/emp/date/users/{date}",
                LocalDate.ofEpochDay(1L));
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.dailyFormController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10"));
    }

    @Test
    void testGetUniqueNumDailyFormsByWeek() throws Exception {
        when(this.dailyFormServiceImpl.getUniqueNumDailyFormByWeek((LocalDate) any()))
                .thenReturn(new int[]{10, 10, 10, 10});
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/dailyForm/emp/date/users/week/{date}", LocalDate.ofEpochDay(1L));
        MockMvcBuilders.standaloneSetup(this.dailyFormController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[10,10,10,10]"));
    }
}


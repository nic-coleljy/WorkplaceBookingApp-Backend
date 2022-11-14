package com.collab.g5.demo.dailyForm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.collab.g5.demo.bookings.Bookings;
import com.collab.g5.demo.companies.Company;
import com.collab.g5.demo.news.News;
import com.collab.g5.demo.regulations.RegulationLimit;
import com.collab.g5.demo.users.User;
import com.collab.g5.demo.users.UserRole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DailyFormServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DailyFormServiceImplTest {
    @MockBean
    private DailyFormRepository dailyFormRepository;

    @Autowired
    private DailyFormServiceImpl dailyFormServiceImpl;

    @Test
    void testGetAllDailyForms() {
        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        List<DailyForm> actualAllDailyForms = this.dailyFormServiceImpl.getAllDailyForms();
        assertSame(dailyFormList, actualAllDailyForms);
        assertTrue(actualAllDailyForms.isEmpty());
        verify(this.dailyFormRepository).findAll();
    }

    @Test
    void testGetDailyFormByUser() {
        when(this.dailyFormRepository.findAll()).thenReturn(new ArrayList<DailyForm>());
        List<DailyForm> actualDailyFormByUser = this.dailyFormServiceImpl.getDailyFormByUser("jane.doe@example.org");
        assertTrue(actualDailyFormByUser.isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(actualDailyFormByUser, this.dailyFormServiceImpl.getAllDailyForms());
    }

    @Test
    void testGetDailyFormByUser2() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        List<DailyForm> actualDailyFormByUser = this.dailyFormServiceImpl.getDailyFormByUser("jane.doe@example.org");
        assertEquals(1, actualDailyFormByUser.size());
        verify(this.dailyFormRepository).findAll();
        assertEquals(actualDailyFormByUser, this.dailyFormServiceImpl.getAllDailyForms());
    }

    @Test
    void testGetDailyFormByUser3() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        assertTrue(this.dailyFormServiceImpl.getDailyFormByUser(null).isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetDailyFormByUserToday() {
        when(this.dailyFormRepository.findAll()).thenReturn(new ArrayList<DailyForm>());
        assertFalse(this.dailyFormServiceImpl.getDailyFormByUserToday("jane.doe@example.org"));
        verify(this.dailyFormRepository).findAll();
        assertTrue(this.dailyFormServiceImpl.getAllDailyForms().isEmpty());
    }

    @Test
    void testGetDailyFormByUserToday2() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        assertFalse(this.dailyFormServiceImpl.getDailyFormByUserToday("jane.doe@example.org"));
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetDailyFormByUserToday3() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        assertFalse(this.dailyFormServiceImpl.getDailyFormByUserToday(null));
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetDailyFormByUserAndDate() {
        when(this.dailyFormRepository.findAll()).thenReturn(new ArrayList<DailyForm>());

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
        List<DailyForm> actualDailyFormByUserAndDate = this.dailyFormServiceImpl.getDailyFormByUserAndDate(user,
                LocalDate.ofEpochDay(1L));
        assertTrue(actualDailyFormByUserAndDate.isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(actualDailyFormByUserAndDate, this.dailyFormServiceImpl.getAllDailyForms());
    }

    @Test
    void testGetDailyFormByUserAndDate2() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);

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
        assertTrue(this.dailyFormServiceImpl.getDailyFormByUserAndDate(user1, LocalDate.ofEpochDay(1L)).isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetDailyFormByUserAndDate3() {
        Company company = new Company();
        company.setQuota(0);
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

        Company company1 = new Company();
        company1.setQuota(0);
        company1.setUsers(new ArrayList<User>());
        company1.setName("jane.doe@example.org");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company1);
        user1.setNewsList(new ArrayList<News>());
        user1.setFname("jane.doe@example.org");
        user1.setLname("jane.doe@example.org");
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm1);
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setCompany(company2);
        user2.setNewsList(new ArrayList<News>());
        user2.setFname("Fname");
        user2.setLname("Lname");
        user2.setEnabled(true);
        user2.setBookings(new ArrayList<Bookings>());
        user2.setLocked(true);
        user2.setVaccinated(true);
        user2.setUserRole(UserRole.EMPLOYEE);
        user2.setDailyFormList(new ArrayList<DailyForm>());
        assertTrue(this.dailyFormServiceImpl.getDailyFormByUserAndDate(user2, LocalDate.ofEpochDay(1L)).isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(2, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetDailyFormByUserAndDate4() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);

        Company company1 = new Company();
        company1.setQuota(1);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        User user1 = new User();
        user1.setEmail("Fname");
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
        assertTrue(this.dailyFormServiceImpl.getDailyFormByUserAndDate(user1, LocalDate.ofEpochDay(1L)).isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetDailyFormByUserAndDate5() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);

        Company company1 = new Company();
        company1.setQuota(1);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("jane.doe@example.org");
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
        assertTrue(this.dailyFormServiceImpl.getDailyFormByUserAndDate(user1, LocalDate.ofEpochDay(1L)).isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetDailyFormByUserAndDate6() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);

        Company company1 = new Company();
        company1.setQuota(0);
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
        List<DailyForm> actualDailyFormByUserAndDate = this.dailyFormServiceImpl.getDailyFormByUserAndDate(user1,
                LocalDate.ofEpochDay(1L));
        assertEquals(1, actualDailyFormByUserAndDate.size());
        verify(this.dailyFormRepository).findAll();
        assertEquals(actualDailyFormByUserAndDate, this.dailyFormServiceImpl.getAllDailyForms());
    }

    @Test
    void testGetDailyFormByUserAndDate7() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);

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
        user1.setLname("jane.doe@example.org");
        user1.setEnabled(true);
        user1.setBookings(new ArrayList<Bookings>());
        user1.setLocked(true);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(new ArrayList<DailyForm>());
        assertTrue(this.dailyFormServiceImpl.getDailyFormByUserAndDate(user1, LocalDate.ofEpochDay(1L)).isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetDailyFormByUserAndDate8() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);

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
        user1.setEnabled(false);
        user1.setBookings(new ArrayList<Bookings>());
        user1.setLocked(true);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(new ArrayList<DailyForm>());
        assertTrue(this.dailyFormServiceImpl.getDailyFormByUserAndDate(user1, LocalDate.ofEpochDay(1L)).isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetDailyFormByUserAndDate9() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);

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
        user1.setLocked(false);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(new ArrayList<DailyForm>());
        assertTrue(this.dailyFormServiceImpl.getDailyFormByUserAndDate(user1, LocalDate.ofEpochDay(1L)).isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetDailyFormByUserAndDate10() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);

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
        user1.setVaccinated(false);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(new ArrayList<DailyForm>());
        assertTrue(this.dailyFormServiceImpl.getDailyFormByUserAndDate(user1, LocalDate.ofEpochDay(1L)).isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetDailyFormByUserAndDate11() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);

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
        user1.setUserRole(UserRole.HR);
        user1.setDailyFormList(new ArrayList<DailyForm>());
        assertTrue(this.dailyFormServiceImpl.getDailyFormByUserAndDate(user1, LocalDate.ofEpochDay(1L)).isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testAddDailyForm() {
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
        when(this.dailyFormRepository.save((DailyForm) any())).thenReturn(dailyForm);

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
        this.dailyFormServiceImpl.addDailyForm(dailyForm1);
        verify(this.dailyFormRepository).save((DailyForm) any());
        assertTrue(this.dailyFormServiceImpl.getAllDailyForms().isEmpty());
    }

    @Test
    void testGetDailyFormByDate() {
        when(this.dailyFormRepository.findAll()).thenReturn(new ArrayList<DailyForm>());
        List<DailyForm> actualDailyFormByDate = this.dailyFormServiceImpl.getDailyFormByDate(LocalDate.ofEpochDay(1L));
        assertTrue(actualDailyFormByDate.isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(actualDailyFormByDate, this.dailyFormServiceImpl.getAllDailyForms());
    }

    @Test
    void testGetDailyFormByDate2() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        List<DailyForm> actualDailyFormByDate = this.dailyFormServiceImpl.getDailyFormByDate(LocalDate.ofEpochDay(1L));
        assertEquals(1, actualDailyFormByDate.size());
        verify(this.dailyFormRepository).findAll();
        assertEquals(actualDailyFormByDate, this.dailyFormServiceImpl.getAllDailyForms());
    }

    @Test
    void testGetDailyFormByDate3() {
        Company company = new Company();
        company.setQuota(0);
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
        dailyForm.setDateTime(LocalDate.ofYearDay(1, 1));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        assertTrue(this.dailyFormServiceImpl.getDailyFormByDate(LocalDate.ofEpochDay(1L)).isEmpty());
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetNumDailyFormByDate() {
        when(this.dailyFormRepository.findAll()).thenReturn(new ArrayList<DailyForm>());
        assertEquals(0, this.dailyFormServiceImpl.getNumDailyFormByDate(LocalDate.ofEpochDay(1L)));
        verify(this.dailyFormRepository).findAll();
        assertTrue(this.dailyFormServiceImpl.getAllDailyForms().isEmpty());
    }

    @Test
    void testGetNumDailyFormByDate2() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        assertEquals(1, this.dailyFormServiceImpl.getNumDailyFormByDate(LocalDate.ofEpochDay(1L)));
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetNumDailyFormByDate3() {
        Company company = new Company();
        company.setQuota(0);
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
        dailyForm.setDateTime(LocalDate.ofYearDay(1, 1));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        assertEquals(0, this.dailyFormServiceImpl.getNumDailyFormByDate(LocalDate.ofEpochDay(1L)));
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByDate() {
        when(this.dailyFormRepository.findAll()).thenReturn(new ArrayList<DailyForm>());
        assertEquals(0, this.dailyFormServiceImpl.getUniqueNumDailyFormByDate(LocalDate.ofEpochDay(1L)));
        verify(this.dailyFormRepository).findAll();
        assertTrue(this.dailyFormServiceImpl.getAllDailyForms().isEmpty());
    }

    @Test
    void testGetUniqueNumDailyFormByDate2() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        assertEquals(1, this.dailyFormServiceImpl.getUniqueNumDailyFormByDate(LocalDate.ofEpochDay(1L)));
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByDate3() {
        Company company = new Company();
        company.setQuota(0);
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

        Company company1 = new Company();
        company1.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm1);
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        assertEquals(1, this.dailyFormServiceImpl.getUniqueNumDailyFormByDate(LocalDate.ofEpochDay(1L)));
        verify(this.dailyFormRepository).findAll();
        assertEquals(2, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByDate4() {
        Company company = new Company();
        company.setQuota(0);
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
        dailyForm.setDateTime(LocalDate.ofYearDay(1, 1));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        assertEquals(0, this.dailyFormServiceImpl.getUniqueNumDailyFormByDate(LocalDate.ofEpochDay(1L)));
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByDate5() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user);

        Company company1 = new Company();
        company1.setQuota(0);
        company1.setUsers(userList);
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

        DailyForm dailyForm = new DailyForm();
        dailyForm.setTemperature(10.0f);
        dailyForm.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user1);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        assertEquals(1, this.dailyFormServiceImpl.getUniqueNumDailyFormByDate(LocalDate.ofEpochDay(1L)));
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByDate6() {
        Company company = new Company();
        company.setQuota(0);
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

        Company company1 = new Company();
        company1.setQuota(0);
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

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user);

        Company company2 = new Company();
        company2.setQuota(0);
        company2.setUsers(userList);
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setCompany(company2);
        user2.setNewsList(new ArrayList<News>());
        user2.setFname("Fname");
        user2.setLname("Lname");
        user2.setEnabled(true);
        user2.setBookings(new ArrayList<Bookings>());
        user2.setLocked(true);
        user2.setVaccinated(true);
        user2.setUserRole(UserRole.EMPLOYEE);
        user2.setDailyFormList(new ArrayList<DailyForm>());

        DailyForm dailyForm = new DailyForm();
        dailyForm.setTemperature(10.0f);
        dailyForm.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user2);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        assertEquals(1, this.dailyFormServiceImpl.getUniqueNumDailyFormByDate(LocalDate.ofEpochDay(1L)));
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByDate7() {
        Company company = new Company();
        company.setQuota(0);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        Company company1 = new Company();
        company1.setQuota(0);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        Company company2 = new Company();
        company2.setQuota(0);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setCompany(company2);
        user.setNewsList(new ArrayList<News>());
        user.setFname("Fname");
        user.setLname("Lname");
        user.setEnabled(true);
        user.setBookings(new ArrayList<Bookings>());
        user.setLocked(true);
        user.setVaccinated(true);
        user.setUserRole(UserRole.EMPLOYEE);
        user.setDailyFormList(new ArrayList<DailyForm>());

        News news = new News();
        news.setDate(LocalDate.ofEpochDay(1L));
        news.setCompany(company1);
        news.setUser(user);
        news.setNid(1);
        news.setTitle("Dr");
        news.setContent("Not all who wander are lost");

        ArrayList<News> newsList = new ArrayList<News>();
        newsList.add(news);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company);
        user1.setNewsList(newsList);
        user1.setFname("Fname");
        user1.setLname("Lname");
        user1.setEnabled(true);
        user1.setBookings(new ArrayList<Bookings>());
        user1.setLocked(true);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(new ArrayList<DailyForm>());

        DailyForm dailyForm = new DailyForm();
        dailyForm.setTemperature(10.0f);
        dailyForm.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user1);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        assertEquals(1, this.dailyFormServiceImpl.getUniqueNumDailyFormByDate(LocalDate.ofEpochDay(1L)));
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByDate8() {
        Company company = new Company();
        company.setQuota(0);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        Company company1 = new Company();
        company1.setQuota(0);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setCompany(company1);
        user.setNewsList(new ArrayList<News>());
        user.setFname("Fname");
        user.setLname("Lname");
        user.setEnabled(true);
        user.setBookings(new ArrayList<Bookings>());
        user.setLocked(true);
        user.setVaccinated(true);
        user.setUserRole(UserRole.EMPLOYEE);
        user.setDailyFormList(new ArrayList<DailyForm>());

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);

        ArrayList<Bookings> bookingsList = new ArrayList<Bookings>();
        bookingsList.add(bookings);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company);
        user1.setNewsList(new ArrayList<News>());
        user1.setFname("Fname");
        user1.setLname("Lname");
        user1.setEnabled(true);
        user1.setBookings(bookingsList);
        user1.setLocked(true);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(new ArrayList<DailyForm>());

        DailyForm dailyForm = new DailyForm();
        dailyForm.setTemperature(10.0f);
        dailyForm.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user1);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        assertEquals(1, this.dailyFormServiceImpl.getUniqueNumDailyFormByDate(LocalDate.ofEpochDay(1L)));
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByDate9() {
        Company company = new Company();
        company.setQuota(0);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        Company company1 = new Company();
        company1.setQuota(0);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setCompany(company1);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company);
        user1.setNewsList(new ArrayList<News>());
        user1.setFname("Fname");
        user1.setLname("Lname");
        user1.setEnabled(true);
        user1.setBookings(new ArrayList<Bookings>());
        user1.setLocked(true);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(dailyFormList);

        DailyForm dailyForm1 = new DailyForm();
        dailyForm1.setTemperature(10.0f);
        dailyForm1.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm1.setDateExactTime("2020-03-01");
        dailyForm1.setUser(user1);
        dailyForm1.setSymptoms(true);
        dailyForm1.setFID(1);

        ArrayList<DailyForm> dailyFormList1 = new ArrayList<DailyForm>();
        dailyFormList1.add(dailyForm1);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList1);
        assertEquals(1, this.dailyFormServiceImpl.getUniqueNumDailyFormByDate(LocalDate.ofEpochDay(1L)));
        verify(this.dailyFormRepository).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByWeek() {
        when(this.dailyFormRepository.findAll()).thenReturn(new ArrayList<DailyForm>());
        int[] actualUniqueNumDailyFormByWeek = this.dailyFormServiceImpl
                .getUniqueNumDailyFormByWeek(LocalDate.ofEpochDay(1L));
        assertEquals(7, actualUniqueNumDailyFormByWeek.length);
        assertEquals(0, actualUniqueNumDailyFormByWeek[0]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[1]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[2]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[3]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[4]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[5]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[6]);
        verify(this.dailyFormRepository, atLeast(1)).findAll();
        assertTrue(this.dailyFormServiceImpl.getAllDailyForms().isEmpty());
    }

    @Test
    void testGetUniqueNumDailyFormByWeek2() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        int[] actualUniqueNumDailyFormByWeek = this.dailyFormServiceImpl
                .getUniqueNumDailyFormByWeek(LocalDate.ofEpochDay(1L));
        assertEquals(7, actualUniqueNumDailyFormByWeek.length);
        assertEquals(0, actualUniqueNumDailyFormByWeek[0]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[1]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[2]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[3]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[4]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[5]);
        assertEquals(1, actualUniqueNumDailyFormByWeek[6]);
        verify(this.dailyFormRepository, atLeast(1)).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByWeek3() {
        Company company = new Company();
        company.setQuota(0);
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

        Company company1 = new Company();
        company1.setQuota(0);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm1);
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        int[] actualUniqueNumDailyFormByWeek = this.dailyFormServiceImpl
                .getUniqueNumDailyFormByWeek(LocalDate.ofEpochDay(1L));
        assertEquals(7, actualUniqueNumDailyFormByWeek.length);
        assertEquals(0, actualUniqueNumDailyFormByWeek[0]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[1]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[2]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[3]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[4]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[5]);
        assertEquals(1, actualUniqueNumDailyFormByWeek[6]);
        verify(this.dailyFormRepository, atLeast(1)).findAll();
        assertEquals(2, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByWeek4() {
        Company company = new Company();
        company.setQuota(0);
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

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user);

        Company company1 = new Company();
        company1.setQuota(0);
        company1.setUsers(userList);
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

        DailyForm dailyForm = new DailyForm();
        dailyForm.setTemperature(10.0f);
        dailyForm.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user1);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        int[] actualUniqueNumDailyFormByWeek = this.dailyFormServiceImpl
                .getUniqueNumDailyFormByWeek(LocalDate.ofEpochDay(1L));
        assertEquals(7, actualUniqueNumDailyFormByWeek.length);
        assertEquals(0, actualUniqueNumDailyFormByWeek[0]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[1]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[2]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[3]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[4]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[5]);
        assertEquals(1, actualUniqueNumDailyFormByWeek[6]);
        verify(this.dailyFormRepository, atLeast(1)).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByWeek5() {
        Company company = new Company();
        company.setQuota(0);
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

        Company company1 = new Company();
        company1.setQuota(0);
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

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user);

        Company company2 = new Company();
        company2.setQuota(0);
        company2.setUsers(userList);
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setCompany(company2);
        user2.setNewsList(new ArrayList<News>());
        user2.setFname("Fname");
        user2.setLname("Lname");
        user2.setEnabled(true);
        user2.setBookings(new ArrayList<Bookings>());
        user2.setLocked(true);
        user2.setVaccinated(true);
        user2.setUserRole(UserRole.EMPLOYEE);
        user2.setDailyFormList(new ArrayList<DailyForm>());

        DailyForm dailyForm = new DailyForm();
        dailyForm.setTemperature(10.0f);
        dailyForm.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user2);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        int[] actualUniqueNumDailyFormByWeek = this.dailyFormServiceImpl
                .getUniqueNumDailyFormByWeek(LocalDate.ofEpochDay(1L));
        assertEquals(7, actualUniqueNumDailyFormByWeek.length);
        assertEquals(0, actualUniqueNumDailyFormByWeek[0]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[1]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[2]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[3]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[4]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[5]);
        assertEquals(1, actualUniqueNumDailyFormByWeek[6]);
        verify(this.dailyFormRepository, atLeast(1)).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByWeek6() {
        Company company = new Company();
        company.setQuota(0);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        Company company1 = new Company();
        company1.setQuota(0);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        Company company2 = new Company();
        company2.setQuota(0);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setCompany(company2);
        user.setNewsList(new ArrayList<News>());
        user.setFname("Fname");
        user.setLname("Lname");
        user.setEnabled(true);
        user.setBookings(new ArrayList<Bookings>());
        user.setLocked(true);
        user.setVaccinated(true);
        user.setUserRole(UserRole.EMPLOYEE);
        user.setDailyFormList(new ArrayList<DailyForm>());

        News news = new News();
        news.setDate(LocalDate.ofEpochDay(1L));
        news.setCompany(company1);
        news.setUser(user);
        news.setNid(1);
        news.setTitle("Dr");
        news.setContent("Not all who wander are lost");

        ArrayList<News> newsList = new ArrayList<News>();
        newsList.add(news);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company);
        user1.setNewsList(newsList);
        user1.setFname("Fname");
        user1.setLname("Lname");
        user1.setEnabled(true);
        user1.setBookings(new ArrayList<Bookings>());
        user1.setLocked(true);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(new ArrayList<DailyForm>());

        DailyForm dailyForm = new DailyForm();
        dailyForm.setTemperature(10.0f);
        dailyForm.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user1);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        int[] actualUniqueNumDailyFormByWeek = this.dailyFormServiceImpl
                .getUniqueNumDailyFormByWeek(LocalDate.ofEpochDay(1L));
        assertEquals(7, actualUniqueNumDailyFormByWeek.length);
        assertEquals(0, actualUniqueNumDailyFormByWeek[0]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[1]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[2]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[3]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[4]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[5]);
        assertEquals(1, actualUniqueNumDailyFormByWeek[6]);
        verify(this.dailyFormRepository, atLeast(1)).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByWeek7() {
        Company company = new Company();
        company.setQuota(0);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        Company company1 = new Company();
        company1.setQuota(0);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setCompany(company1);
        user.setNewsList(new ArrayList<News>());
        user.setFname("Fname");
        user.setLname("Lname");
        user.setEnabled(true);
        user.setBookings(new ArrayList<Bookings>());
        user.setLocked(true);
        user.setVaccinated(true);
        user.setUserRole(UserRole.EMPLOYEE);
        user.setDailyFormList(new ArrayList<DailyForm>());

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);

        ArrayList<Bookings> bookingsList = new ArrayList<Bookings>();
        bookingsList.add(bookings);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company);
        user1.setNewsList(new ArrayList<News>());
        user1.setFname("Fname");
        user1.setLname("Lname");
        user1.setEnabled(true);
        user1.setBookings(bookingsList);
        user1.setLocked(true);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(new ArrayList<DailyForm>());

        DailyForm dailyForm = new DailyForm();
        dailyForm.setTemperature(10.0f);
        dailyForm.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm.setDateExactTime("2020-03-01");
        dailyForm.setUser(user1);
        dailyForm.setSymptoms(true);
        dailyForm.setFID(1);

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList);
        int[] actualUniqueNumDailyFormByWeek = this.dailyFormServiceImpl
                .getUniqueNumDailyFormByWeek(LocalDate.ofEpochDay(1L));
        assertEquals(7, actualUniqueNumDailyFormByWeek.length);
        assertEquals(0, actualUniqueNumDailyFormByWeek[0]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[1]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[2]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[3]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[4]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[5]);
        assertEquals(1, actualUniqueNumDailyFormByWeek[6]);
        verify(this.dailyFormRepository, atLeast(1)).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }

    @Test
    void testGetUniqueNumDailyFormByWeek8() {
        Company company = new Company();
        company.setQuota(0);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        Company company1 = new Company();
        company1.setQuota(0);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setCompany(company1);
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

        ArrayList<DailyForm> dailyFormList = new ArrayList<DailyForm>();
        dailyFormList.add(dailyForm);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company);
        user1.setNewsList(new ArrayList<News>());
        user1.setFname("Fname");
        user1.setLname("Lname");
        user1.setEnabled(true);
        user1.setBookings(new ArrayList<Bookings>());
        user1.setLocked(true);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(dailyFormList);

        DailyForm dailyForm1 = new DailyForm();
        dailyForm1.setTemperature(10.0f);
        dailyForm1.setDateTime(LocalDate.ofEpochDay(1L));
        dailyForm1.setDateExactTime("2020-03-01");
        dailyForm1.setUser(user1);
        dailyForm1.setSymptoms(true);
        dailyForm1.setFID(1);

        ArrayList<DailyForm> dailyFormList1 = new ArrayList<DailyForm>();
        dailyFormList1.add(dailyForm1);
        when(this.dailyFormRepository.findAll()).thenReturn(dailyFormList1);
        int[] actualUniqueNumDailyFormByWeek = this.dailyFormServiceImpl
                .getUniqueNumDailyFormByWeek(LocalDate.ofEpochDay(1L));
        assertEquals(7, actualUniqueNumDailyFormByWeek.length);
        assertEquals(0, actualUniqueNumDailyFormByWeek[0]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[1]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[2]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[3]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[4]);
        assertEquals(0, actualUniqueNumDailyFormByWeek[5]);
        assertEquals(1, actualUniqueNumDailyFormByWeek[6]);
        verify(this.dailyFormRepository, atLeast(1)).findAll();
        assertEquals(1, this.dailyFormServiceImpl.getAllDailyForms().size());
    }
}


package com.collab.g5.demo.bookings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.collab.g5.demo.companies.Company;
import com.collab.g5.demo.dailyForm.DailyForm;
import com.collab.g5.demo.news.News;
import com.collab.g5.demo.regulations.RegulationLimit;
import com.collab.g5.demo.users.User;
import com.collab.g5.demo.users.UserRole;
import com.collab.g5.demo.users.UserServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BookingServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BookingServiceImplTest {
    @Autowired
    private BookingServiceImpl bookingServiceImpl;

    @MockBean
    private BookingsRepository bookingsRepository;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Test
    void testGetAllBookings() {
        ArrayList<Bookings> bookingsList = new ArrayList<Bookings>();
        when(this.bookingsRepository.findAll()).thenReturn(bookingsList);
        List<Bookings> actualAllBookings = this.bookingServiceImpl.getAllBookings();
        assertSame(bookingsList, actualAllBookings);
        assertTrue(actualAllBookings.isEmpty());
        verify(this.bookingsRepository).findAll();
    }

    @Test
    void testGetAllMyBookings() {
        ArrayList<Bookings> bookingsList = new ArrayList<Bookings>();
        when(this.bookingsRepository.findBookingsByEmail((String) any())).thenReturn(bookingsList);
        List<Bookings> actualAllMyBookings = this.bookingServiceImpl.getAllMyBookings("jane.doe@example.org");
        assertSame(bookingsList, actualAllMyBookings);
        assertTrue(actualAllMyBookings.isEmpty());
        verify(this.bookingsRepository).findBookingsByEmail((String) any());
        assertTrue(this.bookingServiceImpl.getAllBookings().isEmpty());
    }

    @Test
    void testGetAllMyPastBookings() {
        ArrayList<Bookings> bookingsList = new ArrayList<Bookings>();
        when(this.bookingsRepository.findAllByUser((User) any())).thenReturn(bookingsList);

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
        List<Bookings> actualAllMyPastBookings = this.bookingServiceImpl.getAllMyPastBookings(user);
        assertSame(bookingsList, actualAllMyPastBookings);
        assertTrue(actualAllMyPastBookings.isEmpty());
        verify(this.bookingsRepository).findAllByUser((User) any());
        assertTrue(this.bookingServiceImpl.getAllBookings().isEmpty());
    }

    @Test
    void testGetAllMyPastBookings2() {
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

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);

        ArrayList<Bookings> bookingsList = new ArrayList<Bookings>();
        bookingsList.add(bookings);
        when(this.bookingsRepository.findAllByUser((User) any())).thenReturn(bookingsList);

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
        List<Bookings> actualAllMyPastBookings = this.bookingServiceImpl.getAllMyPastBookings(user1);
        assertSame(bookingsList, actualAllMyPastBookings);
        assertEquals(1, actualAllMyPastBookings.size());
        verify(this.bookingsRepository).findAllByUser((User) any());
    }

    @Test
    void testGetAllMyUpcomingBookings() {
        ArrayList<Bookings> bookingsList = new ArrayList<Bookings>();
        when(this.bookingsRepository.findAllByUser((User) any())).thenReturn(bookingsList);

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
        List<Bookings> actualAllMyUpcomingBookings = this.bookingServiceImpl.getAllMyUpcomingBookings(user);
        assertSame(bookingsList, actualAllMyUpcomingBookings);
        assertTrue(actualAllMyUpcomingBookings.isEmpty());
        verify(this.bookingsRepository).findAllByUser((User) any());
        assertTrue(this.bookingServiceImpl.getAllBookings().isEmpty());
    }

    @Test
    void testGetAllMyUpcomingBookings2() {
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

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);

        ArrayList<Bookings> bookingsList = new ArrayList<Bookings>();
        bookingsList.add(bookings);
        when(this.bookingsRepository.findAllByUser((User) any())).thenReturn(bookingsList);

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
        List<Bookings> actualAllMyUpcomingBookings = this.bookingServiceImpl.getAllMyUpcomingBookings(user1);
        assertSame(bookingsList, actualAllMyUpcomingBookings);
        assertTrue(actualAllMyUpcomingBookings.isEmpty());
        verify(this.bookingsRepository).findAllByUser((User) any());
        assertTrue(this.bookingServiceImpl.getAllBookings().isEmpty());
    }

    @Test
    void testGetBookingsCountByDate() {
        when(this.bookingsRepository.getBookingsCountByDate(anyInt(), (LocalDate) any())).thenReturn(3);
        assertEquals(3, this.bookingServiceImpl.getBookingsCountByDate(1, LocalDate.ofEpochDay(1L)));
        verify(this.bookingsRepository).getBookingsCountByDate(anyInt(), (LocalDate) any());
        assertTrue(this.bookingServiceImpl.getAllBookings().isEmpty());
    }

    @Test
    void testGetBookingsCountByUserAndMonth() {
        when(this.bookingsRepository.getBookingsCountByUserAndMonth((String) any(), anyInt())).thenReturn(3);
        assertEquals(3,
                this.bookingServiceImpl.getBookingsCountByUserAndMonth("jane.doe@example.org", LocalDate.ofEpochDay(1L)));
        verify(this.bookingsRepository).getBookingsCountByUserAndMonth((String) any(), anyInt());
        assertTrue(this.bookingServiceImpl.getAllBookings().isEmpty());
    }

    @Test
    void testAutoUpdateBookings() {
        ArrayList<User> userList = new ArrayList<User>();
        when(this.userServiceImpl.getAllUsers()).thenReturn(userList);
        doNothing().when(this.bookingsRepository).updateBookings(anyInt());
        when(this.bookingsRepository.findAll()).thenReturn(new ArrayList<Bookings>());
        this.bookingServiceImpl.autoUpdateBookings(1, LocalDate.ofEpochDay(1L));
        verify(this.userServiceImpl).getAllUsers();
        verify(this.bookingsRepository).findAll();
        verify(this.bookingsRepository).updateBookings(anyInt());
        List<Bookings> allBookings = this.bookingServiceImpl.getAllBookings();
        assertEquals(userList, allBookings);
        assertTrue(allBookings.isEmpty());
    }

    @Test
    void testAutoUpdateBookings2() {
        Company company = new Company();
        company.setQuota(0);
        ArrayList<User> userList = new ArrayList<User>();
        company.setUsers(userList);
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

        ArrayList<User> userList1 = new ArrayList<User>();
        userList1.add(user);
        when(this.userServiceImpl.getAllUsers()).thenReturn(userList1);
        doNothing().when(this.bookingsRepository).updateBookings(anyInt());
        when(this.bookingsRepository.findAll()).thenReturn(new ArrayList<Bookings>());
        this.bookingServiceImpl.autoUpdateBookings(1, LocalDate.ofEpochDay(1L));
        verify(this.userServiceImpl).getAllUsers();
        verify(this.bookingsRepository).findAll();
        verify(this.bookingsRepository).updateBookings(anyInt());
        List<Bookings> allBookings = this.bookingServiceImpl.getAllBookings();
        assertEquals(userList, allBookings);
        assertTrue(allBookings.isEmpty());
    }

    @Test
    void testAutoUpdateBookings3() {
        Company company = new Company();
        company.setQuota(0);
        ArrayList<User> userList = new ArrayList<User>();
        company.setUsers(userList);
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

        ArrayList<User> userList1 = new ArrayList<User>();
        userList1.add(user1);
        userList1.add(user);
        when(this.userServiceImpl.getAllUsers()).thenReturn(userList1);
        doNothing().when(this.bookingsRepository).updateBookings(anyInt());
        when(this.bookingsRepository.findAll()).thenReturn(new ArrayList<Bookings>());
        this.bookingServiceImpl.autoUpdateBookings(1, LocalDate.ofEpochDay(1L));
        verify(this.userServiceImpl).getAllUsers();
        verify(this.bookingsRepository).findAll();
        verify(this.bookingsRepository).updateBookings(anyInt());
        List<Bookings> allBookings = this.bookingServiceImpl.getAllBookings();
        assertEquals(userList, allBookings);
        assertTrue(allBookings.isEmpty());
    }

    @Test
    void testAutoUpdateBookings4() {
        ArrayList<User> userList = new ArrayList<User>();
        when(this.userServiceImpl.getAllUsers()).thenReturn(userList);

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

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);

        ArrayList<Bookings> bookingsList = new ArrayList<Bookings>();
        bookingsList.add(bookings);
        doNothing().when(this.bookingsRepository).updateBookings(anyInt());
        when(this.bookingsRepository.findAll()).thenReturn(bookingsList);
        this.bookingServiceImpl.autoUpdateBookings(1, LocalDate.ofEpochDay(1L));
        verify(this.userServiceImpl).getAllUsers();
        verify(this.bookingsRepository).findAll();
        verify(this.bookingsRepository).updateBookings(anyInt());
        List<Bookings> allBookings = this.bookingServiceImpl.getAllBookings();
        assertEquals(userList, allBookings);
        assertTrue(allBookings.isEmpty());
    }

    @Test
    void testAutoUpdateBookings5() {
        ArrayList<User> userList = new ArrayList<User>();
        when(this.userServiceImpl.getAllUsers()).thenReturn(userList);

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

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);

        Company company1 = new Company();
        company1.setQuota(0);
        company1.setUsers(new ArrayList<User>());
        company1.setName("1970-01-02");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company1);
        user1.setNewsList(new ArrayList<News>());
        user1.setFname("1970-01-02");
        user1.setLname("1970-01-02");
        user1.setEnabled(true);
        user1.setBookings(new ArrayList<Bookings>());
        user1.setLocked(true);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(new ArrayList<DailyForm>());

        Bookings bookings1 = new Bookings();
        bookings1.setStatus("1970-01-02");
        bookings1.setUser(user1);
        bookings1.setBDate(LocalDate.ofEpochDay(1L));
        bookings1.setBid(1);

        ArrayList<Bookings> bookingsList = new ArrayList<Bookings>();
        bookingsList.add(bookings1);
        bookingsList.add(bookings);
        doNothing().when(this.bookingsRepository).updateBookings(anyInt());
        when(this.bookingsRepository.findAll()).thenReturn(bookingsList);
        this.bookingServiceImpl.autoUpdateBookings(1, LocalDate.ofEpochDay(1L));
        verify(this.userServiceImpl).getAllUsers();
        verify(this.bookingsRepository).findAll();
        verify(this.bookingsRepository).updateBookings(anyInt());
        List<Bookings> allBookings = this.bookingServiceImpl.getAllBookings();
        assertEquals(userList, allBookings);
        assertTrue(allBookings.isEmpty());
    }

    @Test
    void testAutoUpdateBookings6() {
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
        when(this.userServiceImpl.getAllUsers()).thenReturn(userList);
        doNothing().when(this.bookingsRepository).updateBookings(anyInt());
        when(this.bookingsRepository.findAll()).thenReturn(new ArrayList<Bookings>());
        this.bookingServiceImpl.autoUpdateBookings(123, LocalDate.ofEpochDay(1L));
        verify(this.userServiceImpl).getAllUsers();
        verify(this.bookingsRepository).findAll();
        verify(this.bookingsRepository).updateBookings(anyInt());
        List<Bookings> allBookings = this.bookingServiceImpl.getAllBookings();
        assertEquals(userList, allBookings);
        assertTrue(allBookings.isEmpty());
    }

    @Test
    void testAutoUpdateBookings7() {
        ArrayList<User> userList = new ArrayList<User>();
        when(this.userServiceImpl.getAllUsers()).thenReturn(userList);

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

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user);
        bookings.setBDate(LocalDate.ofYearDay(1, 1));
        bookings.setBid(1);

        ArrayList<Bookings> bookingsList = new ArrayList<Bookings>();
        bookingsList.add(bookings);
        doNothing().when(this.bookingsRepository).updateBookings(anyInt());
        when(this.bookingsRepository.findAll()).thenReturn(bookingsList);
        this.bookingServiceImpl.autoUpdateBookings(1, LocalDate.ofEpochDay(1L));
        verify(this.userServiceImpl).getAllUsers();
        verify(this.bookingsRepository).findAll();
        verify(this.bookingsRepository).updateBookings(anyInt());
        List<Bookings> allBookings = this.bookingServiceImpl.getAllBookings();
        assertEquals(userList, allBookings);
        assertTrue(allBookings.isEmpty());
    }

    @Test
    void testGetBookingsById() {
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

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);
        Optional<Bookings> ofResult = Optional.<Bookings>of(bookings);
        when(this.bookingsRepository.findById((Integer) any())).thenReturn(ofResult);
        assertSame(bookings, this.bookingServiceImpl.getBookingsById(1));
        verify(this.bookingsRepository).findById((Integer) any());
        assertTrue(this.bookingServiceImpl.getAllBookings().isEmpty());
    }

    @Test
    void testUpdateBookings() {
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

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);
        Optional<Bookings> ofResult = Optional.<Bookings>of(bookings);

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

        Bookings bookings1 = new Bookings();
        bookings1.setStatus("Status");
        bookings1.setUser(user1);
        bookings1.setBDate(LocalDate.ofEpochDay(1L));
        bookings1.setBid(1);
        when(this.bookingsRepository.save((Bookings) any())).thenReturn(bookings1);
        when(this.bookingsRepository.findById((Integer) any())).thenReturn(ofResult);

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

        Bookings bookings2 = new Bookings();
        bookings2.setStatus("Status");
        bookings2.setUser(user2);
        bookings2.setBDate(LocalDate.ofEpochDay(1L));
        bookings2.setBid(1);
        assertSame(bookings1, this.bookingServiceImpl.updateBookings(1, bookings2));
        verify(this.bookingsRepository).findById((Integer) any());
        verify(this.bookingsRepository).save((Bookings) any());
    }

    @Test
    void testUpdateBookings2() {
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

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);
        when(this.bookingsRepository.save((Bookings) any())).thenReturn(bookings);
        when(this.bookingsRepository.findById((Integer) any())).thenReturn(Optional.<Bookings>empty());

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

        Bookings bookings1 = new Bookings();
        bookings1.setStatus("Status");
        bookings1.setUser(user1);
        bookings1.setBDate(LocalDate.ofEpochDay(1L));
        bookings1.setBid(1);
        assertNull(this.bookingServiceImpl.updateBookings(1, bookings1));
        verify(this.bookingsRepository).findById((Integer) any());
        assertTrue(this.bookingServiceImpl.getAllBookings().isEmpty());
    }

    @Test
    void testDelete() {
        doNothing().when(this.bookingsRepository).delete((Bookings) any());

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

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);
        this.bookingServiceImpl.delete(bookings);
        verify(this.bookingsRepository).delete((Bookings) any());
        assertTrue(this.bookingServiceImpl.getAllBookings().isEmpty());
    }

    @Test
    void testDeleteById() {
        doNothing().when(this.bookingsRepository).deleteById((Integer) any());
        this.bookingServiceImpl.deleteById(1);
        verify(this.bookingsRepository).deleteById((Integer) any());
        assertTrue(this.bookingServiceImpl.getAllBookings().isEmpty());
    }

    @Test
    void testCheckForDuplicateBookings() {
        when(this.bookingsRepository.checkForDuplicateBookings((String) any(), (LocalDate) any())).thenReturn(1);
        assertEquals(1,
                this.bookingServiceImpl.checkForDuplicateBookings("jane.doe@example.org", LocalDate.ofEpochDay(1L)));
        verify(this.bookingsRepository).checkForDuplicateBookings((String) any(), (LocalDate) any());
        assertTrue(this.bookingServiceImpl.getAllBookings().isEmpty());
    }

    @Test
    void testSave() {
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

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);
        when(this.bookingsRepository.save((Bookings) any())).thenReturn(bookings);

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

        Bookings bookings1 = new Bookings();
        bookings1.setStatus("Status");
        bookings1.setUser(user1);
        bookings1.setBDate(LocalDate.ofEpochDay(1L));
        bookings1.setBid(1);
        assertSame(bookings, this.bookingServiceImpl.save(bookings1));
        verify(this.bookingsRepository).save((Bookings) any());
    }

    @Test
    void testGetBookingsCountByEmail() {
        when(this.bookingsRepository.findBookingsCountByEmail((String) any())).thenReturn(3);
        assertEquals(3, this.bookingServiceImpl.getBookingsCountByEmail("jane.doe@example.org"));
        verify(this.bookingsRepository).findBookingsCountByEmail((String) any());
        assertTrue(this.bookingServiceImpl.getAllBookings().isEmpty());
    }

    @Test
    void testGetBookingByUser() {
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
        when(this.userServiceImpl.getUserByEmail((String) any())).thenReturn(user);
        when(this.bookingsRepository.findAll()).thenReturn(new ArrayList<Bookings>());
        List<Bookings> actualBookingByUser = this.bookingServiceImpl.getBookingByUser("jane.doe@example.org");
        assertTrue(actualBookingByUser.isEmpty());
        verify(this.userServiceImpl).getUserByEmail((String) any());
        verify(this.bookingsRepository).findAll();
        assertEquals(actualBookingByUser, this.bookingServiceImpl.getAllBookings());
    }

    @Test
    void testGetBookingByUser2() {
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
        when(this.userServiceImpl.getUserByEmail((String) any())).thenReturn(user);

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

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user1);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);

        ArrayList<Bookings> bookingsList = new ArrayList<Bookings>();
        bookingsList.add(bookings);
        when(this.bookingsRepository.findAll()).thenReturn(bookingsList);
        List<Bookings> actualBookingByUser = this.bookingServiceImpl.getBookingByUser("jane.doe@example.org");
        assertEquals(1, actualBookingByUser.size());
        verify(this.userServiceImpl).getUserByEmail((String) any());
        verify(this.bookingsRepository).findAll();
        assertEquals(actualBookingByUser, this.bookingServiceImpl.getAllBookings());
    }

    @Test
    void testGetBookingByUser3() {
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
        when(this.userServiceImpl.getUserByEmail((String) any())).thenReturn(user);

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

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user1);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);

        ArrayList<Bookings> bookingsList = new ArrayList<Bookings>();
        bookingsList.add(bookings);
        when(this.bookingsRepository.findAll()).thenReturn(bookingsList);
        assertTrue(this.bookingServiceImpl.getBookingByUser(null).isEmpty());
        verify(this.userServiceImpl).getUserByEmail((String) any());
        verify(this.bookingsRepository).findAll();
        assertEquals(1, this.bookingServiceImpl.getAllBookings().size());
    }
}


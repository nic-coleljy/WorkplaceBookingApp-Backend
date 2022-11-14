package com.collab.g5.demo.bookings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.collab.g5.demo.companies.Company;
import com.collab.g5.demo.companies.CompanyRepository;
import com.collab.g5.demo.companies.CompanyServiceImpl;
import com.collab.g5.demo.dailyForm.DailyForm;
import com.collab.g5.demo.email.MailService;
import com.collab.g5.demo.exceptions.bookings.BookingExistsException;
import com.collab.g5.demo.exceptions.bookings.BookingNotFoundException;
import com.collab.g5.demo.exceptions.users.UserMonthlyQuotaExceeded;
import com.collab.g5.demo.exceptions.users.UserNotFoundException;
import com.collab.g5.demo.exceptions.users.UserNotVaccinatedException;
import com.collab.g5.demo.news.News;
import com.collab.g5.demo.regulations.Regulation;
import com.collab.g5.demo.regulations.RegulationLimit;
import com.collab.g5.demo.regulations.RegulationLimitKey;
import com.collab.g5.demo.regulations.RegulationLimitRepository;
import com.collab.g5.demo.regulations.RegulationRepository;
import com.collab.g5.demo.security.JwtAuthenticationEntryPoint;
import com.collab.g5.demo.security.JwtRequestFilter;
import com.collab.g5.demo.security.WebSecurityConfig;
import com.collab.g5.demo.users.User;
import com.collab.g5.demo.users.UserRepository;
import com.collab.g5.demo.users.UserRole;
import com.collab.g5.demo.users.UserServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BookingsController.class})
@ExtendWith(SpringExtension.class)
class BookingsControllerTest {
    @MockBean
    private BookingServiceImpl bookingServiceImpl;

    @Autowired
    private BookingsController bookingsController;

    @MockBean
    private CompanyServiceImpl companyServiceImpl;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Test
    void testGetAllMyPastBookings() throws Exception {
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
        when(this.bookingServiceImpl.getAllMyPastBookings((User) any())).thenReturn(new ArrayList<Bookings>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/bookings/emp/past/{email}/",
                "jane.doe@example.org");
        MockMvcBuilders.standaloneSetup(this.bookingsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllMyUpcomingBookings() throws Exception {
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
        when(this.bookingServiceImpl.getAllMyUpcomingBookings((User) any())).thenReturn(new ArrayList<Bookings>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/bookings/emp/upcoming/{email}/",
                "jane.doe@example.org");
        MockMvcBuilders.standaloneSetup(this.bookingsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetBookingsCountByEmail() throws Exception {
        when(this.bookingServiceImpl.getBookingsCountByEmail((String) any())).thenReturn(3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/bookings/emp/{email}/",
                "jane.doe@example.org");
        MockMvcBuilders.standaloneSetup(this.bookingsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("3"));
    }

    @Test
    void testGetBookingsCountByEmail2() throws Exception {
        when(this.bookingServiceImpl.getBookingsCountByEmail((String) any())).thenReturn(3);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/bookings/emp/{email}/",
                "jane.doe@example.org");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.bookingsController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("3"));
    }

    @Test
    void testGetBookingByUser() throws Exception {
        when(this.bookingServiceImpl.getBookingByUser((String) any())).thenReturn(new ArrayList<Bookings>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/bookings/UserBookings/{email}",
                "jane.doe@example.org");
        MockMvcBuilders.standaloneSetup(this.bookingsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetBookingByUser2() throws Exception {
        when(this.bookingServiceImpl.getBookingByUser((String) any())).thenReturn(new ArrayList<Bookings>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/bookings/UserBookings/{email}",
                "jane.doe@example.org");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.bookingsController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testAddBooking() {
        BookingsRepository bookingsRepository = mock(BookingsRepository.class);
        when(bookingsRepository.checkForDuplicateBookings((String) any(), (LocalDate) any())).thenReturn(1);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        BookingServiceImpl bookingServiceImpl = new BookingServiceImpl(bookingsRepository,
                new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null)));

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
        UserRepository userRepository1 = mock(UserRepository.class);
        when(userRepository1.findByEmail((String) any())).thenReturn(Optional.<User>of(user));
        MailService mailService1 = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository1, mailService1,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService, new JwtRequestFilter(null, null)));

        BookingsController bookingsController = new BookingsController(bookingServiceImpl, userServiceImpl,
                new CompanyServiceImpl(mock(CompanyRepository.class), mock(RegulationRepository.class),
                        mock(RegulationLimitRepository.class)));

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

        Bookings bookings = new Bookings();
        bookings.setStatus("Status");
        bookings.setUser(user1);
        bookings.setBDate(LocalDate.ofEpochDay(1L));
        bookings.setBid(1);
        assertThrows(BookingExistsException.class, () -> bookingsController.addBooking(bookings));
        verify(bookingsRepository).checkForDuplicateBookings((String) any(), (LocalDate) any());
        verify(userRepository1).findByEmail((String) any());
    }

    @Test
    void testAddBooking2() {
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
        BookingsRepository bookingsRepository = mock(BookingsRepository.class);
        when(bookingsRepository.save((Bookings) any())).thenReturn(bookings);
        when(bookingsRepository.getBookingsCountByDate(anyInt(), (LocalDate) any())).thenReturn(3);
        when(bookingsRepository.getBookingsCountByUserAndMonth((String) any(), anyInt())).thenReturn(3);
        when(bookingsRepository.checkForDuplicateBookings((String) any(), (LocalDate) any())).thenReturn(0);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        BookingServiceImpl bookingServiceImpl = new BookingServiceImpl(bookingsRepository,
                new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null)));

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
        Optional<User> ofResult = Optional.<User>of(user1);
        UserRepository userRepository1 = mock(UserRepository.class);
        when(userRepository1.getVaccinatedByEmail((String) any())).thenReturn(true);
        when(userRepository1.findByEmail((String) any())).thenReturn(ofResult);
        MailService mailService1 = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository1, mailService1,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService, new JwtRequestFilter(null, null)));

        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.findStartDateBasedCustomDate((LocalDate) any())).thenReturn(LocalDate.ofEpochDay(1L));

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company2);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        when(regulationLimitRepository.getById((RegulationLimitKey) any())).thenReturn(regulationLimit);
        BookingsController bookingsController = new BookingsController(bookingServiceImpl, userServiceImpl,
                new CompanyServiceImpl(mock(CompanyRepository.class), regulationRepository, regulationLimitRepository));

        Company company3 = new Company();
        company3.setQuota(1);
        company3.setUsers(new ArrayList<User>());
        company3.setName("Name");
        company3.setSize(3L);
        company3.setRegulationLimit(new ArrayList<RegulationLimit>());
        company3.setCid(1);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setCompany(company3);
        user2.setNewsList(new ArrayList<News>());
        user2.setFname("Fname");
        user2.setLname("Lname");
        user2.setEnabled(true);
        user2.setBookings(new ArrayList<Bookings>());
        user2.setLocked(true);
        user2.setVaccinated(true);
        user2.setUserRole(UserRole.EMPLOYEE);
        user2.setDailyFormList(new ArrayList<DailyForm>());

        Bookings bookings1 = new Bookings();
        bookings1.setStatus("Status");
        bookings1.setUser(user2);
        bookings1.setBDate(LocalDate.ofEpochDay(1L));
        bookings1.setBid(1);
        assertSame(bookings, bookingsController.addBooking(bookings1));
        verify(bookingsRepository).checkForDuplicateBookings((String) any(), (LocalDate) any());
        verify(bookingsRepository).getBookingsCountByDate(anyInt(), (LocalDate) any());
        verify(bookingsRepository).getBookingsCountByUserAndMonth((String) any(), anyInt());
        verify(bookingsRepository).save((Bookings) any());
        verify(userRepository1).findByEmail((String) any());
        verify(userRepository1).getVaccinatedByEmail((String) any());
        verify(regulationRepository).findStartDateBasedCustomDate((LocalDate) any());
        verify(regulationLimitRepository).getById((RegulationLimitKey) any());
        assertEquals("pending", bookings1.getStatus());
    }

    @Test
    void testAddBooking3() {
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
        BookingsRepository bookingsRepository = mock(BookingsRepository.class);
        when(bookingsRepository.save((Bookings) any())).thenReturn(bookings);
        when(bookingsRepository.getBookingsCountByDate(anyInt(), (LocalDate) any())).thenReturn(0);
        when(bookingsRepository.getBookingsCountByUserAndMonth((String) any(), anyInt())).thenReturn(3);
        when(bookingsRepository.checkForDuplicateBookings((String) any(), (LocalDate) any())).thenReturn(0);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        BookingServiceImpl bookingServiceImpl = new BookingServiceImpl(bookingsRepository,
                new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null)));

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
        Optional<User> ofResult = Optional.<User>of(user1);
        UserRepository userRepository1 = mock(UserRepository.class);
        when(userRepository1.getVaccinatedByEmail((String) any())).thenReturn(true);
        when(userRepository1.findByEmail((String) any())).thenReturn(ofResult);
        MailService mailService1 = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository1, mailService1,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService, new JwtRequestFilter(null, null)));

        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.findStartDateBasedCustomDate((LocalDate) any())).thenReturn(LocalDate.ofEpochDay(1L));

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company2);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        when(regulationLimitRepository.getById((RegulationLimitKey) any())).thenReturn(regulationLimit);
        BookingsController bookingsController = new BookingsController(bookingServiceImpl, userServiceImpl,
                new CompanyServiceImpl(mock(CompanyRepository.class), regulationRepository, regulationLimitRepository));

        Company company3 = new Company();
        company3.setQuota(1);
        company3.setUsers(new ArrayList<User>());
        company3.setName("Name");
        company3.setSize(3L);
        company3.setRegulationLimit(new ArrayList<RegulationLimit>());
        company3.setCid(1);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setCompany(company3);
        user2.setNewsList(new ArrayList<News>());
        user2.setFname("Fname");
        user2.setLname("Lname");
        user2.setEnabled(true);
        user2.setBookings(new ArrayList<Bookings>());
        user2.setLocked(true);
        user2.setVaccinated(true);
        user2.setUserRole(UserRole.EMPLOYEE);
        user2.setDailyFormList(new ArrayList<DailyForm>());

        Bookings bookings1 = new Bookings();
        bookings1.setStatus("Status");
        bookings1.setUser(user2);
        bookings1.setBDate(LocalDate.ofEpochDay(1L));
        bookings1.setBid(1);
        assertSame(bookings, bookingsController.addBooking(bookings1));
        verify(bookingsRepository).checkForDuplicateBookings((String) any(), (LocalDate) any());
        verify(bookingsRepository).getBookingsCountByDate(anyInt(), (LocalDate) any());
        verify(bookingsRepository).getBookingsCountByUserAndMonth((String) any(), anyInt());
        verify(bookingsRepository).save((Bookings) any());
        verify(userRepository1).findByEmail((String) any());
        verify(userRepository1).getVaccinatedByEmail((String) any());
        verify(regulationRepository).findStartDateBasedCustomDate((LocalDate) any());
        verify(regulationLimitRepository).getById((RegulationLimitKey) any());
        assertEquals("Confirmed", bookings1.getStatus());
    }

    @Test
    void testAddBooking4() {
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
        BookingsRepository bookingsRepository = mock(BookingsRepository.class);
        when(bookingsRepository.save((Bookings) any())).thenReturn(bookings);
        when(bookingsRepository.getBookingsCountByDate(anyInt(), (LocalDate) any())).thenReturn(3);
        when(bookingsRepository.getBookingsCountByUserAndMonth((String) any(), anyInt())).thenReturn(Short.SIZE);
        when(bookingsRepository.checkForDuplicateBookings((String) any(), (LocalDate) any())).thenReturn(0);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        BookingServiceImpl bookingServiceImpl = new BookingServiceImpl(bookingsRepository,
                new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null)));

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
        Optional<User> ofResult = Optional.<User>of(user1);
        UserRepository userRepository1 = mock(UserRepository.class);
        when(userRepository1.getVaccinatedByEmail((String) any())).thenReturn(true);
        when(userRepository1.findByEmail((String) any())).thenReturn(ofResult);
        MailService mailService1 = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository1, mailService1,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService, new JwtRequestFilter(null, null)));

        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.findStartDateBasedCustomDate((LocalDate) any())).thenReturn(LocalDate.ofEpochDay(1L));

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company2);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        when(regulationLimitRepository.getById((RegulationLimitKey) any())).thenReturn(regulationLimit);
        BookingsController bookingsController = new BookingsController(bookingServiceImpl, userServiceImpl,
                new CompanyServiceImpl(mock(CompanyRepository.class), regulationRepository, regulationLimitRepository));

        Company company3 = new Company();
        company3.setQuota(1);
        company3.setUsers(new ArrayList<User>());
        company3.setName("Name");
        company3.setSize(3L);
        company3.setRegulationLimit(new ArrayList<RegulationLimit>());
        company3.setCid(1);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setCompany(company3);
        user2.setNewsList(new ArrayList<News>());
        user2.setFname("Fname");
        user2.setLname("Lname");
        user2.setEnabled(true);
        user2.setBookings(new ArrayList<Bookings>());
        user2.setLocked(true);
        user2.setVaccinated(true);
        user2.setUserRole(UserRole.EMPLOYEE);
        user2.setDailyFormList(new ArrayList<DailyForm>());

        Bookings bookings1 = new Bookings();
        bookings1.setStatus("Status");
        bookings1.setUser(user2);
        bookings1.setBDate(LocalDate.ofEpochDay(1L));
        bookings1.setBid(1);
        assertThrows(UserMonthlyQuotaExceeded.class, () -> bookingsController.addBooking(bookings1));
        verify(bookingsRepository).checkForDuplicateBookings((String) any(), (LocalDate) any());
        verify(bookingsRepository).getBookingsCountByUserAndMonth((String) any(), anyInt());
        verify(userRepository1).findByEmail((String) any());
        verify(userRepository1).getVaccinatedByEmail((String) any());
    }

    @Test
    void testAddBooking5() {
        BookingServiceImpl bookingServiceImpl = mock(BookingServiceImpl.class);
        when(bookingServiceImpl.checkForDuplicateBookings((String) any(), (LocalDate) any())).thenReturn(1);

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
        Optional<User> ofResult = Optional.<User>of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.getVaccinatedByEmail((String) any())).thenReturn(true);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService, new JwtRequestFilter(null, null)));

        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.findStartDateBasedCustomDate((LocalDate) any())).thenReturn(LocalDate.ofEpochDay(1L));

        Company company1 = new Company();
        company1.setQuota(1);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company1);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        when(regulationLimitRepository.getById((RegulationLimitKey) any())).thenReturn(regulationLimit);
        BookingsController bookingsController = new BookingsController(bookingServiceImpl, userServiceImpl,
                new CompanyServiceImpl(mock(CompanyRepository.class), regulationRepository, regulationLimitRepository));

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company2);
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
        assertThrows(BookingExistsException.class, () -> bookingsController.addBooking(bookings));
        verify(bookingServiceImpl).checkForDuplicateBookings((String) any(), (LocalDate) any());
        verify(userRepository).findByEmail((String) any());
    }

    @Test
    void testAddBooking6() {
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
        BookingServiceImpl bookingServiceImpl = mock(BookingServiceImpl.class);
        when(bookingServiceImpl.save((Bookings) any())).thenReturn(bookings);
        when(bookingServiceImpl.getBookingsCountByDate(anyInt(), (LocalDate) any())).thenReturn(3);
        when(bookingServiceImpl.getBookingsCountByUserAndMonth((String) any(), (LocalDate) any())).thenReturn(3);
        when(bookingServiceImpl.checkForDuplicateBookings((String) any(), (LocalDate) any())).thenReturn(0);

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
        Optional<User> ofResult = Optional.<User>of(user1);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.getVaccinatedByEmail((String) any())).thenReturn(true);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService, new JwtRequestFilter(null, null)));

        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.findStartDateBasedCustomDate((LocalDate) any())).thenReturn(LocalDate.ofEpochDay(1L));

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company2);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        when(regulationLimitRepository.getById((RegulationLimitKey) any())).thenReturn(regulationLimit);
        BookingsController bookingsController = new BookingsController(bookingServiceImpl, userServiceImpl,
                new CompanyServiceImpl(mock(CompanyRepository.class), regulationRepository, regulationLimitRepository));

        Company company3 = new Company();
        company3.setQuota(1);
        company3.setUsers(new ArrayList<User>());
        company3.setName("Name");
        company3.setSize(3L);
        company3.setRegulationLimit(new ArrayList<RegulationLimit>());
        company3.setCid(1);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setCompany(company3);
        user2.setNewsList(new ArrayList<News>());
        user2.setFname("Fname");
        user2.setLname("Lname");
        user2.setEnabled(true);
        user2.setBookings(new ArrayList<Bookings>());
        user2.setLocked(true);
        user2.setVaccinated(true);
        user2.setUserRole(UserRole.EMPLOYEE);
        user2.setDailyFormList(new ArrayList<DailyForm>());

        Bookings bookings1 = new Bookings();
        bookings1.setStatus("Status");
        bookings1.setUser(user2);
        bookings1.setBDate(LocalDate.ofEpochDay(1L));
        bookings1.setBid(1);
        assertSame(bookings, bookingsController.addBooking(bookings1));
        verify(bookingServiceImpl).checkForDuplicateBookings((String) any(), (LocalDate) any());
        verify(bookingServiceImpl).getBookingsCountByDate(anyInt(), (LocalDate) any());
        verify(bookingServiceImpl).getBookingsCountByUserAndMonth((String) any(), (LocalDate) any());
        verify(bookingServiceImpl).save((Bookings) any());
        verify(userRepository).findByEmail((String) any());
        verify(userRepository).getVaccinatedByEmail((String) any());
        verify(regulationRepository).findStartDateBasedCustomDate((LocalDate) any());
        verify(regulationLimitRepository).getById((RegulationLimitKey) any());
        assertEquals("pending", bookings1.getStatus());
    }

    @Test
    void testAddBooking7() {
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
        BookingServiceImpl bookingServiceImpl = mock(BookingServiceImpl.class);
        when(bookingServiceImpl.save((Bookings) any())).thenReturn(bookings);
        when(bookingServiceImpl.getBookingsCountByDate(anyInt(), (LocalDate) any())).thenReturn(3);
        when(bookingServiceImpl.getBookingsCountByUserAndMonth((String) any(), (LocalDate) any())).thenReturn(3);
        when(bookingServiceImpl.checkForDuplicateBookings((String) any(), (LocalDate) any())).thenReturn(0);

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
        Optional<User> ofResult = Optional.<User>of(user1);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.getVaccinatedByEmail((String) any())).thenReturn(false);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService, new JwtRequestFilter(null, null)));

        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.findStartDateBasedCustomDate((LocalDate) any())).thenReturn(LocalDate.ofEpochDay(1L));

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company2);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        when(regulationLimitRepository.getById((RegulationLimitKey) any())).thenReturn(regulationLimit);
        BookingsController bookingsController = new BookingsController(bookingServiceImpl, userServiceImpl,
                new CompanyServiceImpl(mock(CompanyRepository.class), regulationRepository, regulationLimitRepository));

        Company company3 = new Company();
        company3.setQuota(1);
        company3.setUsers(new ArrayList<User>());
        company3.setName("Name");
        company3.setSize(3L);
        company3.setRegulationLimit(new ArrayList<RegulationLimit>());
        company3.setCid(1);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setCompany(company3);
        user2.setNewsList(new ArrayList<News>());
        user2.setFname("Fname");
        user2.setLname("Lname");
        user2.setEnabled(true);
        user2.setBookings(new ArrayList<Bookings>());
        user2.setLocked(true);
        user2.setVaccinated(true);
        user2.setUserRole(UserRole.EMPLOYEE);
        user2.setDailyFormList(new ArrayList<DailyForm>());

        Bookings bookings1 = new Bookings();
        bookings1.setStatus("Status");
        bookings1.setUser(user2);
        bookings1.setBDate(LocalDate.ofEpochDay(1L));
        bookings1.setBid(1);
        assertThrows(UserNotVaccinatedException.class, () -> bookingsController.addBooking(bookings1));
        verify(bookingServiceImpl).checkForDuplicateBookings((String) any(), (LocalDate) any());
        verify(userRepository).findByEmail((String) any());
        verify(userRepository).getVaccinatedByEmail((String) any());
    }

    @Test
    void testAddBooking8() {
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
        BookingServiceImpl bookingServiceImpl = mock(BookingServiceImpl.class);
        when(bookingServiceImpl.save((Bookings) any())).thenReturn(bookings);
        when(bookingServiceImpl.getBookingsCountByDate(anyInt(), (LocalDate) any())).thenReturn(3);
        when(bookingServiceImpl.getBookingsCountByUserAndMonth((String) any(), (LocalDate) any())).thenReturn(3);
        when(bookingServiceImpl.checkForDuplicateBookings((String) any(), (LocalDate) any())).thenReturn(0);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.getVaccinatedByEmail((String) any())).thenReturn(true);
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.<User>empty());
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService, new JwtRequestFilter(null, null)));

        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.findStartDateBasedCustomDate((LocalDate) any())).thenReturn(LocalDate.ofEpochDay(1L));

        Company company1 = new Company();
        company1.setQuota(1);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company1);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        when(regulationLimitRepository.getById((RegulationLimitKey) any())).thenReturn(regulationLimit);
        BookingsController bookingsController = new BookingsController(bookingServiceImpl, userServiceImpl,
                new CompanyServiceImpl(mock(CompanyRepository.class), regulationRepository, regulationLimitRepository));

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company2);
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
        assertThrows(UserNotFoundException.class, () -> bookingsController.addBooking(bookings1));
        verify(userRepository).findByEmail((String) any());
    }

    @Test
    void testAddBooking9() {
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
        BookingServiceImpl bookingServiceImpl = mock(BookingServiceImpl.class);
        when(bookingServiceImpl.save((Bookings) any())).thenReturn(bookings);
        when(bookingServiceImpl.getBookingsCountByDate(anyInt(), (LocalDate) any())).thenReturn(3);
        when(bookingServiceImpl.getBookingsCountByUserAndMonth((String) any(), (LocalDate) any())).thenReturn(3);
        when(bookingServiceImpl.checkForDuplicateBookings((String) any(), (LocalDate) any())).thenReturn(0);

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
        UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
        when(userServiceImpl.getVaccinatedByEmail((String) any())).thenReturn(true);
        when(userServiceImpl.getUserByEmail((String) any())).thenReturn(user1);
        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.findStartDateBasedCustomDate((LocalDate) any())).thenReturn(LocalDate.ofEpochDay(1L));

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company2);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        when(regulationLimitRepository.getById((RegulationLimitKey) any())).thenReturn(regulationLimit);
        BookingsController bookingsController = new BookingsController(bookingServiceImpl, userServiceImpl,
                new CompanyServiceImpl(mock(CompanyRepository.class), regulationRepository, regulationLimitRepository));

        Company company3 = new Company();
        company3.setQuota(1);
        company3.setUsers(new ArrayList<User>());
        company3.setName("Name");
        company3.setSize(3L);
        company3.setRegulationLimit(new ArrayList<RegulationLimit>());
        company3.setCid(1);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setCompany(company3);
        user2.setNewsList(new ArrayList<News>());
        user2.setFname("Fname");
        user2.setLname("Lname");
        user2.setEnabled(true);
        user2.setBookings(new ArrayList<Bookings>());
        user2.setLocked(true);
        user2.setVaccinated(true);
        user2.setUserRole(UserRole.EMPLOYEE);
        user2.setDailyFormList(new ArrayList<DailyForm>());

        Bookings bookings1 = new Bookings();
        bookings1.setStatus("Status");
        bookings1.setUser(user2);
        bookings1.setBDate(LocalDate.ofEpochDay(1L));
        bookings1.setBid(1);
        assertSame(bookings, bookingsController.addBooking(bookings1));
        verify(bookingServiceImpl).checkForDuplicateBookings((String) any(), (LocalDate) any());
        verify(bookingServiceImpl).getBookingsCountByDate(anyInt(), (LocalDate) any());
        verify(bookingServiceImpl).getBookingsCountByUserAndMonth((String) any(), (LocalDate) any());
        verify(bookingServiceImpl).save((Bookings) any());
        verify(userServiceImpl).getUserByEmail((String) any());
        verify(userServiceImpl).getVaccinatedByEmail((String) any());
        verify(regulationRepository).findStartDateBasedCustomDate((LocalDate) any());
        verify(regulationLimitRepository).getById((RegulationLimitKey) any());
        assertEquals("pending", bookings1.getStatus());
    }

    @Test
    void testUpdateBookings() throws BookingNotFoundException {
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
        BookingsRepository bookingsRepository = mock(BookingsRepository.class);
        when(bookingsRepository.save((Bookings) any())).thenReturn(bookings1);
        when(bookingsRepository.findById((Integer) any())).thenReturn(ofResult);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        BookingServiceImpl bookingServiceImpl = new BookingServiceImpl(bookingsRepository,
                new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null)));

        UserRepository userRepository1 = mock(UserRepository.class);
        MailService mailService1 = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository1, mailService1,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService, new JwtRequestFilter(null, null)));

        BookingsController bookingsController = new BookingsController(bookingServiceImpl, userServiceImpl,
                new CompanyServiceImpl(mock(CompanyRepository.class), mock(RegulationRepository.class),
                        mock(RegulationLimitRepository.class)));

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
        assertSame(bookings1, bookingsController.updateBookings(1, bookings2));
        verify(bookingsRepository).findById((Integer) any());
        verify(bookingsRepository).save((Bookings) any());
    }

    @Test
    void testUpdateBookings2() throws BookingNotFoundException {
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
        BookingsRepository bookingsRepository = mock(BookingsRepository.class);
        when(bookingsRepository.save((Bookings) any())).thenReturn(bookings);
        when(bookingsRepository.findById((Integer) any())).thenReturn(Optional.<Bookings>empty());
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        BookingServiceImpl bookingServiceImpl = new BookingServiceImpl(bookingsRepository,
                new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null)));

        UserRepository userRepository1 = mock(UserRepository.class);
        MailService mailService1 = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository1, mailService1,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService, new JwtRequestFilter(null, null)));

        BookingsController bookingsController = new BookingsController(bookingServiceImpl, userServiceImpl,
                new CompanyServiceImpl(mock(CompanyRepository.class), mock(RegulationRepository.class),
                        mock(RegulationLimitRepository.class)));

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
        assertThrows(BookingNotFoundException.class, () -> bookingsController.updateBookings(1, bookings1));
        verify(bookingsRepository).findById((Integer) any());
    }

    @Test
    void testUpdateBookings3() throws BookingNotFoundException {
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
        BookingServiceImpl bookingServiceImpl = mock(BookingServiceImpl.class);
        when(bookingServiceImpl.updateBookings(anyInt(), (Bookings) any())).thenReturn(bookings);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService, new JwtRequestFilter(null, null)));

        BookingsController bookingsController = new BookingsController(bookingServiceImpl, userServiceImpl,
                new CompanyServiceImpl(mock(CompanyRepository.class), mock(RegulationRepository.class),
                        mock(RegulationLimitRepository.class)));

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
        assertSame(bookings, bookingsController.updateBookings(1, bookings1));
        verify(bookingServiceImpl).updateBookings(anyInt(), (Bookings) any());
    }

    @Test
    void testDeleteBooking() throws Exception {
        when(this.companyServiceImpl.getCurrentQuota(anyInt(), (LocalDate) any())).thenReturn(1);

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
        doNothing().when(this.bookingServiceImpl).autoUpdateBookings(anyInt(), (LocalDate) any());
        doNothing().when(this.bookingServiceImpl).delete((Bookings) any());
        when(this.bookingServiceImpl.getBookingsCountByDate(anyInt(), (LocalDate) any())).thenReturn(3);
        when(this.bookingServiceImpl.getBookingsById(anyInt())).thenReturn(bookings);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/bookings/hr/del/{id}", 1);
        MockMvcBuilders.standaloneSetup(this.bookingsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteBooking2() throws Exception {
        when(this.companyServiceImpl.getCurrentQuota(anyInt(), (LocalDate) any())).thenReturn(1);

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
        doNothing().when(this.bookingServiceImpl).autoUpdateBookings(anyInt(), (LocalDate) any());
        doNothing().when(this.bookingServiceImpl).delete((Bookings) any());
        when(this.bookingServiceImpl.getBookingsCountByDate(anyInt(), (LocalDate) any())).thenReturn(0);
        when(this.bookingServiceImpl.getBookingsById(anyInt())).thenReturn(bookings);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/bookings/hr/del/{id}", 1);
        MockMvcBuilders.standaloneSetup(this.bookingsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetAllForEmp() throws Exception {
        when(this.bookingServiceImpl.getAllMyBookings((String) any())).thenReturn(new ArrayList<Bookings>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/bookings/emp/allEmp/{email}/",
                "jane.doe@example.org");
        MockMvcBuilders.standaloneSetup(this.bookingsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllForEmp2() throws Exception {
        when(this.bookingServiceImpl.getBookingsCountByEmail((String) any())).thenReturn(3);
        when(this.bookingServiceImpl.getAllMyBookings((String) any())).thenReturn(new ArrayList<Bookings>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/bookings/emp/allEmp/{email}/", "",
                "Uri Vars");
        MockMvcBuilders.standaloneSetup(this.bookingsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("3"));
    }

    @Test
    void testGetBookings() throws Exception {
        when(this.bookingServiceImpl.getAllBookings()).thenReturn(new ArrayList<Bookings>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/bookings/hr");
        MockMvcBuilders.standaloneSetup(this.bookingsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetBookings2() throws Exception {
        when(this.bookingServiceImpl.getAllBookings()).thenReturn(new ArrayList<Bookings>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/bookings/hr");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.bookingsController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}


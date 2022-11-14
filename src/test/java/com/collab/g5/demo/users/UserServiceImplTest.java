package com.collab.g5.demo.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.collab.g5.demo.bookings.Bookings;
import com.collab.g5.demo.companies.Company;
import com.collab.g5.demo.dailyForm.DailyForm;
import com.collab.g5.demo.email.MailService;
import com.collab.g5.demo.news.News;
import com.collab.g5.demo.regulations.RegulationLimit;
import com.collab.g5.demo.security.JwtAuthenticationEntryPoint;
import com.collab.g5.demo.security.JwtRequestFilter;
import com.collab.g5.demo.security.JwtTokenUtil;
import com.collab.g5.demo.security.JwtUserDetailsService;
import com.collab.g5.demo.security.WebSecurityConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

class UserServiceImplTest {
    @Test
    void testForgetPassword() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.getById((String) any())).thenReturn(user);
        MailService mailService = mock(MailService.class);
        doNothing().when(mailService).sendEmail((com.collab.g5.demo.email.Mail) any());
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));
        userServiceImpl.forgetPassword("jane.doe@example.org");
        verify(userRepository).getById((String) any());
        verify(mailService).sendEmail((com.collab.g5.demo.email.Mail) any());
        assertTrue(userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testAddNewUser() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(user);
        MailService mailService = mock(MailService.class);
        doNothing().when(mailService).sendEmail((com.collab.g5.demo.email.Mail) any());
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));

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
        userServiceImpl.addNewUser(user1);
        verify(userRepository).save((User) any());
        verify(mailService).sendEmail((com.collab.g5.demo.email.Mail) any());
        assertFalse(user1.getVaccinated());
        assertTrue(userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testGetAllUsers() {
        UserRepository userRepository = mock(UserRepository.class);
        ArrayList<User> userList = new ArrayList<User>();
        when(userRepository.findAll()).thenReturn(userList);
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        List<User> actualAllUsers = (new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())))).getAllUsers();
        assertSame(userList, actualAllUsers);
        assertTrue(actualAllUsers.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserByEmail() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.<User>of(user));
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));
        assertSame(user, userServiceImpl.getUserByEmail("jane.doe@example.org"));
        verify(userRepository).findByEmail((String) any());
        assertTrue(userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testGetUserByEmail2() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.<User>empty());
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));
        assertNull(userServiceImpl.getUserByEmail("jane.doe@example.org"));
        verify(userRepository).findByEmail((String) any());
        assertTrue(userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testDelete() {
        UserRepository userRepository = mock(UserRepository.class);
        doNothing().when(userRepository).delete((User) any());
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));

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
        userServiceImpl.delete(user);
        verify(userRepository).delete((User) any());
        assertTrue(userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testDeleteById() {
        UserRepository userRepository = mock(UserRepository.class);
        doNothing().when(userRepository).deleteById((String) any());
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));
        userServiceImpl.deleteById("jane.doe@example.org");
        verify(userRepository).deleteById((String) any());
        assertTrue(userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testGetVaccinatedByEmail() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.getVaccinatedByEmail((String) any())).thenReturn(true);
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));
        assertTrue(userServiceImpl.getVaccinatedByEmail("jane.doe@example.org"));
        verify(userRepository).getVaccinatedByEmail((String) any());
        assertTrue(userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testGetVaccinatedByEmail2() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.getVaccinatedByEmail((String) any())).thenReturn(false);
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));
        assertFalse(userServiceImpl.getVaccinatedByEmail("jane.doe@example.org"));
        verify(userRepository).getVaccinatedByEmail((String) any());
        assertTrue(userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testUpdatePassword() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.getById((String) any())).thenReturn(user);
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));

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
        assertSame(user, userServiceImpl.updatePassword("iloveyou", user2));
        verify(userRepository).getById((String) any());
        verify(userRepository).save((User) any());
    }

    @Test
    void testUpdateFname() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.getById((String) any())).thenReturn(user);
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));

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
        User actualUpdateFnameResult = userServiceImpl.updateFname("F Name", user2);
        assertSame(user, actualUpdateFnameResult);
        assertEquals("F Name", actualUpdateFnameResult.getFname());
        verify(userRepository).getById((String) any());
        verify(userRepository).save((User) any());
    }

    @Test
    void testUpdateLName() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.getById((String) any())).thenReturn(user);
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));

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
        User actualUpdateLNameResult = userServiceImpl.updateLName("L Name", user2);
        assertSame(user, actualUpdateLNameResult);
        assertEquals("L Name", actualUpdateLNameResult.getLname());
        verify(userRepository).getById((String) any());
        verify(userRepository).save((User) any());
    }

    @Test
    void testUpdateEmail() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.getById((String) any())).thenReturn(user);
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));

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
        User actualUpdateEmailResult = userServiceImpl.updateEmail("jane.doe@example.org", user2);
        assertSame(user, actualUpdateEmailResult);
        assertEquals("jane.doe@example.org", actualUpdateEmailResult.getUsername());
        verify(userRepository).getById((String) any());
        verify(userRepository).save((User) any());
    }

    @Test
    void testUpdateVaccination() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.getById((String) any())).thenReturn(user);
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));

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
        User actualUpdateVaccinationResult = userServiceImpl.updateVaccination(true, user2);
        assertSame(user, actualUpdateVaccinationResult);
        assertTrue(actualUpdateVaccinationResult.getVaccinated());
        verify(userRepository).getById((String) any());
        verify(userRepository).save((User) any());
    }

    @Test
    void testSetForgetPassword() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));
        assertSame(user, userServiceImpl.setForgetPassword("jane.doe@example.org", "iloveyou"));
        verify(userRepository).findByEmail((String) any());
        verify(userRepository).save((User) any());
        assertTrue(userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testSetForgetPassword2() {
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.<User>empty());
        MailService mailService = mock(MailService.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        JdbcDaoImpl jwtUserDetailsService = new JdbcDaoImpl();
        JwtUserDetailsService jwtUserDetailsService1 = new JwtUserDetailsService(mock(UserRepository.class));
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, mailService,
                new WebSecurityConfig(jwtAuthenticationEntryPoint, jwtUserDetailsService,
                        new JwtRequestFilter(jwtUserDetailsService1, new JwtTokenUtil())));
        assertNull(userServiceImpl.setForgetPassword("jane.doe@example.org", "iloveyou"));
        verify(userRepository).findByEmail((String) any());
        assertTrue(userServiceImpl.getAllUsers().isEmpty());
    }
}


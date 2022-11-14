package com.collab.g5.demo.regulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.collab.g5.demo.companies.Company;
import com.collab.g5.demo.companies.CompanyRepository;
import com.collab.g5.demo.companies.CompanyServiceImpl;
import com.collab.g5.demo.email.MailService;
import com.collab.g5.demo.regulations.*;
import com.collab.g5.demo.security.WebSecurityConfig;
import com.collab.g5.demo.users.User;
import com.collab.g5.demo.users.UserRepository;
import com.collab.g5.demo.users.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RegulationLimitController.class})
@ExtendWith(SpringExtension.class)
class RegulationLimitControllerTest {
    @MockBean
    private CompanyServiceImpl companyServiceImpl;

    @Autowired
    private RegulationLimitController regulationLimitController;

    @MockBean
    private RegulationLimitServiceImpl regulationLimitServiceImpl;

    @MockBean
    private RegulationServiceImpl regulationServiceImpl;

    @Test
    void testGetRegulationLimitById() throws Exception {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        Optional<RegulationLimit> ofResult = Optional.<RegulationLimit>of(regulationLimit);
        when(this.regulationLimitServiceImpl.getRegulationLimitById((LocalDate) any(), anyInt())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/regulationLimit/emp/{startDate}/{cid}", LocalDate.ofEpochDay(1L), 1);
        MockMvcBuilders.standaloneSetup(this.regulationLimitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"regulationLimitKey\":{\"startDate\":[1970,1,2],\"cid\":1},\"dailyLimit\":1}"));
    }

    @Test
    void testGetRegulationLimitById2() throws Exception {
        when(this.regulationLimitServiceImpl.getRegulationLimitById((LocalDate) any(), anyInt()))
                .thenReturn(Optional.<RegulationLimit>empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/regulationLimit/emp/{startDate}/{cid}", LocalDate.ofEpochDay(1L), 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.regulationLimitController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetCurrentRegulationLimitById() throws Exception {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        when(this.regulationLimitServiceImpl.getCurrentRegulationLimitById(anyInt())).thenReturn(regulationLimit);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/regulationLimit/emp/{cid}", 1);
        MockMvcBuilders.standaloneSetup(this.regulationLimitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"regulationLimitKey\":{\"startDate\":[1970,1,2],\"cid\":1},\"dailyLimit\":1}"));
    }

    @Test
    void testGetCurrentRegulationLimitByUser() throws Exception {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        when(this.regulationLimitServiceImpl.getCurrentRegulationLimitByUser((String) any())).thenReturn(regulationLimit);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/regulationLimit/emp/num/{email}",
                "jane.doe@example.org");
        MockMvcBuilders.standaloneSetup(this.regulationLimitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }

    @Test
    void testAddRegulationLimit() {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        when(regulationLimitRepository.save((RegulationLimit) any())).thenReturn(regulationLimit);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        RegulationLimitServiceImpl regulationLimitServiceImpl = new RegulationLimitServiceImpl(regulationLimitRepository,
                new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null)));

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);
        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.findById((LocalDate) any())).thenReturn(Optional.<Regulation>of(regulation1));
        RegulationServiceImpl regulationServiceImpl = new RegulationServiceImpl(regulationRepository);

        Company company1 = new Company();
        company1.setQuota(1);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.findById((Integer) any())).thenReturn(Optional.<Company>of(company1));
        RegulationLimitController regulationLimitController = new RegulationLimitController(regulationLimitServiceImpl,
                regulationServiceImpl, new CompanyServiceImpl(companyRepository, mock(RegulationRepository.class),
                mock(RegulationLimitRepository.class)));

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        RegulationLimitKey regulationLimitKey1 = new RegulationLimitKey();
        regulationLimitKey1.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey1.setCid(1);

        Regulation regulation2 = new Regulation();
        regulation2.setStartDate(LocalDate.ofEpochDay(1L));
        regulation2.setEndDate(LocalDate.ofEpochDay(1L));
        regulation2.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation2.setPercentage(1);

        RegulationLimit regulationLimit1 = new RegulationLimit();
        regulationLimit1.setCompany(company2);
        regulationLimit1.setRegulationLimitKey(regulationLimitKey1);
        regulationLimit1.setRegulation(regulation2);
        regulationLimit1.setDailyLimit(1);
        assertSame(regulationLimit, regulationLimitController.addRegulationLimit(regulationLimit1));
        verify(regulationLimitRepository).save((RegulationLimit) any());
        verify(regulationRepository).findById((LocalDate) any());
        verify(companyRepository).findById((Integer) any());
        assertEquals(company2, regulationLimit1.getCompany());
        assertEquals(regulation2, regulationLimit1.getRegulation());
        assertTrue(regulationLimitController.getRegulationLimits().isEmpty());
    }

    @Test
    void testAddRegulationLimit2() {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        RegulationLimitServiceImpl regulationLimitServiceImpl = mock(RegulationLimitServiceImpl.class);
        when(regulationLimitServiceImpl.save((RegulationLimit) any())).thenReturn(regulationLimit);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);
        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.findById((LocalDate) any())).thenReturn(Optional.<Regulation>of(regulation1));
        RegulationServiceImpl regulationServiceImpl = new RegulationServiceImpl(regulationRepository);

        Company company1 = new Company();
        company1.setQuota(1);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.findById((Integer) any())).thenReturn(Optional.<Company>of(company1));
        RegulationLimitController regulationLimitController = new RegulationLimitController(regulationLimitServiceImpl,
                regulationServiceImpl, new CompanyServiceImpl(companyRepository, mock(RegulationRepository.class),
                mock(RegulationLimitRepository.class)));

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        RegulationLimitKey regulationLimitKey1 = new RegulationLimitKey();
        regulationLimitKey1.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey1.setCid(1);

        Regulation regulation2 = new Regulation();
        regulation2.setStartDate(LocalDate.ofEpochDay(1L));
        regulation2.setEndDate(LocalDate.ofEpochDay(1L));
        regulation2.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation2.setPercentage(1);

        RegulationLimit regulationLimit1 = new RegulationLimit();
        regulationLimit1.setCompany(company2);
        regulationLimit1.setRegulationLimitKey(regulationLimitKey1);
        regulationLimit1.setRegulation(regulation2);
        regulationLimit1.setDailyLimit(1);
        assertSame(regulationLimit, regulationLimitController.addRegulationLimit(regulationLimit1));
        verify(regulationLimitServiceImpl).save((RegulationLimit) any());
        verify(regulationRepository).findById((LocalDate) any());
        verify(companyRepository).findById((Integer) any());
        assertEquals(company2, regulationLimit1.getCompany());
        assertEquals(regulation2, regulationLimit1.getRegulation());
        assertTrue(regulationLimitController.getRegulationLimits().isEmpty());
    }

    @Test
    void testAddRegulationLimit3() {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        RegulationLimitServiceImpl regulationLimitServiceImpl = mock(RegulationLimitServiceImpl.class);
        when(regulationLimitServiceImpl.save((RegulationLimit) any())).thenReturn(regulationLimit);
        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.findById((LocalDate) any())).thenReturn(Optional.<Regulation>empty());
        RegulationServiceImpl regulationServiceImpl = new RegulationServiceImpl(regulationRepository);

        Company company1 = new Company();
        company1.setQuota(1);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.findById((Integer) any())).thenReturn(Optional.<Company>of(company1));
        RegulationLimitController regulationLimitController = new RegulationLimitController(regulationLimitServiceImpl,
                regulationServiceImpl, new CompanyServiceImpl(companyRepository, mock(RegulationRepository.class),
                mock(RegulationLimitRepository.class)));

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        RegulationLimitKey regulationLimitKey1 = new RegulationLimitKey();
        regulationLimitKey1.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey1.setCid(1);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);

        RegulationLimit regulationLimit1 = new RegulationLimit();
        regulationLimit1.setCompany(company2);
        regulationLimit1.setRegulationLimitKey(regulationLimitKey1);
        regulationLimit1.setRegulation(regulation1);
        regulationLimit1.setDailyLimit(1);
        assertSame(regulationLimit, regulationLimitController.addRegulationLimit(regulationLimit1));
        verify(regulationLimitServiceImpl).save((RegulationLimit) any());
        verify(regulationRepository).findById((LocalDate) any());
        verify(companyRepository).findById((Integer) any());
        assertEquals(company2, regulationLimit1.getCompany());
        assertNull(regulationLimit1.getRegulation());
        assertTrue(regulationLimitController.getRegulationLimits().isEmpty());
    }

    @Test
    void testAddRegulationLimit4() {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        RegulationLimitServiceImpl regulationLimitServiceImpl = mock(RegulationLimitServiceImpl.class);
        when(regulationLimitServiceImpl.save((RegulationLimit) any())).thenReturn(regulationLimit);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);
        RegulationServiceImpl regulationServiceImpl = mock(RegulationServiceImpl.class);
        when(regulationServiceImpl.getRegulationById((LocalDate) any())).thenReturn(regulation1);

        Company company1 = new Company();
        company1.setQuota(1);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.findById((Integer) any())).thenReturn(Optional.<Company>of(company1));
        RegulationLimitController regulationLimitController = new RegulationLimitController(regulationLimitServiceImpl,
                regulationServiceImpl, new CompanyServiceImpl(companyRepository, mock(RegulationRepository.class),
                mock(RegulationLimitRepository.class)));

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        RegulationLimitKey regulationLimitKey1 = new RegulationLimitKey();
        regulationLimitKey1.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey1.setCid(1);

        Regulation regulation2 = new Regulation();
        regulation2.setStartDate(LocalDate.ofEpochDay(1L));
        regulation2.setEndDate(LocalDate.ofEpochDay(1L));
        regulation2.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation2.setPercentage(1);

        RegulationLimit regulationLimit1 = new RegulationLimit();
        regulationLimit1.setCompany(company2);
        regulationLimit1.setRegulationLimitKey(regulationLimitKey1);
        regulationLimit1.setRegulation(regulation2);
        regulationLimit1.setDailyLimit(1);
        assertSame(regulationLimit, regulationLimitController.addRegulationLimit(regulationLimit1));
        verify(regulationLimitServiceImpl).save((RegulationLimit) any());
        verify(regulationServiceImpl).getRegulationById((LocalDate) any());
        verify(companyRepository).findById((Integer) any());
        assertEquals(company2, regulationLimit1.getCompany());
        assertEquals(regulation2, regulationLimit1.getRegulation());
        assertTrue(regulationLimitController.getRegulationLimits().isEmpty());
    }

    @Test
    void testAddRegulationLimit5() {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        RegulationLimitServiceImpl regulationLimitServiceImpl = mock(RegulationLimitServiceImpl.class);
        when(regulationLimitServiceImpl.save((RegulationLimit) any())).thenReturn(regulationLimit);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);
        RegulationServiceImpl regulationServiceImpl = mock(RegulationServiceImpl.class);
        when(regulationServiceImpl.getRegulationById((LocalDate) any())).thenReturn(regulation1);

        Company company1 = new Company();
        company1.setQuota(1);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);
        CompanyServiceImpl companyServiceImpl = mock(CompanyServiceImpl.class);
        when(companyServiceImpl.getCompanyById(anyInt())).thenReturn(company1);
        RegulationLimitController regulationLimitController = new RegulationLimitController(regulationLimitServiceImpl,
                regulationServiceImpl, companyServiceImpl);

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        RegulationLimitKey regulationLimitKey1 = new RegulationLimitKey();
        regulationLimitKey1.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey1.setCid(1);

        Regulation regulation2 = new Regulation();
        regulation2.setStartDate(LocalDate.ofEpochDay(1L));
        regulation2.setEndDate(LocalDate.ofEpochDay(1L));
        regulation2.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation2.setPercentage(1);

        RegulationLimit regulationLimit1 = new RegulationLimit();
        regulationLimit1.setCompany(company2);
        regulationLimit1.setRegulationLimitKey(regulationLimitKey1);
        regulationLimit1.setRegulation(regulation2);
        regulationLimit1.setDailyLimit(1);
        assertSame(regulationLimit, regulationLimitController.addRegulationLimit(regulationLimit1));
        verify(regulationLimitServiceImpl).save((RegulationLimit) any());
        verify(regulationServiceImpl).getRegulationById((LocalDate) any());
        verify(companyServiceImpl).getCompanyById(anyInt());
        assertEquals(company2, regulationLimit1.getCompany());
        assertEquals(regulation2, regulationLimit1.getRegulation());
        assertTrue(regulationLimitController.getRegulationLimits().isEmpty());
    }

    @Test
    void testUpdateRegulationLimit() throws Exception {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        RegulationLimitKey regulationLimitKey = new RegulationLimitKey();
        regulationLimitKey.setStartDate(LocalDate.ofEpochDay(1L));
        regulationLimitKey.setCid(1);

        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation);
        regulationLimit.setDailyLimit(1);
        when(this.regulationLimitServiceImpl.updateRegulationLimit((LocalDate) any(), anyInt(), (RegulationLimit) any()))
                .thenReturn(regulationLimit);

        Company company1 = new Company();
        company1.setQuota(1);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        RegulationLimitKey regulationLimitKey1 = new RegulationLimitKey();
        regulationLimitKey1.setStartDate(null);
        regulationLimitKey1.setCid(1);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);

        RegulationLimit regulationLimit1 = new RegulationLimit();
        regulationLimit1.setCompany(company1);
        regulationLimit1.setRegulationLimitKey(regulationLimitKey1);
        regulationLimit1.setRegulation(regulation1);
        regulationLimit1.setDailyLimit(1);
        String content = (new ObjectMapper()).writeValueAsString(regulationLimit1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/regulationLimit/hr/{startDate}/{cid}", LocalDate.ofEpochDay(1L), 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.regulationLimitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"regulationLimitKey\":{\"startDate\":[1970,1,2],\"cid\":1},\"dailyLimit\":1}"));
    }

    @Test
    void testDeleteRegulationLimitById() throws Exception {
        doNothing().when(this.regulationLimitServiceImpl).deleteRegulationLimitById((LocalDate) any(), anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/regulationLimit/hr/{startDate}/{cid}", LocalDate.ofEpochDay(1L), 1);
        MockMvcBuilders.standaloneSetup(this.regulationLimitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteRegulationLimitById2() throws Exception {
        doThrow(new EmptyResultDataAccessException(3)).when(this.regulationLimitServiceImpl)
                .deleteRegulationLimitById((LocalDate) any(), anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/regulationLimit/hr/{startDate}/{cid}", LocalDate.ofEpochDay(1L), 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.regulationLimitController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteRegulationLimitById3() throws Exception {
        doNothing().when(this.regulationLimitServiceImpl).deleteRegulationLimitById((LocalDate) any(), anyInt());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders
                .delete("/api/regulationLimit/hr/{startDate}/{cid}", LocalDate.ofEpochDay(1L), 1);
        deleteResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.regulationLimitController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetRegulationLimits() throws Exception {
        when(this.regulationLimitServiceImpl.getAllRegulationLimit()).thenReturn(new ArrayList<RegulationLimit>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/regulationLimit/emp");
        MockMvcBuilders.standaloneSetup(this.regulationLimitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetRegulationLimits2() throws Exception {
        when(this.regulationLimitServiceImpl.getAllRegulationLimit()).thenReturn(new ArrayList<RegulationLimit>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/regulationLimit/emp");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.regulationLimitController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}


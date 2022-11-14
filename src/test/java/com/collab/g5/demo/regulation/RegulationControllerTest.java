package com.collab.g5.demo.regulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.collab.g5.demo.companies.Company;
import com.collab.g5.demo.companies.CompanyRepository;
import com.collab.g5.demo.companies.CompanyServiceImpl;
import com.collab.g5.demo.email.MailService;
import com.collab.g5.demo.exceptions.regulations.RegulationExistsException;
import com.collab.g5.demo.exceptions.regulations.RegulationInvalidDateException;
import com.collab.g5.demo.exceptions.regulations.RegulationNotFoundException;
import com.collab.g5.demo.regulations.*;
import com.collab.g5.demo.security.WebSecurityConfig;
import com.collab.g5.demo.users.User;
import com.collab.g5.demo.users.UserRepository;
import com.collab.g5.demo.users.UserServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RegulationController.class})
@ExtendWith(SpringExtension.class)
class RegulationControllerTest {
    @MockBean
    private CompanyServiceImpl companyServiceImpl;

    @Autowired
    private RegulationController regulationController;

    @MockBean
    private RegulationLimitServiceImpl regulationLimitServiceImpl;

    @MockBean
    private RegulationServiceImpl regulationServiceImpl;

    @Test
    void testGetRegulationById() throws Exception {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);
        when(this.regulationServiceImpl.getRegulationById((LocalDate) any())).thenReturn(regulation);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/regulation/emp/{startDate}",
                LocalDate.ofEpochDay(1L));
        MockMvcBuilders.standaloneSetup(this.regulationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"startDate\":[1970,1,2],\"endDate\":[1970,1,2],\"percentage\":1}"));
    }

    @Test
    void testGetRegulationById2() throws Exception {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);
        when(this.regulationServiceImpl.getAllRegulation()).thenReturn(new ArrayList<Regulation>());
        when(this.regulationServiceImpl.getRegulationById((LocalDate) any())).thenReturn(regulation);
        LocalDate.ofEpochDay(1L);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/regulation/emp/{startDate}", null,
                null);
        MockMvcBuilders.standaloneSetup(this.regulationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testAddRegulation() {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        ArrayList<RegulationLimit> regulationLimitList = new ArrayList<RegulationLimit>();
        regulation.setRegulationLimits(regulationLimitList);
        regulation.setPercentage(1);
        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.save((Regulation) any())).thenReturn(regulation);
        when(regulationRepository.findAll()).thenReturn(new ArrayList<Regulation>());
        RegulationServiceImpl regulationServiceImpl = new RegulationServiceImpl(regulationRepository);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.findAll()).thenReturn(new ArrayList<Company>());
        CompanyServiceImpl companyServiceImpl = new CompanyServiceImpl(companyRepository, mock(RegulationRepository.class),
                mock(RegulationLimitRepository.class));

        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        RegulationController regulationController = new RegulationController(regulationServiceImpl,
                new RegulationLimitServiceImpl(regulationLimitRepository,
                        new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null))),
                companyServiceImpl);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);
        regulationController.addRegulation(regulation1);
        verify(regulationRepository).findAll();
        verify(regulationRepository).save((Regulation) any());
        verify(companyRepository).findAll();
        assertEquals(regulationLimitList, regulationController.getRegulations());
    }

    @Test
    void testAddRegulation2() {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(0);

        ArrayList<Regulation> regulationList = new ArrayList<Regulation>();
        regulationList.add(regulation);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);
        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.save((Regulation) any())).thenReturn(regulation1);
        when(regulationRepository.findAll()).thenReturn(regulationList);
        RegulationServiceImpl regulationServiceImpl = new RegulationServiceImpl(regulationRepository);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.findAll()).thenReturn(new ArrayList<Company>());
        CompanyServiceImpl companyServiceImpl = new CompanyServiceImpl(companyRepository, mock(RegulationRepository.class),
                mock(RegulationLimitRepository.class));

        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        RegulationController regulationController = new RegulationController(regulationServiceImpl,
                new RegulationLimitServiceImpl(regulationLimitRepository,
                        new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null))),
                companyServiceImpl);

        Regulation regulation2 = new Regulation();
        regulation2.setStartDate(LocalDate.ofEpochDay(1L));
        regulation2.setEndDate(LocalDate.ofEpochDay(1L));
        regulation2.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation2.setPercentage(1);
        assertThrows(RegulationExistsException.class, () -> regulationController.addRegulation(regulation2));
        verify(regulationRepository).findAll();
    }

    @Test
    void testAddRegulation3() {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);
        RegulationServiceImpl regulationServiceImpl = mock(RegulationServiceImpl.class);
        when(regulationServiceImpl.save((Regulation) any())).thenReturn(regulation);
        when(regulationServiceImpl.getAllRegulation()).thenReturn(new ArrayList<Regulation>());
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        ArrayList<Company> companyList = new ArrayList<Company>();
        when(companyRepository.findAll()).thenReturn(companyList);
        CompanyServiceImpl companyServiceImpl = new CompanyServiceImpl(companyRepository, mock(RegulationRepository.class),
                mock(RegulationLimitRepository.class));

        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        RegulationController regulationController = new RegulationController(regulationServiceImpl,
                new RegulationLimitServiceImpl(regulationLimitRepository,
                        new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null))),
                companyServiceImpl);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);
        regulationController.addRegulation(regulation1);
        verify(regulationServiceImpl).getAllRegulation();
        verify(regulationServiceImpl).save((Regulation) any());
        verify(companyRepository).findAll();
        assertEquals(companyList, regulationController.getRegulations());
    }

    @Test
    void testAddRegulation4() {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        ArrayList<RegulationLimit> regulationLimitList = new ArrayList<RegulationLimit>();
        regulation.setRegulationLimits(regulationLimitList);
        regulation.setPercentage(1);
        RegulationServiceImpl regulationServiceImpl = mock(RegulationServiceImpl.class);
        when(regulationServiceImpl.save((Regulation) any())).thenReturn(regulation);
        when(regulationServiceImpl.getAllRegulation()).thenReturn(new ArrayList<Regulation>());

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

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation1);
        regulationLimit.setDailyLimit(1);
        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        when(regulationLimitRepository.save((RegulationLimit) any())).thenReturn(regulationLimit);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        RegulationLimitServiceImpl regulationLimitServiceImpl = new RegulationLimitServiceImpl(regulationLimitRepository,
                new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null)));

        Company company1 = new Company();
        company1.setQuota(0);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        ArrayList<Company> companyList = new ArrayList<Company>();
        companyList.add(company1);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.findAll()).thenReturn(companyList);
        RegulationController regulationController = new RegulationController(regulationServiceImpl,
                regulationLimitServiceImpl, new CompanyServiceImpl(companyRepository, mock(RegulationRepository.class),
                mock(RegulationLimitRepository.class)));

        Regulation regulation2 = new Regulation();
        regulation2.setStartDate(LocalDate.ofEpochDay(1L));
        regulation2.setEndDate(LocalDate.ofEpochDay(1L));
        regulation2.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation2.setPercentage(1);
        regulationController.addRegulation(regulation2);
        verify(regulationServiceImpl).getAllRegulation();
        verify(regulationServiceImpl).save((Regulation) any());
        verify(regulationLimitRepository).save((RegulationLimit) any());
        verify(companyRepository).findAll();
        assertEquals(regulationLimitList, regulationController.getRegulations());
    }

    @Test
    void testAddRegulation5() {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        ArrayList<RegulationLimit> regulationLimitList = new ArrayList<RegulationLimit>();
        regulation.setRegulationLimits(regulationLimitList);
        regulation.setPercentage(1);
        RegulationServiceImpl regulationServiceImpl = mock(RegulationServiceImpl.class);
        when(regulationServiceImpl.save((Regulation) any())).thenReturn(regulation);
        when(regulationServiceImpl.getAllRegulation()).thenReturn(new ArrayList<Regulation>());

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

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation1);
        regulationLimit.setDailyLimit(1);
        RegulationLimitServiceImpl regulationLimitServiceImpl = mock(RegulationLimitServiceImpl.class);
        when(regulationLimitServiceImpl.save((RegulationLimit) any())).thenReturn(regulationLimit);

        Company company1 = new Company();
        company1.setQuota(0);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        ArrayList<Company> companyList = new ArrayList<Company>();
        companyList.add(company1);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.findAll()).thenReturn(companyList);
        RegulationController regulationController = new RegulationController(regulationServiceImpl,
                regulationLimitServiceImpl, new CompanyServiceImpl(companyRepository, mock(RegulationRepository.class),
                mock(RegulationLimitRepository.class)));

        Regulation regulation2 = new Regulation();
        regulation2.setStartDate(LocalDate.ofEpochDay(1L));
        regulation2.setEndDate(LocalDate.ofEpochDay(1L));
        regulation2.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation2.setPercentage(1);
        regulationController.addRegulation(regulation2);
        verify(regulationServiceImpl).getAllRegulation();
        verify(regulationServiceImpl).save((Regulation) any());
        verify(regulationLimitServiceImpl).save((RegulationLimit) any());
        verify(companyRepository).findAll();
        assertEquals(regulationLimitList, regulationController.getRegulations());
    }

    @Test
    void testAddRegulation6() {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);
        RegulationServiceImpl regulationServiceImpl = mock(RegulationServiceImpl.class);
        when(regulationServiceImpl.save((Regulation) any())).thenReturn(regulation);
        when(regulationServiceImpl.getAllRegulation()).thenReturn(new ArrayList<Regulation>());

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

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);

        RegulationLimit regulationLimit = new RegulationLimit();
        regulationLimit.setCompany(company);
        regulationLimit.setRegulationLimitKey(regulationLimitKey);
        regulationLimit.setRegulation(regulation1);
        regulationLimit.setDailyLimit(1);
        RegulationLimitServiceImpl regulationLimitServiceImpl = mock(RegulationLimitServiceImpl.class);
        when(regulationLimitServiceImpl.save((RegulationLimit) any())).thenReturn(regulationLimit);

        Company company1 = new Company();
        company1.setQuota(0);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);

        ArrayList<Company> companyList = new ArrayList<Company>();
        companyList.add(company1);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(companyRepository.findAll()).thenReturn(companyList);
        RegulationController regulationController = new RegulationController(regulationServiceImpl,
                regulationLimitServiceImpl, new CompanyServiceImpl(companyRepository, mock(RegulationRepository.class),
                mock(RegulationLimitRepository.class)));

        Regulation regulation2 = new Regulation();
        regulation2.setStartDate(LocalDate.ofEpochDay(4L));
        regulation2.setEndDate(LocalDate.ofEpochDay(1L));
        regulation2.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation2.setPercentage(1);
        assertThrows(RegulationInvalidDateException.class, () -> regulationController.addRegulation(regulation2));
        verify(regulationServiceImpl).getAllRegulation();
    }

    @Test
    void testUpdateRegulation() throws RegulationNotFoundException {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);
        Optional<Regulation> ofResult = Optional.<Regulation>of(regulation);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);
        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.save((Regulation) any())).thenReturn(regulation1);
        when(regulationRepository.findById((LocalDate) any())).thenReturn(ofResult);
        RegulationServiceImpl regulationServiceImpl = new RegulationServiceImpl(regulationRepository);
        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        RegulationLimitServiceImpl regulationLimitServiceImpl = new RegulationLimitServiceImpl(regulationLimitRepository,
                new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null)));

        RegulationController regulationController = new RegulationController(regulationServiceImpl,
                regulationLimitServiceImpl, new CompanyServiceImpl(mock(CompanyRepository.class),
                mock(RegulationRepository.class), mock(RegulationLimitRepository.class)));
        LocalDate startDate = LocalDate.ofEpochDay(1L);

        Regulation regulation2 = new Regulation();
        regulation2.setStartDate(LocalDate.ofEpochDay(1L));
        regulation2.setEndDate(LocalDate.ofEpochDay(1L));
        regulation2.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation2.setPercentage(1);
        assertSame(regulation1, regulationController.updateRegulation(startDate, regulation2));
        verify(regulationRepository).findById((LocalDate) any());
        verify(regulationRepository).save((Regulation) any());
        assertTrue(regulationController.getRegulations().isEmpty());
    }

    @Test
    void testUpdateRegulation2() throws RegulationNotFoundException {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);
        RegulationRepository regulationRepository = mock(RegulationRepository.class);
        when(regulationRepository.save((Regulation) any())).thenReturn(regulation);
        when(regulationRepository.findById((LocalDate) any())).thenReturn(Optional.<Regulation>empty());
        RegulationServiceImpl regulationServiceImpl = new RegulationServiceImpl(regulationRepository);
        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        RegulationLimitServiceImpl regulationLimitServiceImpl = new RegulationLimitServiceImpl(regulationLimitRepository,
                new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null)));

        RegulationController regulationController = new RegulationController(regulationServiceImpl,
                regulationLimitServiceImpl, new CompanyServiceImpl(mock(CompanyRepository.class),
                mock(RegulationRepository.class), mock(RegulationLimitRepository.class)));
        LocalDate startDate = LocalDate.ofEpochDay(1L);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);
        assertThrows(RegulationNotFoundException.class,
                () -> regulationController.updateRegulation(startDate, regulation1));
        verify(regulationRepository).findById((LocalDate) any());
    }

    @Test
    void testUpdateRegulation3() throws RegulationNotFoundException {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);
        RegulationServiceImpl regulationServiceImpl = mock(RegulationServiceImpl.class);
        when(regulationServiceImpl.updateRegulation((LocalDate) any(), (Regulation) any())).thenReturn(regulation);
        RegulationLimitRepository regulationLimitRepository = mock(RegulationLimitRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        MailService mailService = mock(MailService.class);
        RegulationLimitServiceImpl regulationLimitServiceImpl = new RegulationLimitServiceImpl(regulationLimitRepository,
                new UserServiceImpl(userRepository, mailService, new WebSecurityConfig(null, null, null)));

        RegulationController regulationController = new RegulationController(regulationServiceImpl,
                regulationLimitServiceImpl, new CompanyServiceImpl(mock(CompanyRepository.class),
                mock(RegulationRepository.class), mock(RegulationLimitRepository.class)));
        LocalDate startDate = LocalDate.ofEpochDay(1L);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);
        assertSame(regulation, regulationController.updateRegulation(startDate, regulation1));
        verify(regulationServiceImpl).updateRegulation((LocalDate) any(), (Regulation) any());
        assertTrue(regulationController.getRegulations().isEmpty());
    }

    @Test
    void testDeleteRegulationById() throws Exception {
        doNothing().when(this.regulationServiceImpl).deleteRegulationById((LocalDate) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/regulation/hr/{startDate}",
                LocalDate.ofEpochDay(1L));
        MockMvcBuilders.standaloneSetup(this.regulationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteRegulationById2() throws Exception {
        doThrow(new EmptyResultDataAccessException(3)).when(this.regulationServiceImpl)
                .deleteRegulationById((LocalDate) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/regulation/hr/{startDate}",
                LocalDate.ofEpochDay(1L));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.regulationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetRegulations() throws Exception {
        when(this.regulationServiceImpl.getAllRegulation()).thenReturn(new ArrayList<Regulation>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/regulation/emp");
        MockMvcBuilders.standaloneSetup(this.regulationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetRegulations2() throws Exception {
        when(this.regulationServiceImpl.getAllRegulation()).thenReturn(new ArrayList<Regulation>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/regulation/emp");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.regulationController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetRegulationsWithLimit() throws Exception {
        when(this.regulationServiceImpl.getAllRegulationWithLimit((String) any()))
                .thenReturn(new ArrayList<List<String>>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/regulation/emp/limit/{userEmail}/",
                "jane.doe@example.org");
        MockMvcBuilders.standaloneSetup(this.regulationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}


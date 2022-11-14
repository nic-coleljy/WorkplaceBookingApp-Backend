package com.collab.g5.demo.companies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.collab.g5.demo.regulations.Regulation;
import com.collab.g5.demo.regulations.RegulationLimit;
import com.collab.g5.demo.regulations.RegulationLimitKey;
import com.collab.g5.demo.regulations.RegulationLimitRepository;
import com.collab.g5.demo.regulations.RegulationRepository;
import com.collab.g5.demo.users.User;

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

@ContextConfiguration(classes = {CompanyServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CompanyServiceImplTest {
    @MockBean
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyServiceImpl companyServiceImpl;

    @MockBean
    private RegulationLimitRepository regulationLimitRepository;

    @MockBean
    private RegulationRepository regulationRepository;

    @Test
    void testGetAllCompanies() {
        ArrayList<Company> companyList = new ArrayList<Company>();
        when(this.companyRepository.findAll()).thenReturn(companyList);
        List<Company> actualAllCompanies = this.companyServiceImpl.getAllCompanies();
        assertSame(companyList, actualAllCompanies);
        assertTrue(actualAllCompanies.isEmpty());
        verify(this.companyRepository).findAll();
    }

    @Test
    void testGetCompanyById() {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);
        Optional<Company> ofResult = Optional.<Company>of(company);
        when(this.companyRepository.findById((Integer) any())).thenReturn(ofResult);
        assertSame(company, this.companyServiceImpl.getCompanyById(1));
        verify(this.companyRepository).findById((Integer) any());
        assertTrue(this.companyServiceImpl.getAllCompanies().isEmpty());
    }

    @Test
    void testContainsCompany() {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);
        Optional<Company> ofResult = Optional.<Company>of(company);
        when(this.companyRepository.findById((Integer) any())).thenReturn(ofResult);
        assertTrue(this.companyServiceImpl.containsCompany(1));
        verify(this.companyRepository).findById((Integer) any());
        assertTrue(this.companyServiceImpl.getAllCompanies().isEmpty());
    }

    @Test
    void testContainsCompany2() {
        when(this.companyRepository.findById((Integer) any())).thenReturn(Optional.<Company>empty());
        assertFalse(this.companyServiceImpl.containsCompany(1));
        verify(this.companyRepository).findById((Integer) any());
        assertTrue(this.companyServiceImpl.getAllCompanies().isEmpty());
    }

    @Test
    void testAddNewCompany() {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);
        when(this.companyRepository.save((Company) any())).thenReturn(company);

        Company company1 = new Company();
        company1.setQuota(1);
        company1.setUsers(new ArrayList<User>());
        company1.setName("Name");
        company1.setSize(3L);
        company1.setRegulationLimit(new ArrayList<RegulationLimit>());
        company1.setCid(1);
        this.companyServiceImpl.addNewCompany(company1);
        verify(this.companyRepository).save((Company) any());
        assertTrue(this.companyServiceImpl.getAllCompanies().isEmpty());
    }

    @Test
    void testDelete() {
        doNothing().when(this.companyRepository).delete((Company) any());

        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);
        this.companyServiceImpl.delete(company);
        verify(this.companyRepository).delete((Company) any());
        assertTrue(this.companyServiceImpl.getAllCompanies().isEmpty());
    }

    @Test
    void testUpdateCompany() {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);
        assertNull(this.companyServiceImpl.updateCompany(1, company));
    }

    @Test
    void testDeleteById() {
        doNothing().when(this.companyRepository).deleteById((Integer) any());
        this.companyServiceImpl.deleteById(1);
        verify(this.companyRepository).deleteById((Integer) any());
        assertTrue(this.companyServiceImpl.getAllCompanies().isEmpty());
    }

    @Test
    void testGetCurrentQuota() {
        when(this.regulationRepository.findStartDateBasedCustomDate((LocalDate) any()))
                .thenReturn(LocalDate.ofEpochDay(1L));

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
        when(this.regulationLimitRepository.getById((RegulationLimitKey) any())).thenReturn(regulationLimit);
        assertEquals(1, this.companyServiceImpl.getCurrentQuota(1, LocalDate.ofEpochDay(1L)));
        verify(this.regulationRepository).findStartDateBasedCustomDate((LocalDate) any());
        verify(this.regulationLimitRepository).getById((RegulationLimitKey) any());
        assertTrue(this.companyServiceImpl.getAllCompanies().isEmpty());
    }
}


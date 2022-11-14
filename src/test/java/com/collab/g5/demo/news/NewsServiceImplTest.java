package com.collab.g5.demo.news;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.collab.g5.demo.bookings.Bookings;
import com.collab.g5.demo.companies.Company;
import com.collab.g5.demo.dailyForm.DailyForm;
import com.collab.g5.demo.regulations.RegulationLimit;
import com.collab.g5.demo.users.User;
import com.collab.g5.demo.users.UserRole;

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

@ContextConfiguration(classes = {NewsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class NewsServiceImplTest {
    @MockBean
    private NewsRepository newsRepository;

    @Autowired
    private NewsServiceImpl newsServiceImpl;

    @Test
    void testGetAllNews() {
        ArrayList<News> newsList = new ArrayList<News>();
        when(this.newsRepository.findAll()).thenReturn(newsList);
        List<News> actualAllNews = this.newsServiceImpl.getAllNews();
        assertSame(newsList, actualAllNews);
        assertTrue(actualAllNews.isEmpty());
        verify(this.newsRepository).findAll();
    }

    @Test
    void testGetNewsById() {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        Company company1 = new Company();
        company1.setQuota(1);
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

        News news = new News();
        news.setDate(LocalDate.ofEpochDay(1L));
        news.setCompany(company);
        news.setUser(user);
        news.setNid(1);
        news.setTitle("Dr");
        news.setContent("Not all who wander are lost");
        Optional<News> ofResult = Optional.<News>of(news);
        when(this.newsRepository.findById((Integer) any())).thenReturn(ofResult);
        assertSame(news, this.newsServiceImpl.getNewsById(1));
        verify(this.newsRepository).findById((Integer) any());
    }

    @Test
    void testGetNewsById2() {
        when(this.newsRepository.findById((Integer) any())).thenReturn(Optional.<News>empty());
        assertNull(this.newsServiceImpl.getNewsById(1));
        verify(this.newsRepository).findById((Integer) any());
        assertTrue(this.newsServiceImpl.getAllNews().isEmpty());
    }

    @Test
    void testAddNews() {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        Company company1 = new Company();
        company1.setQuota(1);
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

        News news = new News();
        news.setDate(LocalDate.ofEpochDay(1L));
        news.setCompany(company);
        news.setUser(user);
        news.setNid(1);
        news.setTitle("Dr");
        news.setContent("Not all who wander are lost");
        Optional<News> ofResult = Optional.<News>of(news);
        when(this.newsRepository.findById((Integer) any())).thenReturn(ofResult);

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        Company company3 = new Company();
        company3.setQuota(1);
        company3.setUsers(new ArrayList<User>());
        company3.setName("Name");
        company3.setSize(3L);
        company3.setRegulationLimit(new ArrayList<RegulationLimit>());
        company3.setCid(1);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company3);
        user1.setNewsList(new ArrayList<News>());
        user1.setFname("Fname");
        user1.setLname("Lname");
        user1.setEnabled(true);
        user1.setBookings(new ArrayList<Bookings>());
        user1.setLocked(true);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(new ArrayList<DailyForm>());

        News news1 = new News();
        news1.setDate(LocalDate.ofEpochDay(1L));
        news1.setCompany(company2);
        news1.setUser(user1);
        news1.setNid(1);
        news1.setTitle("Dr");
        news1.setContent("Not all who wander are lost");
        assertNull(this.newsServiceImpl.addNews(news1));
        verify(this.newsRepository).findById((Integer) any());
    }

    @Test
    void testAddNews2() {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        Company company1 = new Company();
        company1.setQuota(1);
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

        News news = new News();
        news.setDate(LocalDate.ofEpochDay(1L));
        news.setCompany(company);
        news.setUser(user);
        news.setNid(1);
        news.setTitle("Dr");
        news.setContent("Not all who wander are lost");
        when(this.newsRepository.save((News) any())).thenReturn(news);
        when(this.newsRepository.findById((Integer) any())).thenReturn(Optional.<News>empty());

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        Company company3 = new Company();
        company3.setQuota(1);
        company3.setUsers(new ArrayList<User>());
        company3.setName("Name");
        company3.setSize(3L);
        company3.setRegulationLimit(new ArrayList<RegulationLimit>());
        company3.setCid(1);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company3);
        user1.setNewsList(new ArrayList<News>());
        user1.setFname("Fname");
        user1.setLname("Lname");
        user1.setEnabled(true);
        user1.setBookings(new ArrayList<Bookings>());
        user1.setLocked(true);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(new ArrayList<DailyForm>());

        News news1 = new News();
        news1.setDate(LocalDate.ofEpochDay(1L));
        news1.setCompany(company2);
        news1.setUser(user1);
        news1.setNid(1);
        news1.setTitle("Dr");
        news1.setContent("Not all who wander are lost");
        assertSame(news, this.newsServiceImpl.addNews(news1));
        verify(this.newsRepository).findById((Integer) any());
        verify(this.newsRepository).save((News) any());
    }

    @Test
    void testUpdateNews() {
        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        Company company1 = new Company();
        company1.setQuota(1);
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

        News news = new News();
        news.setDate(LocalDate.ofEpochDay(1L));
        news.setCompany(company);
        news.setUser(user);
        news.setNid(1);
        news.setTitle("Dr");
        news.setContent("Not all who wander are lost");
        Optional<News> ofResult = Optional.<News>of(news);

        Company company2 = new Company();
        company2.setQuota(1);
        company2.setUsers(new ArrayList<User>());
        company2.setName("Name");
        company2.setSize(3L);
        company2.setRegulationLimit(new ArrayList<RegulationLimit>());
        company2.setCid(1);

        Company company3 = new Company();
        company3.setQuota(1);
        company3.setUsers(new ArrayList<User>());
        company3.setName("Name");
        company3.setSize(3L);
        company3.setRegulationLimit(new ArrayList<RegulationLimit>());
        company3.setCid(1);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setCompany(company3);
        user1.setNewsList(new ArrayList<News>());
        user1.setFname("Fname");
        user1.setLname("Lname");
        user1.setEnabled(true);
        user1.setBookings(new ArrayList<Bookings>());
        user1.setLocked(true);
        user1.setVaccinated(true);
        user1.setUserRole(UserRole.EMPLOYEE);
        user1.setDailyFormList(new ArrayList<DailyForm>());

        News news1 = new News();
        news1.setDate(LocalDate.ofEpochDay(1L));
        news1.setCompany(company2);
        news1.setUser(user1);
        news1.setNid(1);
        news1.setTitle("Dr");
        news1.setContent("Not all who wander are lost");
        when(this.newsRepository.save((News) any())).thenReturn(news1);
        when(this.newsRepository.findById((Integer) any())).thenReturn(ofResult);

        Company company4 = new Company();
        company4.setQuota(1);
        company4.setUsers(new ArrayList<User>());
        company4.setName("Name");
        company4.setSize(3L);
        company4.setRegulationLimit(new ArrayList<RegulationLimit>());
        company4.setCid(1);

        Company company5 = new Company();
        company5.setQuota(1);
        company5.setUsers(new ArrayList<User>());
        company5.setName("Name");
        company5.setSize(3L);
        company5.setRegulationLimit(new ArrayList<RegulationLimit>());
        company5.setCid(1);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setCompany(company5);
        user2.setNewsList(new ArrayList<News>());
        user2.setFname("Fname");
        user2.setLname("Lname");
        user2.setEnabled(true);
        user2.setBookings(new ArrayList<Bookings>());
        user2.setLocked(true);
        user2.setVaccinated(true);
        user2.setUserRole(UserRole.EMPLOYEE);
        user2.setDailyFormList(new ArrayList<DailyForm>());

        News news2 = new News();
        news2.setDate(LocalDate.ofEpochDay(1L));
        news2.setCompany(company4);
        news2.setUser(user2);
        news2.setNid(1);
        news2.setTitle("Dr");
        news2.setContent("Not all who wander are lost");
        assertSame(news1, this.newsServiceImpl.updateNews(1, news2));
        verify(this.newsRepository).findById((Integer) any());
        verify(this.newsRepository).save((News) any());
    }

    @Test
    void testDelete() {
        doNothing().when(this.newsRepository).delete((News) any());

        Company company = new Company();
        company.setQuota(1);
        company.setUsers(new ArrayList<User>());
        company.setName("Name");
        company.setSize(3L);
        company.setRegulationLimit(new ArrayList<RegulationLimit>());
        company.setCid(1);

        Company company1 = new Company();
        company1.setQuota(1);
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

        News news = new News();
        news.setDate(LocalDate.ofEpochDay(1L));
        news.setCompany(company);
        news.setUser(user);
        news.setNid(1);
        news.setTitle("Dr");
        news.setContent("Not all who wander are lost");
        this.newsServiceImpl.delete(news);
        verify(this.newsRepository).delete((News) any());
    }

    @Test
    void testDeleteNewsById() {
        doNothing().when(this.newsRepository).deleteById((Integer) any());
        this.newsServiceImpl.deleteNewsById(1);
        verify(this.newsRepository).deleteById((Integer) any());
        assertTrue(this.newsServiceImpl.getAllNews().isEmpty());
    }
}


package com.collab.g5.demo.regulations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * We only need this interface declaration
 * Spring will automatically generate an implementation of the repo
 *
 * JpaRepository provides more features by extending PagingAndSortingRepository, which in turn extends CrudRepository
 */
public interface RegulationRepository extends JpaRepository<Regulation, LocalDate> {
    @Override
    List<Regulation> findAll();

    @Query(value = "select t1.start_date, t1.end_date, t1.percentage, t1.daily_limit from (select * from regulation r inner join regulation_limit l where r.start_date = l.regulation_start_date) as t1 inner join(select * from company where cid in (select cid from user where email = ?)) as t2 where t1.company_cid = t2.cid;" ,nativeQuery = true)
    public List<List<String>> findAllRegulationWithLimit(String userEmail);

    @Query(value = "select start_date from regulation r where r.start_date <= ?1 and r.end_date >= ?1" , nativeQuery = true)
    LocalDate findStartDateBasedCustomDate(LocalDate date);
}

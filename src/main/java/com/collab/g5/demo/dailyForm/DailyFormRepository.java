package com.collab.g5.demo.dailyForm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyFormRepository extends JpaRepository<DailyForm, Integer> {

}

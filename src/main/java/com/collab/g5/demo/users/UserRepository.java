package com.collab.g5.demo.users;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * We only need this interface declaration
 * Spring will automatically generate an implementation of the repo
 * <p>
 * JpaRepository provides more features by extending PagingAndSortingRepository, which in turn extends CrudRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    @Query(value = "select vaccinated from user where email = ?1", nativeQuery = true)
    boolean getVaccinatedByEmail(String email);
}

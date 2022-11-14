package com.collab.g5.demo.regulations;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * We only need this interface declaration
 * Spring will automatically generate an implementation of the repo
 *
 * JpaRepository provides more features by extending PagingAndSortingRepository, which in turn extends CrudRepository
 */
public interface RegulationLimitRepository extends JpaRepository<RegulationLimit, RegulationLimitKey> {

}

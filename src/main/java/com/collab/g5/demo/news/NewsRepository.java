package com.collab.g5.demo.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * We only need this interface declaration
 * Spring will automatically generate an implementation of the repo
 *
 * JpaRepository provides more features by extending PagingAndSortingRepository, which in turn extends CrudRepository
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    // derived query to find news by title
    List<News> findByTitle(String title);

}

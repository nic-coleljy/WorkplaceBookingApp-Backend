package com.collab.g5.demo.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {
    /**
     * Declaring our variables
     */
    private NewsRepository newsRepository;

    /**
     * Instantiating our variables values
     *
     * @param newsRepository
     */
    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    /**
     * This method will return all the news that is in the database
     *
     * @return a list of all news in the company
     */
    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    /**
     * Returns a news based on ID
     *
     * @param id
     * @return attempts to return a News object if there exists if not will return null.
     */
    @Override
    public News getNewsById(int id) {
        return newsRepository.findById(id).map(news -> {
            return news;
        }).orElse(null);
    }

    /**
     * Persist a news into the database
     *
     * @param news
     * @return The news object that is being saved to the database
     */
    @Override
    public News addNews(News news) {
        Optional<News> newsExists = newsRepository.findById(news.getNid());
        if (newsExists.isPresent()) {
            return null;
        }
        return newsRepository.save(news);
    }

    /**
     * Will take in the news ID to be updated and then populate it with the data inside the freshNews
     *
     * @param nid
     * @param freshNews
     * @return A news object with the newly populated data.
     */
    @Override
    public News updateNews(int nid, News freshNews) {
        return newsRepository.findById(nid).map(news -> {
            news.setDate(freshNews.getDate());
            news.setTitle(freshNews.getTitle());
            news.setContent(freshNews.getContent());
            return newsRepository.save(news);
        }).orElse(null);
    }

    /**
     * Deletes a news object from the database.
     *
     * @param news
     */
    @Override
    public void delete(News news) {
        newsRepository.delete(news);
    }

    /**
     * Remove a news with the given nid
     * Spring Data JPA does not return a value for delete operation
     * Cascading: removing a news will also remove all its associated information
     */
    @Override
    public void deleteNewsById(int nid) {
        newsRepository.deleteById(nid);
    }


}

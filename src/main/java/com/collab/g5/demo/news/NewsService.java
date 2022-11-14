package com.collab.g5.demo.news;

import java.util.List;

public interface NewsService {
    //CREATE
    News addNews(News news); //save

    //READ
    List<News> getAllNews();

    News getNewsById(int nid);

    //UPDATE
    News updateNews(int nid, News news);

    //DELETE
    void deleteNewsById(int nid);

    void delete(News news);
}

package com.collab.g5.demo.news;

import com.collab.g5.demo.exceptions.bookings.BookingNotFoundException;
import com.collab.g5.demo.exceptions.news.NewsExistsException;
import com.collab.g5.demo.exceptions.news.NewsNotFoundException;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/news")
public class NewsController {

    private NewsServiceImpl newsServiceImpl;

    @Autowired
    public NewsController(NewsServiceImpl newsServiceImpl) {
        this.newsServiceImpl = newsServiceImpl;
    }

    /**
     * List all news in the system
     *
     * @return list of all news
     */
    @GetMapping("/emp/")
    public List<News> getNews() {
        return newsServiceImpl.getAllNews();
    }

    /**
     * Search for news with the given nid
     * If there is no news with the given "nid", throw a NewsNotFoundException
     *
     * @param nid
     * @return news with the given nid
     */
    @GetMapping("/emp/{nid}")
    public News getNewsById(@PathVariable int nid) throws NewsNotFoundException {
        News news = newsServiceImpl.getNewsById(nid);
        if (news == null) throw new NewsNotFoundException(nid);
        return newsServiceImpl.getNewsById(nid);
    }

    /**
     * Add a new news with POST request to "/hr"
     *
     * @param news
     * @return the newly added news
     */
    @PostMapping("/hr")
    public News addBook(@RequestBody News news) throws NewsExistsException {
        News freshNews = newsServiceImpl.addNews(news);
        if (freshNews == null) throw new NewsExistsException("News " + freshNews.toString() + " exists");
        return newsServiceImpl.addNews(freshNews);
    }

    /**
     * If there is no news with the given "nid", throw a NewsNotFoundException
     *
     * @param nid         an int value
     * @param newNewsInfo a News object containing the new news info to be updated
     * @return the updated, or newly added news
     */
    @PutMapping("/hr/{nid}")
    public News updateNews(@PathVariable int nid, @RequestBody News newNewsInfo) throws NewsNotFoundException {
        News news = newsServiceImpl.updateNews(nid, newNewsInfo);
        if (news == null) throw new NewsNotFoundException(nid);
        return news;
    }

    /**
     * Remove news with the DELETE request to "/hr/{nid}"
     * If there is no news with the given "nid", throw a NewsNotFoundException
     *
     * @param nid
     */
    @DeleteMapping("/hr/{nid}")
    public void deleteNews(@PathVariable int nid) throws NewsNotFoundException {
        News news = newsServiceImpl.getNewsById(nid);
        if (news == null) {
            throw new BookingNotFoundException(nid);
        }
        newsServiceImpl.delete(getNewsById(nid));
    }

    /**
     * Scrape this cna news website returns the news articles in the top news headline
     *
     * @return a list containing the news article title, article URL and image source
     */
    @GetMapping("/emp/cna")
    public List<HashMap<String, String>> getCnaNews() {
        final String httpsUrl = "https://www.channelnewsasia.com/coronavirus-covid-19";
        List<HashMap<String, String>> res = null;

        try {
            Document webpageContent = Jsoup.connect(httpsUrl)
                    .userAgent("Mozilla").data("name", "jsoup").get();

            Elements titles = webpageContent.select("div").first().getElementsByClass("media-object media-object-- top-stories-primary-section__item");

            res = new ArrayList<HashMap<String, String>>();

            for (Element title : titles) {
                HashMap<String, String> toAdd = new HashMap<>();
                toAdd.put("href", "https://www.channelnewsasia.com" + title.getElementsByTag("a").attr("href"));
                toAdd.put("title", title.getElementsByTag("img").attr("title"));
                toAdd.put("src", title.getElementsByTag("img").attr("src"));
                res.add(toAdd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * Scrape this website and returns the number of covid cases, total deaths and total recovered
     *
     * @return a HashMap containing the above 3 mentioned values
     */
    @GetMapping("/emp/covidcases/")
    private HashMap<String, String> getCovidCases() {
        final String httpsUrl = "https://www.worldometers.info/coronavirus/country/singapore/";
        HashMap<String, String> res = null;

        try {
            Document webpageContent = Jsoup.connect(httpsUrl).get();

            Elements stats = webpageContent.select("[id='maincounter-wrap']");

            res = new HashMap<String, String>();

            res.put("cases", stats.get(0).getElementsByTag("h1").text());
            res.put("caseno", stats.get(0).getElementsByClass("maincounter-number").text());
            res.put("deaths", stats.get(1).getElementsByTag("h1").text());
            res.put("deathno", stats.get(1).getElementsByClass("maincounter-number").text());
            res.put("header: ", stats.get(0).getElementsByTag("h1").text());
            res.put("recovered", stats.get(2).getElementsByTag("h1").text());
            res.put("recoveredno", stats.get(2).getElementsByClass("maincounter-number").text());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

}


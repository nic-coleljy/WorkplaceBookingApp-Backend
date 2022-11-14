package com.collab.g5.demo.exceptions.news;

public class NewsExistsException extends RuntimeException {
    public NewsExistsException(String e){
        super(e);
    }
}

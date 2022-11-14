package com.collab.g5.demo.exceptions.news;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Error
public class NewsNotFoundException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NewsNotFoundException(int nid) {
        super("Could not find news " + nid);
    }

}


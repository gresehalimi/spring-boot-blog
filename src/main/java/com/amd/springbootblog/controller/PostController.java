package com.amd.springbootblog.controller;

import com.amd.springbootblog.data.DataResultObject;
import com.amd.springbootblog.data.ErrorMessageResultObject;
import com.amd.springbootblog.data.PagingResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping(value = "/{pageNumber}/category-content")
    public ResponseEntity filterPostsByCategoryAndOrContent(@PathVariable String categoryName, @PathVariable String content, @PathVariable("pageNo") int pageNumber) {

        ResponseEntity responseEntity;
        PagingResultObject pagingResultObject = postService.filterPostsByCategoryAndOrContent(categoryName, content, pageNumber);
        if (pagingResultObject.getResponseStatus() == ResponseStatus.NO_DATA) {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(pagingResultObject);
        }

        return responseEntity;
    }
}

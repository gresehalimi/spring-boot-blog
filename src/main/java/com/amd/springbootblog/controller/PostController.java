package com.amd.springbootblog.controller;

import com.amd.springbootblog.data.BooleanResultObject;
import com.amd.springbootblog.data.ErrorMessageResultObject;
import com.amd.springbootblog.data.PagingResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.dto.PostRegister;
import com.amd.springbootblog.dto.PostUpdate;
import com.amd.springbootblog.security.UserPrincipal;
import com.amd.springbootblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;


    @PostMapping(value = "/")
    public ResponseEntity<?> createPost(@RequestBody PostRegister postRegister, UserPrincipal userPrincipal, HttpServletRequest request) {
        ResponseEntity responseEntity;
        BooleanResultObject booleanResultObject = postService.createPost(postRegister, userPrincipal);

        if (booleanResultObject.getResponseStatus() == ResponseStatus.INTERNAL_SERVER_ERROR) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessageResultObject(new Date(), booleanResultObject.getStatus(), "Internal Server Error!", booleanResultObject.getMessage(), request.getRequestURI()));
        } else if (booleanResultObject.getResponseStatus() == ResponseStatus.CONFLICT) {
            responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorMessageResultObject(new Date(), booleanResultObject.getStatus(), "Conflict!", booleanResultObject.getMessage(), request.getRequestURI()));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(booleanResultObject);
        }
        return responseEntity;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updatePost(@RequestBody PostUpdate postUpdate, HttpServletRequest request) {
        ResponseEntity responseEntity;
        BooleanResultObject booleanResultObject = postService.updatePost(postUpdate);

        if (booleanResultObject.getResponseStatus() == ResponseStatus.INTERNAL_SERVER_ERROR) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessageResultObject(new Date(), booleanResultObject.getStatus(), "Internal Server Error!", booleanResultObject.getMessage(), request.getRequestURI()));
        } else if (booleanResultObject.getResponseStatus() == ResponseStatus.CONFLICT) {
            responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorMessageResultObject(new Date(), booleanResultObject.getStatus(), "Conflict!", booleanResultObject.getMessage(), request.getRequestURI()));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(booleanResultObject);
        }
        return responseEntity;
    }


    @GetMapping(value = "/{pageNumber}/category-content")
    public ResponseEntity<?> filterPostsByCategoryAndOrContent(@PathVariable String categoryName, @PathVariable String content, @PathVariable("pageNo") int pageNumber) {

        ResponseEntity responseEntity;
        PagingResultObject pagingResultObject = postService.filterPostsByCategoryAndOrContent(categoryName, content, pageNumber);
        if (pagingResultObject.getResponseStatus() == ResponseStatus.NO_DATA) {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(pagingResultObject);
        }

        return responseEntity;
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, HttpServletRequest request) {
        ResponseEntity responseEntity;
        BooleanResultObject booleanResultObject = postService.deletePost(id);

        if (booleanResultObject.getResponseStatus() == ResponseStatus.INTERNAL_SERVER_ERROR) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessageResultObject(new Date(), booleanResultObject.getStatus(), "Internal Server Error!", booleanResultObject.getMessage(), request.getRequestURI()));
        } else if (booleanResultObject.getResponseStatus() == ResponseStatus.CONFLICT) {
            responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorMessageResultObject(new Date(), booleanResultObject.getStatus(), "Conflict!", booleanResultObject.getMessage(), request.getRequestURI()));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(booleanResultObject);
        }

        return responseEntity;
    }
}

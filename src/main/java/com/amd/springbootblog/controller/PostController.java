package com.amd.springbootblog.controller;

import com.amd.springbootblog.common.BaseAbstractController;
import com.amd.springbootblog.data.BooleanResultObject;
import com.amd.springbootblog.data.PagingResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.dto.PostRegister;
import com.amd.springbootblog.dto.PostUpdate;
import com.amd.springbootblog.security.CurrentUser;
import com.amd.springbootblog.security.UserPrincipal;
import com.amd.springbootblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/posts")
public class PostController extends BaseAbstractController {

    @Autowired
    PostService postService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createPost(@RequestBody PostRegister postRegister, @CurrentUser UserPrincipal currentUser, HttpServletRequest request) {
      return prepareResponseEntity(postService.createPost(postRegister,currentUser),request);}

    @PutMapping(value = "update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") Long id, @RequestBody PostUpdate postUpdate, @CurrentUser UserPrincipal currentUser, HttpServletRequest request) {
        return prepareResponseEntity(postService.updatePost(id,postUpdate,currentUser),request);}

    @GetMapping(value = "get/{id}")
    public ResponseEntity getPostByUser(@PathVariable Long id, @CurrentUser UserPrincipal currentUser, HttpServletRequest request){
        return prepareResponseEntity(postService.getPost(id,currentUser),request);
    }

    @GetMapping(value = "/get/{pageNumber}/category-content")
    public ResponseEntity<?> filterPostsByCategoryAndOrContent(@RequestParam String categoryName, @RequestParam String content, @PathVariable("pageNumber") int pageNumber) {
        ResponseEntity responseEntity;

        PagingResultObject pagingResultObject = postService.filterPostsByCategoryAndOrContent(categoryName, content, pageNumber);
        if (pagingResultObject.getResponseStatus() == ResponseStatus.NO_DATA) {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(pagingResultObject);
        }
        return responseEntity;
    }

    @GetMapping(value = "/get/{pageNumber}/all")
    public ResponseEntity<?> getAllPosts(@PathVariable("pageNumber") int pageNumber){
        ResponseEntity responseEntity;
        PagingResultObject pagingResultObject= postService.getAllPosts(pageNumber);
        if (pagingResultObject.getResponseStatus() == ResponseStatus.NO_DATA) {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(pagingResultObject);
        }
        return responseEntity;
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @CurrentUser UserPrincipal currentUser, HttpServletRequest request) {
        return prepareResponseEntity(postService.deletePost(id,currentUser),request);
    }

}

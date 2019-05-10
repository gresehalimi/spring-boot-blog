package com.amd.springbootblog.controller;

import com.amd.springbootblog.common.BaseAbstractController;
import com.amd.springbootblog.data.FieldErrorResultObject;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController extends BaseAbstractController {

    @Autowired
    PostService postService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostRegister postRegister, BindingResult bindingResult, @CurrentUser UserPrincipal currentUser, HttpServletRequest request) {
        ResponseEntity responseEntity;

        FieldErrorResultObject fieldErrorResultObject = new FieldErrorResultObject();
        fieldErrorResultObject.setResponseStatus(ResponseStatus.FIELD_ERROR);
        List<com.amd.springbootblog.data.FieldError> list = new ArrayList<>();

        if (bindingResult.hasErrors()){
            for (Object object : bindingResult.getAllErrors()){
                if(object instanceof FieldError){
                    FieldError fieldError = (FieldError) object;
                    com.amd.springbootblog.data.FieldError fieldError1 = new com.amd.springbootblog.data.FieldError(fieldError.getField(), fieldError.getDefaultMessage());
                    list.add(fieldError1);
                }
            }
            fieldErrorResultObject.setFieldsError(list);
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrorResultObject);
            return responseEntity;
        }
      return prepareResponseEntity(postService.createPost(postRegister,currentUser),request);}

    @PutMapping(value = "update/{id}")
    public ResponseEntity<?> updatePost( @PathVariable("id") Long id, @Valid @RequestBody PostUpdate postUpdate, BindingResult bindingResult, @CurrentUser UserPrincipal currentUser, HttpServletRequest request) {
        ResponseEntity responseEntity;

        FieldErrorResultObject fieldErrorResultObject = new FieldErrorResultObject();
        fieldErrorResultObject.setResponseStatus(ResponseStatus.FIELD_ERROR);
        List<com.amd.springbootblog.data.FieldError> list = new ArrayList<>();

        if (bindingResult.hasErrors()){
            for (Object object : bindingResult.getAllErrors()){
                if(object instanceof FieldError){
                    FieldError fieldError = (FieldError) object;
                    com.amd.springbootblog.data.FieldError fieldError1 = new com.amd.springbootblog.data.FieldError(fieldError.getField(), fieldError.getDefaultMessage());
                    list.add(fieldError1);
                }
            }
            fieldErrorResultObject.setFieldsError(list);
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrorResultObject);
            return responseEntity;
        }
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

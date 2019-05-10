package com.amd.springbootblog.controller;

import com.amd.springbootblog.common.BaseAbstractController;
import com.amd.springbootblog.data.FieldError;
import com.amd.springbootblog.data.FieldErrorResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.dto.PostCommentData;
import com.amd.springbootblog.security.CurrentUser;
import com.amd.springbootblog.security.UserPrincipal;
import com.amd.springbootblog.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/comments")
public class PostCommentController extends BaseAbstractController {

    @Autowired
    PostCommentService postCommentService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createComment(@RequestBody @Valid PostCommentData postCommentData, BindingResult bindingResult, @CurrentUser UserPrincipal currentUser, HttpServletRequest request) {
        ResponseEntity responseEntity;

        FieldErrorResultObject fieldErrorResultObject = new FieldErrorResultObject();
        fieldErrorResultObject.setResponseStatus(ResponseStatus.FIELD_ERROR);
        List<FieldError> list = new ArrayList<>();

        if (bindingResult.hasErrors()){
            for (Object object : bindingResult.getAllErrors()){
                if(object instanceof org.springframework.validation.FieldError){
                    org.springframework.validation.FieldError fieldError = (org.springframework.validation.FieldError) object;
                    com.amd.springbootblog.data.FieldError fieldError1 = new com.amd.springbootblog.data.FieldError(fieldError.getField(), fieldError.getDefaultMessage());
                    list.add(fieldError1);
                }
            }
            fieldErrorResultObject.setFieldsError(list);
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrorResultObject);
            return responseEntity;
        }
        return prepareResponseEntity(postCommentService.create(postCommentData, currentUser), request);
    }

    @DeleteMapping(value = "/delete/{commentId}")
    public ResponseEntity<?> deletePostComment(@PathVariable Long commentId,@CurrentUser UserPrincipal currentUser, HttpServletRequest request) {
        return prepareResponseEntity(postCommentService.delete(commentId,currentUser),request);
    }
}

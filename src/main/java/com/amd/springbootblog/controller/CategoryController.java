/*
package com.amd.springbootblog.controller;

import com.amd.springbootblog.data.*;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.dto.CategoryModelAttribute;
import com.amd.springbootblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PutMapping(value = "/create", produces = "application/json")
    public ResponseEntity<?> createCategory(@Valid @ModelAttribute("categoryModelAttribute") Ca categoryModelAttribute, BindingResult bindingResult, HttpServletRequest request) {
        ResponseEntity responseEntity;

        FieldErrorResultObject fieldErrorResultObject = new FieldErrorResultObject();
        fieldErrorResultObject.setResponseStatus(ResponseStatus.FIELD_ERROR);
        List<com.amd.springbootblog.data.FieldError> list = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof org.springframework.validation.FieldError) {
                    org.springframework.validation.FieldError fieldError = (org.springframework.validation.FieldError) object;
                    com.amd.springbootblog.data.FieldError fieldError1 = new com.amd.springbootblog.data.FieldError(fieldError.getField(), fieldError.getDefaultMessage());
                    list.add(fieldError1);
                }
            }
            fieldErrorResultObject.setFieldsError(list);
            fieldErrorResultObject.setStatus(ResponseStatus.FIELD_ERROR.getValue());
            fieldErrorResultObject.setResponseStatus(ResponseStatus.FIELD_ERROR);
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrorResultObject);
            return responseEntity;
        }

        BooleanResultObject resultObject = categoryService.create(categoryModelAttribute.getCategoryName());

        if (resultObject.getResponseStatus() == ResponseStatus.INTERNAL_SERVER_ERROR) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), "Internal Server Error!", resultObject.getMessage(), request.getRequestURI()));
        } else if (resultObject.getResponseStatus() == ResponseStatus.CONFLICT) {
            responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), "Conflict!", resultObject.getMessage(), request.getRequestURI()));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(resultObject);
        }

        return responseEntity;
    }

}
*/

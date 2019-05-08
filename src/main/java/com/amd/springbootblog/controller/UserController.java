package com.amd.springbootblog.controller;

import com.amd.springbootblog.data.DataResultObject;
import com.amd.springbootblog.data.ErrorMessageResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.security.CurrentUser;
import com.amd.springbootblog.security.UserPrincipal;
import com.amd.springbootblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public ResponseEntity getCurrentUser(@CurrentUser UserPrincipal currentUser, HttpServletRequest request) {
        ResponseEntity responseEntity;
        DataResultObject resultObject = userService.getCurrentUser(currentUser);

        if (resultObject.getResponseStatus() == ResponseStatus.INTERNAL_SERVER_ERROR) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), ResponseStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Error", request.getRequestURI()));
        } else if (resultObject.getResponseStatus() == ResponseStatus.NOT_FOUND) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), ResponseStatus.NOT_FOUND.getReasonPhrase(), ResponseStatus.NO_DATA.getReasonPhrase(), request.getRequestURI()));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(resultObject);
        }

        return responseEntity;
    }

   /* @GetMapping("/users/{username}")
    public ResponseEntity getUserProfile(@PathVariable(value = "username") String username, HttpServletRequest request) {
        ResponseEntity responseEntity;
        DataResultObject resultObject = userService.getUserProfile(username);

        if (resultObject.getResponseStatus() == ResponseStatus.INTERNAL_SERVER_ERROR) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), ResponseStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Error", request.getRequestURI()));
        } else if (resultObject.getResponseStatus() == ResponseStatus.NOT_FOUND) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), ResponseStatus.NOT_FOUND.getReasonPhrase(), ResponseStatus.NO_DATA.getReasonPhrase(), request.getRequestURI()));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(resultObject);
        }

        return responseEntity;
    }

*/
}


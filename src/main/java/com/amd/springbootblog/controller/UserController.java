package com.amd.springbootblog.controller;

import com.amd.springbootblog.common.BaseAbstractController;
import com.amd.springbootblog.security.CurrentUser;
import com.amd.springbootblog.security.UserPrincipal;
import com.amd.springbootblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseAbstractController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public ResponseEntity getCurrentUser(@CurrentUser UserPrincipal currentUser, HttpServletRequest request) {

        return prepareResponseEntity(userService.getCurrentUser(currentUser),request); }
}


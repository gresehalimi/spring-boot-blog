package com.amd.springbootblog.controller;

import com.amd.springbootblog.data.BooleanResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.dto.JwtAuthenticationResponse;
import com.amd.springbootblog.dto.LoginRequest;
import com.amd.springbootblog.dto.UserRegister;
import com.amd.springbootblog.repository.RoleRepository;
import com.amd.springbootblog.repository.UserRepository;
import com.amd.springbootblog.security.JwtTokenProvider;
import com.amd.springbootblog.service.AuthenticationService;
import com.amd.springbootblog.data.ErrorMessageResultObject;
import com.amd.springbootblog.data.FieldErrorResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity responseEntity;
        FieldErrorResultObject fieldErrorResultObject = new FieldErrorResultObject();
        List<com.amd.springbootblog.data.FieldError> list = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
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

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRegister userRegister, BindingResult bindingResult, HttpServletRequest request) {
        ResponseEntity responseEntity;

        FieldErrorResultObject fieldErrorResultObject = new FieldErrorResultObject();
        List<com.amd.springbootblog.data.FieldError> list = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            if (!bindingResult.hasFieldErrors("password") && !bindingResult.hasFieldErrors("confirmPassword")) {
                if (!userRegister.getPassword().equals(userRegister.getConfirmPassword())) {
                    list = fieldErrorResultObject.getFieldsError();
                    list.add(new com.amd.springbootblog.data.FieldError("confirmPassword", "Password is not equal with confirmPassword!"));
                }
            }
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
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

        if (!userRegister.getPassword().equals(userRegister.getConfirmPassword())) {
            list.add(new com.amd.springbootblog.data.FieldError("confirmPassword", "Password is not equal with confirmPassword!"));

            fieldErrorResultObject.setFieldsError(list);
            fieldErrorResultObject.setStatus(ResponseStatus.FIELD_ERROR.getValue());
            fieldErrorResultObject.setResponseStatus(ResponseStatus.FIELD_ERROR);
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrorResultObject);
            return responseEntity;
        }
        BooleanResultObject resultObject = authenticationService.createUser(userRegister);

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

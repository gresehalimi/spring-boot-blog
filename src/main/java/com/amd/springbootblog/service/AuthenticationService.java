package com.amd.springbootblog.service;

import com.amd.springbootblog.data.BooleanResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.dto.UserRegister;
import com.amd.springbootblog.model.Role;
import com.amd.springbootblog.model.RoleName;
import com.amd.springbootblog.model.User;
import com.amd.springbootblog.repository.RoleRepository;
import com.amd.springbootblog.repository.UserRepository;
import com.amd.springbootblog.security.CustomUserDetailsService;
import com.amd.springbootblog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public BooleanResultObject createUser(UserRegister userRegister) {
        BooleanResultObject resultObject = new BooleanResultObject();
        resultObject.setResponseStatus(ResponseStatus.CONFLICT);
        try {
            Optional<User> optionalUser = userRepository.findByEmail(userRegister.getEmail());
            if (optionalUser.isPresent()) {
                resultObject.setStatus(409);
                resultObject.setMessage("Email exists!");
                return resultObject;
            }

            optionalUser = userRepository.findByUsername(userRegister.getUsername());
            if (optionalUser.isPresent()) {
                resultObject.setStatus(409);
                resultObject.setMessage("Username exists!");
                return resultObject;
            }

            Optional<Role> optionalRole = roleRepository.findByName(RoleName.ROLE_USER);
            if (!optionalRole.isPresent()) {
                resultObject.setStatus(409);
                resultObject.setMessage("User Role not set!");
                return resultObject;
            }
            Role userRole = optionalRole.get();

            User user = userRegister.getUser();
            user.setFullName(userRegister.getName());
            user.setUsername(userRegister.getUsername());
            user.setEmail(userRegister.getEmail());
            user.setPassword(userRegister.getPassword());
            user.setCreatedTime(new Date());
            user.setRoles(Collections.singletonList(userRole));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User result = userRepository.save(user);

                   ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/users/{username}")
                    .buildAndExpand(result.getUsername()).toUri();

            resultObject.setStatus(201);
            resultObject.setResponseStatus(ResponseStatus.CREATED);
            resultObject.setMessage("Success!");
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(500);
            resultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
            resultObject.setMessage("Can not be created!");
        }

        return resultObject;
    }
   /* public BooleanResultObject updatePassword(PasswordUpdate passwordUpdate) {
        BooleanResultObject resultObject = new BooleanResultObject();
        resultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        resultObject.setMessage("Incorrect Password");

        Optional<User> optionalUser = userRepository.findById(customUserDetailsService.);
        if (!optionalUser.isPresent()) {
            return resultObject;
        }

        try {

            User user = optionalUser.get();

            if (user.getPassword().equals(passwordUpdate.getOldPassword())) {
                if (passwordUpdate.getPassword().equals(passwordUpdate.getConfirmPassword())) {
                    user.setPassword(passwordUpdate.getConfirmPassword());

                    userRepository.save(user);
                    resultObject.setStatus(201);
                    resultObject.setResponseStatus(ResponseStatus.SUCCESS);
                    resultObject.setMessage("Password Successfully Updated");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(500);
            resultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
            resultObject.setMessage("Incorrect Password!");
        }
        return resultObject;
    }*/

}


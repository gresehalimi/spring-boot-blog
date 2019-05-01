/*
package com.amd.springbootblog.service;

import com.amd.springbootblog.data.BooleanResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.security.UserPrincipal;
import com.amd.springbootblog.model.User;
import com.amd.springbootblog.repository.RoleRepository;
import com.amd.springbootblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public BooleanResultObject create(UserPrincipal userRegister) {
        BooleanResultObject resultObject = new BooleanResultObject();
        resultObject.setResponseStatus(ResponseStatus.CONFLICT);

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



    }
}
*/

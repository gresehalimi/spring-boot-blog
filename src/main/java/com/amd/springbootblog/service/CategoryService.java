package com.amd.springbootblog.service;


import com.amd.springbootblog.data.BooleanResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.model.Category;
import com.amd.springbootblog.model.Role;
import com.amd.springbootblog.model.RoleName;
import com.amd.springbootblog.repository.CategoryRepository;
import com.amd.springbootblog.repository.RoleRepository;
import com.amd.springbootblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public BooleanResultObject create(String categoryName) {
        BooleanResultObject booleanResultObject = new BooleanResultObject();
        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(categoryName);
        if (optionalCategory.isPresent()) {
            booleanResultObject.setStatus(409);
            booleanResultObject.setMessage("Category exists!");
            return booleanResultObject;
        }

        try {
            Optional<Role> optionalRole = roleRepository.findByName(RoleName.ROLE_ADMIN);

            if (!optionalRole.isPresent()) {
                throw new Exception();
            }

            Category category= optionalCategory.get();
            category.setCategoryName(categoryName);
            categoryRepository.save(category);

            booleanResultObject.setStatus(201);
            booleanResultObject.setResponseStatus(ResponseStatus.CREATED);
            booleanResultObject.setMessage("Success!");
        } catch (Exception e) {
            e.printStackTrace();
            booleanResultObject.setStatus(500);
            booleanResultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
            booleanResultObject.setMessage("Can not be created!");
        }

        return booleanResultObject;
    }
}
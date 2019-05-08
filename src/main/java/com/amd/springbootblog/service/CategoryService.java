package com.amd.springbootblog.service;


import com.amd.springbootblog.data.BooleanResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.dto.CategoryData;
import com.amd.springbootblog.model.Category;
import com.amd.springbootblog.model.Role;
import com.amd.springbootblog.model.RoleName;
import com.amd.springbootblog.model.User;
import com.amd.springbootblog.repository.CategoryRepository;
import com.amd.springbootblog.repository.RoleRepository;
import com.amd.springbootblog.repository.UserRepository;
import com.amd.springbootblog.security.UserPrincipal;
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

    public BooleanResultObject create(CategoryData categoryData, UserPrincipal currentUser) {
        BooleanResultObject booleanResultObject = new BooleanResultObject();

        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(categoryData.getCategoryName());

        if (optionalCategory.isPresent()) {
            booleanResultObject.setStatus(409);
            booleanResultObject.setMessage("Category exists!");
            return booleanResultObject;
        }

        try {
            Optional<User> optionalUser = userRepository.findByUsername(currentUser.getUsername());
            if (!optionalUser.isPresent()) {
                booleanResultObject.setResponseStatus(ResponseStatus.NOT_FOUND);
                booleanResultObject.setStatus(404);
                booleanResultObject.setMessage("User with required Username Not Found");
                return booleanResultObject;
            }

            Optional<Role> optionalRole = roleRepository.findByName(RoleName.ROLE_ADMIN);

            if (!optionalRole.isPresent()) {
                booleanResultObject.setResponseStatus(ResponseStatus.NOT_FOUND);
                booleanResultObject.setStatus(404);
                booleanResultObject.setMessage("Admin Role Not found");
                return booleanResultObject;
            }
            if (optionalUser.get().getRoles().equals(optionalRole.get())) {
                Category category = optionalCategory.get();
                category.setCategoryName(categoryData.getCategoryName());
                categoryRepository.save(category);

                booleanResultObject.setStatus(201);
                booleanResultObject.setResponseStatus(ResponseStatus.CREATED);
                booleanResultObject.setMessage("Success!");
            }
            booleanResultObject.setStatus(401);
            booleanResultObject.setResponseStatus(ResponseStatus.NOT_AUTHORIZED);
            booleanResultObject.setMessage("Only Admin User can create new Categories");
        } catch (Exception e) {
            e.printStackTrace();
            booleanResultObject.setStatus(500);
            booleanResultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
            booleanResultObject.setMessage("Can not be created!");
        }

        return booleanResultObject;
    }

    public BooleanResultObject deleteCategory(Long id, UserPrincipal currentUser) {
        BooleanResultObject booleanResultObject = new BooleanResultObject();
        booleanResultObject.setStatus(409);
        booleanResultObject.setResponseStatus(ResponseStatus.CONFLICT);

        try {
            Optional<Category> optionalCategory = categoryRepository.findById(id);

            if (!optionalCategory.isPresent()) {
                booleanResultObject.setResponseStatus(ResponseStatus.NOT_FOUND);
                booleanResultObject.setStatus(404);
                booleanResultObject.setMessage("Category Not found");
                return booleanResultObject;
            }

            Optional<User> optionalUser = userRepository.findByUsername(currentUser.getUsername());
            if (!optionalUser.isPresent()) {
                booleanResultObject.setResponseStatus(ResponseStatus.NOT_FOUND);
                booleanResultObject.setStatus(404);
                booleanResultObject.setMessage("User with required Username Not Found");
                return booleanResultObject;
            }

            Optional<Role> optionalRole = roleRepository.findByName(RoleName.ROLE_ADMIN);

            if (!optionalRole.isPresent()) {
                booleanResultObject.setResponseStatus(ResponseStatus.NOT_FOUND);
                booleanResultObject.setStatus(404);
                booleanResultObject.setMessage("Admin Role Not found");
                return booleanResultObject;
            }
            if (optionalUser.get().getRoles().equals(optionalRole.get())) {
                Category category = optionalCategory.get();
                categoryRepository.delete(category);
                booleanResultObject.setStatus(200);
                booleanResultObject.setResponseStatus(ResponseStatus.SUCCESS);
                booleanResultObject.setMessage("Successfully Deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
            booleanResultObject.setStatus(500);
            booleanResultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
            booleanResultObject.setMessage("Can not be deleted!");
        }

        return booleanResultObject;
    }
}


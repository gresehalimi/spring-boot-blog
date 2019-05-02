package com.amd.springbootblog.service;

import com.amd.springbootblog.data.DataResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.dto.UserData;
import com.amd.springbootblog.repository.PostCommentRepository;
import com.amd.springbootblog.repository.PostRepository;
import com.amd.springbootblog.repository.RoleRepository;
import com.amd.springbootblog.repository.UserRepository;
import com.amd.springbootblog.security.CurrentUser;
import com.amd.springbootblog.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostCommentRepository commentRepository;

    public DataResultObject getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        DataResultObject resultObject = new DataResultObject();
        resultObject.setStatus(409);
        resultObject.setResponseStatus(ResponseStatus.CONFLICT);
        try {
            UserData userData = new UserData(currentUser.getId(), currentUser.getName(), currentUser.getUsername());
            resultObject.setStatus(200);
            resultObject.setResponseStatus(ResponseStatus.SUCCESS);
            resultObject.setData(userData);
        } catch (Exception e) {
            resultObject.setStatus(500);
            resultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return resultObject;
    }

   /* public DataResultObject getUserProfile(String username) {
        DataResultObject resultObject = new DataResultObject();
        resultObject.setResponseStatus(ResponseStatus.CONFLICT);
        try {
            Optional<User> optionalUser = userRepository.findByUsername(username);

            if (!optionalUser.isPresent()) {
                resultObject.setStatus(409);
                resultObject.setResponseStatus(ResponseStatus.NOT_FOUND);
                return resultObject;
            }
            User user = optionalUser.get();
            Long countPost = postRepository.countPostBy(user.getId());
            Long countPostComment = commentRepository.countPostCommentBy(user.getId());
            UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getFullName(), user.getCreatedTime(), countPost, countPostComment);
            resultObject.setStatus(200);
            resultObject.setResponseStatus(ResponseStatus.SUCCESS);
            resultObject.setData(userProfile);
        } catch (Exception e) {
            resultObject.setStatus(500);
            resultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return resultObject;
    }*/

}
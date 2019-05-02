package com.amd.springbootblog.service;

import com.amd.springbootblog.data.BooleanResultObject;
import com.amd.springbootblog.data.PagingResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.dto.PostRegister;
import com.amd.springbootblog.dto.PostUpdate;
import com.amd.springbootblog.model.File;
import com.amd.springbootblog.model.Post;
import com.amd.springbootblog.model.User;
import com.amd.springbootblog.repository.PostRepository;
import com.amd.springbootblog.repository.UserRepository;
import com.amd.springbootblog.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;


    public BooleanResultObject createPost(PostRegister postRegister, UserPrincipal userPrincipal) {
        BooleanResultObject booleanResultObject = new BooleanResultObject();
        booleanResultObject.setStatus(409);
        booleanResultObject.setResponseStatus(ResponseStatus.CONFLICT);

        try {
            Optional<User> optionalUser = userRepository.findById(userPrincipal.getId());
            if (!optionalUser.isPresent()) {
                booleanResultObject.setStatus(404);
                booleanResultObject.setResponseStatus(ResponseStatus.NOT_FOUND);
                booleanResultObject.setMessage("User Not Found");
            }

            Post post = new Post();
            post.setTitle(postRegister.getTitle());
            post.setContent(postRegister.getContent());
            String fileName = StringUtils.cleanPath(postRegister.getMultipartFile().getOriginalFilename());
            if (fileName.contains("..")) {
                booleanResultObject.setMessage("Sorry! Filename contains invalid path sequence ");
                booleanResultObject.setStatus(404);
                booleanResultObject.setResponseStatus(ResponseStatus.NOT_FOUND);
                return booleanResultObject;
            }

            File file = new File(fileName, postRegister.getMultipartFile().getContentType(), postRegister.getMultipartFile().getBytes());
            post.setFile(file);

            // SET Category
            User user = optionalUser.get();
            post.setUser(user);
            postRepository.save(post);
            booleanResultObject.setStatus(200);
            booleanResultObject.setResponseStatus(ResponseStatus.SUCCESS);
            booleanResultObject.setMessage("Post created Successfully");

            if (fileName.contains("..")) {
                booleanResultObject.setMessage("Sorry! Filename contains invalid path sequence ");
                booleanResultObject.setStatus(404);
                booleanResultObject.setResponseStatus(ResponseStatus.NOT_FOUND);
                return booleanResultObject;
            }
        } catch (Exception e) {
            booleanResultObject.setStatus(500);
            booleanResultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return booleanResultObject;
    }

    public BooleanResultObject updatePost(PostUpdate postUpdate) {
        BooleanResultObject booleanResultObject = new BooleanResultObject();
        booleanResultObject.setStatus(409);
        booleanResultObject.setResponseStatus(ResponseStatus.CONFLICT);
        try {
            Optional<Post> optionalPost = postRepository.findById(postUpdate.getId());
            if (!optionalPost.isPresent()) {
                booleanResultObject.setStatus(404);
                booleanResultObject.setResponseStatus(ResponseStatus.NOT_FOUND);
                booleanResultObject.setMessage("Post Not Found");
            }
            Post post = optionalPost.get();

            post.setTitle(postUpdate.getTitle());
            post.setContent(postUpdate.getContent());

            String fileName = StringUtils.cleanPath(postUpdate.getMultipartFile().getOriginalFilename());
            if (fileName.contains("..")) {
                booleanResultObject.setMessage("Sorry! Filename contains invalid path sequence ");
                booleanResultObject.setStatus(404);
                booleanResultObject.setResponseStatus(ResponseStatus.NOT_FOUND);
                return booleanResultObject;
            }
            File file = new File(fileName, postUpdate.getMultipartFile().getContentType(), postUpdate.getMultipartFile().getBytes());
            post.setFile(file);
            // SET Category
            postRepository.save(post);
            booleanResultObject.setStatus(200);
            booleanResultObject.setResponseStatus(ResponseStatus.SUCCESS);
            booleanResultObject.setMessage("Post updated Successfully");
        } catch (Exception e) {
            booleanResultObject.setStatus(500);
            booleanResultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return booleanResultObject;
    }

    public PagingResultObject filterPostsByCategoryAndOrContent(String categoryName, String content, int pageNo) {
        PagingResultObject pagingResultObject = new PagingResultObject();
        pagingResultObject.setResponseStatus(ResponseStatus.CONFLICT);
        pagingResultObject.setStatus(409);
        try {
            if (content == null && categoryName != null) {
                Page<Post> postPage = postRepository.findPostByCategory(categoryName, PageRequest.of(pageNo, 3));
                pagingResultObject.setStatus(200);
                pagingResultObject.setResponseStatus(ResponseStatus.SUCCESS);
                pagingResultObject.setDataList(postPage.getContent());

            } else if (categoryName == null && content != null) {
                Page<Post> postPage = postRepository.findByContent(content, PageRequest.of(pageNo, 3));
                pagingResultObject.setStatus(200);
                pagingResultObject.setResponseStatus(ResponseStatus.SUCCESS);
                pagingResultObject.setDataList(postPage.getContent());
            } else if (content != null && categoryName != null) {
                Page<Post> postPage = postRepository.findPostByCategoryAndContent(categoryName, content, PageRequest.of(pageNo, 3));
                pagingResultObject.setStatus(200);
                pagingResultObject.setResponseStatus(ResponseStatus.SUCCESS);
                pagingResultObject.setDataList(postPage.getContent());
            } else {
                pagingResultObject.setStatus(200);
                pagingResultObject.setResponseStatus(ResponseStatus.SUCCESS);
            }

        } catch (Exception e) {
            pagingResultObject.setStatus(204);
            pagingResultObject.setResponseStatus(ResponseStatus.NO_DATA);
        }
        return pagingResultObject;
    }

}






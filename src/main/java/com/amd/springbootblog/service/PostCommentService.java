package com.amd.springbootblog.service;

import com.amd.springbootblog.data.BooleanResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.dto.PostCommentData;
import com.amd.springbootblog.model.Post;
import com.amd.springbootblog.model.PostComment;
import com.amd.springbootblog.model.User;
import com.amd.springbootblog.repository.PostCommentRepository;
import com.amd.springbootblog.repository.PostRepository;
import com.amd.springbootblog.repository.UserRepository;
import com.amd.springbootblog.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PostCommentService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostCommentRepository postCommentRepository;


    public BooleanResultObject create(PostCommentData postCommentData, UserPrincipal currentUser) {
        BooleanResultObject booleanResultObject = new BooleanResultObject();
        booleanResultObject.setStatus(409);
        booleanResultObject.setResponseStatus(ResponseStatus.CONFLICT);
        try {
            Optional<User> optionalUser = userRepository.findByUsername(currentUser.getUsername());

            if (!optionalUser.isPresent()) {
                booleanResultObject.setStatus(204);
                booleanResultObject.setResponseStatus(ResponseStatus.NO_DATA);
                booleanResultObject.setMessage("The selected Post does not exist in the selected User");
                return booleanResultObject;
            }
            User user = optionalUser.get();
            Optional<Post> optionalPost = postRepository.findById(postCommentData.getPostId());
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();

                PostComment postComment = new PostComment();
                postComment.setUser(user);
                postComment.setPost(post);
                postComment.setCreatedTime(new Date());
                postComment.setComment(postCommentData.getComment());
                postCommentRepository.save(postComment);

                booleanResultObject.setStatus(200);
                booleanResultObject.setResponseStatus(ResponseStatus.SUCCESS);
                booleanResultObject.setMessage("Comment successfully Created");
                return booleanResultObject;
            } else
                booleanResultObject.setMessage("The selected Post does not exists");
        } catch (Exception e) {
            booleanResultObject.setStatus(500);
            booleanResultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return booleanResultObject;
    }

    public BooleanResultObject delete(Long commentId, UserPrincipal currentUser) {
        BooleanResultObject booleanResultObject = new BooleanResultObject();
        booleanResultObject.setStatus(409);
        booleanResultObject.setResponseStatus(ResponseStatus.CONFLICT);
        try {
            Optional<PostComment> optionalPostComment = postCommentRepository.findById(commentId);
            if (!optionalPostComment.isPresent()) {
                booleanResultObject.setStatus(204);
                booleanResultObject.setResponseStatus(ResponseStatus.NO_DATA);
                booleanResultObject.setMessage("Selected comment is not found!");
            }
            PostComment postComment = optionalPostComment.get();
            if (postComment.getUser().getUsername().equals(currentUser.getUsername())) {

                postCommentRepository.delete(postComment);

                booleanResultObject.setStatus(200);
                booleanResultObject.setResponseStatus(ResponseStatus.SUCCESS);
                booleanResultObject.setMessage("Post successfully deleted");
            } else
                booleanResultObject.setMessage("you are not the owner of the post, you can't delete it!");
        } catch (Exception e) {
            booleanResultObject.setStatus(500);
            booleanResultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return booleanResultObject;
    }
}


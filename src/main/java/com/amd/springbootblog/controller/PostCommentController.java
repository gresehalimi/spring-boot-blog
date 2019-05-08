package com.amd.springbootblog.controller;

import com.amd.springbootblog.common.BaseAbstractController;
import com.amd.springbootblog.dto.PostCommentData;
import com.amd.springbootblog.security.CurrentUser;
import com.amd.springbootblog.security.UserPrincipal;
import com.amd.springbootblog.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/")
public class PostCommentController extends BaseAbstractController {

    @Autowired
    PostCommentService postCommentService;

    @PostMapping(value = "/comments/create")
    public ResponseEntity<?> createComment(@RequestBody PostCommentData postCommentData, @CurrentUser UserPrincipal currentUser, HttpServletRequest request) {
        return prepareResponseEntity(postCommentService.create(postCommentData, currentUser), request);
    }

    @DeleteMapping(value = "/comments/delete/{commentId}")
    public ResponseEntity<?> deletePostComment(@PathVariable Long commentId,@CurrentUser UserPrincipal currentUser, HttpServletRequest request) {
        return prepareResponseEntity(postCommentService.delete(commentId,currentUser),request);
    }
}

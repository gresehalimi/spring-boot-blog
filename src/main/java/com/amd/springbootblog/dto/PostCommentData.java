package com.amd.springbootblog.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostCommentData {

    @NotNull
    private Long postId;

    @NotNull
    private String comment;
}

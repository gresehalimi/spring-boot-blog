package com.amd.springbootblog.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostCommentDTO {

    @NotNull
    private Long postId;

    @NotBlank
    private String comment;
}

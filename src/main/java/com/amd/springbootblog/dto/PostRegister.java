package com.amd.springbootblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRegister {

    private String title;

    private String content;

    private FileDto file;

    private List<CategoryModelAttribute> categories;

    private List<PostCommentDTO> comments;

}


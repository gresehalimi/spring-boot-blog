package com.amd.springbootblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private Date joinedAt;
    private Long countPost;
    private Long countComment;
}
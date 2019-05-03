package com.amd.springbootblog.dto;


import com.amd.springbootblog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostData {

    @NotNull
    @NotBlank
    private Long id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    @Size(max = 10000)
    private String content;

    @NotNull
    @NotBlank
    private MultipartFile multipartFile;

    @NotNull
    @NotBlank
    private Long userId;

    private Date createdTime;

    private String userName;

    public PostData (Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.multipartFile = (MultipartFile) post.getFile();
        this.createdTime = post.getCreatedTime();
        this.userId = post.getUser().getId();
        this.userName = post.getUser().getUsername();
    }
}


package com.amd.springbootblog.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdate {

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
    private List<CategoryModelAttribute> categories;

}


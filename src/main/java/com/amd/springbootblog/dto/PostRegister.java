package com.amd.springbootblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRegister {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    @Size(max = 10000)
    private String content;

    private String image;


    @NotNull
    @NotBlank
    private List<CategoryData> categories;
}


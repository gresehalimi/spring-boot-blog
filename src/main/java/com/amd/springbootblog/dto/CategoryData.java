package com.amd.springbootblog.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryData {

    @NotNull
    @NotBlank
    Long id;

    @NotNull
    @NotBlank
    private String categoryName;
}


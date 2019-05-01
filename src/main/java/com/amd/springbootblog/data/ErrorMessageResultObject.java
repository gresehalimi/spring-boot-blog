package com.amd.springbootblog.data;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageResultObject {

    private Date timestamp;

    private Integer status;

    private String error;

    private String message;

    private String path;
}


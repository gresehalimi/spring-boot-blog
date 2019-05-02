package com.amd.springbootblog.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataResultObject<T> {

    private Integer status;

    private ResponseStatus responseStatus;

    private T data;
}

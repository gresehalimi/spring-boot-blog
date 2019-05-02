package com.amd.springbootblog.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagingResultObject<T> {

    private ResponseStatus responseStatus;

    private Integer status;

    private List<T> dataList;
}
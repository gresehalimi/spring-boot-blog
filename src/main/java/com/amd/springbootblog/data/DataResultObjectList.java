package com.amd.springbootblog.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataResultObjectList<T> {

    private Integer status;

    private ResponseStatus responseStatus;

    private List<T> dataList;
}

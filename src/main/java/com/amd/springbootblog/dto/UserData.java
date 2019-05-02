package com.amd.springbootblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserData {

        private Long id;
        private String username;
        private String name;
}

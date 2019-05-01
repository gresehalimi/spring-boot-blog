package com.amd.springbootblog.data;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class FieldError {

    private String field;

    private String message;
}

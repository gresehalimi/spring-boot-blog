package com.amd.springbootblog.dto;

import com.amd.springbootblog.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class UserRegister {

    @NotNull
    @Pattern(regexp = "[a-zA-Z+\" \"]{2,45}$", message = "Name can contain two to 45 letters, no numbers and must not be blank")
    private String name;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9_.]{3,9}$", message = "Username must contain maximum 9 characters, and must not be blank")
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]{6,45}$", message = "Password must contain at least 6 characters, and must not be blank")
    private String password;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]{6,45}$")
    private String confirmPassword;

    public User getUser() {
        return new User(name, username, email);
    }
}

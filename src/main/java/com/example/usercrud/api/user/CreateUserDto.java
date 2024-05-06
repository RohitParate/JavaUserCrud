package com.example.usercrud.api.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserDto {

    @NotBlank
    @NotNull(message = "name is required")
    @Size(min=0, max=20)
    private String name;

    @NotBlank
    @Size(min=0, max=20)
    private String userName;

    @NotBlank
    private String password;
}

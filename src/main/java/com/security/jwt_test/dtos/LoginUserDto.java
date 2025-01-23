package com.security.jwt_test.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class LoginUserDto {
    private String email;
    private String password;
    private String token;
    private long expiresIn;
}

package com.tma.demo.dtos.requests;

import com.tma.demo.entities.jpa.UserJPA;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequest {
    private String username;
    private String password;
    private String confirmPassword;

    public boolean isValidPassword(){
        return password.length() >= 5;
    }

    public boolean isValidConfirmPassword(){
        return password.equals(confirmPassword);
    }

    public UserJPA toUserJPA(){
        UserJPA userJPA = new UserJPA();
        userJPA.setUserId(UUID.randomUUID());
        userJPA.setUsername(username);
        userJPA.setPassword(password);
        return userJPA;
    }
}

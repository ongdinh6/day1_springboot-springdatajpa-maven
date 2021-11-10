package com.tma.demo.dtos.requests;

import com.tma.demo.entities.jpa.UserJPA;
import com.tma.demo.exceptions.BadRequestException;
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

    public boolean isValidPassword() {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!password.matches(pattern)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isValidConfirmPassword() {
        return password.equals(confirmPassword);
    }

    public UserJPA toUserJPA() {
        UserJPA userJPA = new UserJPA();
        userJPA.setUserId(UUID.randomUUID());
        userJPA.setUsername(username);
        userJPA.setPassword(password);
        return userJPA;
    }
}

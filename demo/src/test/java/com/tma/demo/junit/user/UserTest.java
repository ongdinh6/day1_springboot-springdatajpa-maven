package com.tma.demo.junit.user;

import com.tma.demo.dtos.requests.UserRequest;
import com.tma.demo.entities.jpa.RoleJPA;
import com.tma.demo.entities.jpa.UserJPA;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class UserTest {
    private UserJPA userJPA;
    private UserRequest userRequest;

    public UserTest() {
        userJPA = new UserJPA();
        userRequest = new UserRequest();
    }


    @BeforeEach
    void setUp() {
        userRequest.setPassword("123abcA@");
        userRequest.setUsername("test_username");
        userJPA = userRequest.toUserJPA();

        //init for user jpa to test
        Set<RoleJPA> roles = new HashSet<>();
        RoleJPA roleJPA = new RoleJPA();
        roleJPA.setRoleId(UUID.randomUUID());
        roleJPA.setRoleString("USER");
        roleJPA.setDescription("User access");
        roles.add(roleJPA);
        userJPA.setRoles(roles);
    }

    @Test
    void test_valid_password() {
        String password = "123abAB@";
        userRequest.setPassword(password);
        boolean result = userRequest.isValidPassword();

        assertTrue(result);
    }

    @Test
    void test_invalid_password() {
        String password = "123aAB@";
        userRequest.setPassword(password);
        boolean result = userRequest.isValidPassword();

        Assert.assertFalse(result);
    }

    @Test
    void test_invalid_enoughCharacter_notEqualsPattern_password() {
        String password = "123456789";
        userRequest.setPassword(password);
        boolean result = userRequest.isValidPassword();

        Assert.assertFalse(result);
    }

    @Test
    void test_isUser_UserJPA() {
        assertTrue(userJPA.isUser());
    }

    @Test
    void test_isAdmin_UserJPA() {
        Set<RoleJPA> roles = new HashSet<>();
        RoleJPA roleJPA = new RoleJPA();
        roleJPA.setRoleId(UUID.randomUUID());
        roleJPA.setRoleString("ADMIN");
        roleJPA.setDescription("Administrator access");
        userJPA.getRoles().add(roleJPA);
        assertTrue(userJPA.isAdmin());
    }

}

package com.tma.demo.integration.controller_layer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tma.demo.configs.spring_security.JwtAuthenticationEntryPoint;
import com.tma.demo.configs.spring_security.JwtTokenUtil;
import com.tma.demo.controllers.jpa.UserJPAController;
import com.tma.demo.dtos.requests.LoginFormRequest;
import com.tma.demo.dtos.requests.UserRequest;
import com.tma.demo.dtos.responses.AuthorizationResponse;
import com.tma.demo.dtos.responses.MyUserDetail;
import com.tma.demo.entities.jpa.RoleJPA;
import com.tma.demo.entities.jpa.UserJPA;
import com.tma.demo.services.jpa.IUserJPAService;
import com.tma.demo.services.jpa.impls.JwtUserDetailsService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.isEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*test restful api with json web token authorization*/
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserJPAController.class)
public class UserJpaControllerTest {
    /*UserController we have:
     * 1. register account (unauthorized)
     * 2. login (unauthorized)
     * 3. get user info (authorized)
     * */
    @Autowired
    MockMvc mockMvc;
    @InjectMocks
    UserJPAController userJpaController;
    @MockBean
    IUserJPAService userJPAService;
    @MockBean
    JwtUserDetailsService userDetailsService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    UserDetails userDetails;

    private AuthorizationResponse authorizationResponse;
    static String url = "/api/v1";
    private static String jwt = "Bearer ";

    /*test register account have many case, such as test-success, test-bad-request, test-internal-error*/
    @Test
    void testRegisterAccount_shouldSucceedWithStatus200() {
        //step1: create mock data return
        String username = "nguyenvana";
        String password = "1234abcdA@";
        Set<RoleJPA> roles = new HashSet<RoleJPA>();
        roles.add(new RoleJPA(UUID.randomUUID(), "USER", "User access", null));
        UserJPA userJpa = new UserJPA();
        userJpa.setUsername(username);
        /*password here will be encrypted by PasswordEncoder object*/
        userJpa.setPassword(password);

        //userRequest
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setConfirmPassword(password);


        //step2: define behavior
        authorizationResponse = userJpa.toAuthorizationResponse();
        Mockito.when(userJPAService.save(userRequest)).thenReturn(authorizationResponse);

        //step3: call method and assert
        try {
            //we want to method will be return status 200 OK.
            mockMvc.perform(post(url + "/register")
                    .content(new ObjectMapper().writeValueAsString(userRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testLoginMethod_shouldSucceedWithStatus200() {
        //step1: create user authenticate mock data
        LoginFormRequest loginFormRequest = new LoginFormRequest();
        loginFormRequest.setUsername("anguyen");
        loginFormRequest.setPassword("12345");

        //step2: define behavior
        /*2.1.  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        *2.2.  userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        * 2.3. jwtTokenUtil.generateToken(userDetails);
        * 2.4. userJPAService.getByUsername(authenticationRequest.getUsername());
        * */
        UsernamePasswordAuthenticationToken authenticate = new UsernamePasswordAuthenticationToken(loginFormRequest.getUsername(), loginFormRequest.getUsername());
        Mockito.when(authenticationManager.authenticate(authenticate)).thenReturn(authenticate);

        //2.2
        UserJPA userJpa = new UserJPA();
        userJpa.setUserId(UUID.randomUUID());
        userJpa.setUsername(loginFormRequest.getUsername());
        userJpa.setPassword(loginFormRequest.getPassword());
        Set<RoleJPA> roles = new HashSet<>();
        RoleJPA roleJpa;
        roles.add(roleJpa = new RoleJPA(UUID.randomUUID(), "USER", "User access", null));
        userJpa.setRoles(roles);
        Collection<SimpleGrantedAuthority> authoritiesGranted = new ArrayList<>();
        authoritiesGranted.add(new SimpleGrantedAuthority("ROLE_USER"));

        Mockito.when(userJPAService.getByUsername(anyString())).thenReturn(userJpa);//mockito

        MyUserDetail myUserDetail = new MyUserDetail(userJPAService.getByUsername(loginFormRequest.getUsername()),
                loginFormRequest.getUsername(), loginFormRequest.getPassword(), authoritiesGranted);

        Mockito.when(userDetailsService.loadUserByUsername(anyString())).thenReturn(myUserDetail);

        //2.3 generate token
        String token = jwtTokenUtil.generateToken(myUserDetail);
        Mockito.when(jwtTokenUtil.generateToken(ArgumentMatchers.any(UserDetails.class))).thenReturn(token);

        //4.assert
        try {
            mockMvc.perform(post(url + "/login")
                    .content(new ObjectMapper().writeValueAsString(loginFormRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
//                    .andExpect(jsonPath("$.jwt", is(new ObjectMapper()token)));
//            Assert.assertEquals();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @BeforeEach
    void setUp() {
        //step1: create user authenticate mock data
        LoginFormRequest loginFormRequest = new LoginFormRequest();
        loginFormRequest.setUsername("anguyen");
        loginFormRequest.setPassword("12345");

        //step2: define behavior
        /*2.1.  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        *2.2.  userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        * 2.3. jwtTokenUtil.generateToken(userDetails);
        * 2.4. userJPAService.getByUsername(authenticationRequest.getUsername());
        * */
        UsernamePasswordAuthenticationToken authenticate = new UsernamePasswordAuthenticationToken(loginFormRequest.getUsername(), loginFormRequest.getUsername());
        Mockito.when(authenticationManager.authenticate(authenticate)).thenReturn(authenticate);

        //2.2
        UserJPA userJpa = new UserJPA();
        userJpa.setUserId(UUID.randomUUID());
        userJpa.setUsername(loginFormRequest.getUsername());
        userJpa.setPassword(loginFormRequest.getPassword());
        Set<RoleJPA> roles = new HashSet<>();
        RoleJPA roleJpa;
        roles.add(roleJpa = new RoleJPA(UUID.randomUUID(), "USER", "User access", null));
        userJpa.setRoles(roles);
        Collection<SimpleGrantedAuthority> authoritiesGranted = new ArrayList<>();
        authoritiesGranted.add(new SimpleGrantedAuthority("ROLE_USER"));

        Mockito.when(userJPAService.getByUsername(anyString())).thenReturn(userJpa);//mockito

        MyUserDetail myUserDetail = new MyUserDetail(userJPAService.getByUsername(loginFormRequest.getUsername()),
                loginFormRequest.getUsername(), loginFormRequest.getPassword(), authoritiesGranted);

        Mockito.when(userDetailsService.loadUserByUsername(anyString())).thenReturn(myUserDetail);

        //2.3 generate token
        String token = jwtTokenUtil.generateToken(myUserDetail);
        Mockito.when(jwtTokenUtil.generateToken(myUserDetail)).thenReturn(token);

        jwt += token;
    }

    /*forbidden exception*/
    @Test
    void testGetUserInfo_unauthorizedShouldSucceedWith403() {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/v1/users/info").contentType(MediaType.APPLICATION_JSON)
                .with(user("anguyen").password("12345").authorities(new SimpleGrantedAuthority("ROLE_USER")));

        //step1: create user authenticate mock data
        LoginFormRequest loginFormRequest = new LoginFormRequest();
        loginFormRequest.setUsername("anguyen");
        loginFormRequest.setPassword("12345");

        //step2: define behavior
        /*2.1.  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        *2.2.  userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        * 2.3. jwtTokenUtil.generateToken(userDetails);
        * 2.4. userJPAService.getByUsername(authenticationRequest.getUsername());
        * */
        UsernamePasswordAuthenticationToken authenticate = new UsernamePasswordAuthenticationToken(loginFormRequest.getUsername(), loginFormRequest.getUsername());
        Mockito.when(authenticationManager.authenticate(authenticate)).thenReturn(authenticate);

        //2.2
        UserJPA userJpa = new UserJPA();
        userJpa.setUserId(UUID.randomUUID());
        userJpa.setUsername(loginFormRequest.getUsername());
        userJpa.setPassword(loginFormRequest.getPassword());
        Set<RoleJPA> roles = new HashSet<>();
        RoleJPA roleJpa;
        roles.add(roleJpa = new RoleJPA(UUID.randomUUID(), "USER", "User access", null));
        userJpa.setRoles(roles);
        Collection<SimpleGrantedAuthority> authoritiesGranted = new ArrayList<>();
        authoritiesGranted.add(new SimpleGrantedAuthority("ROLE_USER"));

        Mockito.when(userJPAService.getByUsername(anyString())).thenReturn(userJpa);//mockito

        MyUserDetail myUserDetail = new MyUserDetail(userJPAService.getByUsername(loginFormRequest.getUsername()),
                loginFormRequest.getUsername(), loginFormRequest.getPassword(), authoritiesGranted);

        Mockito.when(userDetailsService.loadUserByUsername(anyString())).thenReturn(myUserDetail);

        //2.3 generate token
        try {
            String token = jwtTokenUtil.generateToken(myUserDetail);
            Mockito.when(jwtTokenUtil.generateToken(myUserDetail)).thenReturn(token);
            mockMvc.perform(get(url + "/users/info").header("Authorization", token)).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetUserInfo_authorizedShouldSucceedWith200() {

    }
}

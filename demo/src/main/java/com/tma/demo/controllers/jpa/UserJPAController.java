package com.tma.demo.controllers.jpa;

import com.tma.demo.configs.spring_security.JwtTokenUtil;
import com.tma.demo.dtos.DataResponse;
import com.tma.demo.dtos.requests.LoginFormRequest;
import com.tma.demo.dtos.requests.UserRequest;
import com.tma.demo.dtos.responses.AuthorizationResponse;
import com.tma.demo.dtos.responses.JwtAuthenticateResponse;
import com.tma.demo.dtos.responses.MyUserDetail;
import com.tma.demo.entities.jpa.UserJPA;
import com.tma.demo.exceptions.BadRequestException;
import com.tma.demo.services.jpa.IUserJPAService;
import com.tma.demo.services.jpa.impls.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserJPAController {
    @Autowired
    IUserJPAService userJPAService;
    @Autowired
    JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public DataResponse<AuthorizationResponse> doRegisterAccount(@RequestBody UserRequest userRequest) {
        return new DataResponse<>(userJPAService.save(userRequest), HttpStatus.OK, "Register an account is successful!");
    }

    @GetMapping("/users/info")
    public DataResponse<AuthorizationResponse> getMyInfo() {
        String userId = MyUserDetail.getUserIns().getUserId().toString();
        return new DataResponse<>(userJPAService.getById(userId), HttpStatus.OK, "Request is called successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginFormRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        UserJPA userJPA = userJPAService.getByUsername(authenticationRequest.getUsername());
        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setUserId(userJPA.getUserId());
        authorizationResponse.setUsername(userJPA.getUsername());
        authorizationResponse.setRoles(userJPA.getRoles());

        return ResponseEntity.ok(new JwtAuthenticateResponse(token, authorizationResponse));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            //catch exception active, but this app always into this block statement
            throw new BadRequestException("Your account is inactive, please active account or contact with administrator for help!");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Your email or password is incorrect!");
        }
    }
}

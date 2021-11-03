package com.tma.demo.services.jpa.impls;

import com.tma.demo.dtos.responses.MyUserDetail;
import com.tma.demo.entities.jpa.UserJPA;
import com.tma.demo.repositories.jpa.IUserJPARepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/*
JWTUserDetailsService implements the Spring Security UserDetailsService interface.
It overrides the loadUserByUsername for fetching user details from the database using the username.
The Spring Security Authentication Manager calls this method for getting the user details from the database
when authenticating the user details provided by the user. Here we are getting the user details from a hardcoded
User List. In the next tutorial we will be adding the DAO implementation for fetching User Details from the Database.
Also the password for a user is stored in encrypted format using BCrypt.
Previously we have seen Spring Boot Security - Password Encoding Using Bcrypt.
Here using the Online Bcrypt Generator you can generate the Bcrypt for a password.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IUserJPARepository userJPARepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserJPA userJPA = userJPARepository.findByUsername(username);
        if (userJPA == null) {
            throw new UsernameNotFoundException("Not found an user with username \'" + username+"\'");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String role = userJPA.isAdmin() ? "ROLE_ADMIN" : userJPA.isUser() ? "ROLE_USER" : "ROLE_GUEST";
        authorities.add(new SimpleGrantedAuthority(role));
        MyUserDetail userDetail = new MyUserDetail(userJPA, userJPA.getUsername(), userJPA.getPassword(), authorities);
        return userDetail;
    }


}
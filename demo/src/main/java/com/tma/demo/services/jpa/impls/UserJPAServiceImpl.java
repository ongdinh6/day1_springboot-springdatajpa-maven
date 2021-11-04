package com.tma.demo.services.jpa.impls;

import com.tma.demo.dtos.requests.UserRequest;
import com.tma.demo.dtos.responses.AuthorizationResponse;
import com.tma.demo.entities.jpa.RoleJPA;
import com.tma.demo.entities.jpa.UserJPA;
import com.tma.demo.exceptions.BadRequestException;
import com.tma.demo.exceptions.NotFoundException;
import com.tma.demo.repositories.jpa.IRoleJPARepository;
import com.tma.demo.repositories.jpa.IUserJPARepository;
import com.tma.demo.services.jpa.IRoleJPAService;
import com.tma.demo.services.jpa.IUserJPAService;
import com.tma.demo.utils.LogUtil;
import com.tma.demo.utils.UUIDHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserJPAServiceImpl implements IUserJPAService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    LogUtil logUtil = LogUtil.getInstance();
    @Autowired
    IUserJPARepository userJPARepository;
    @Autowired
    IRoleJPARepository roleJPARepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public AuthorizationResponse save(UserRequest userRequest) {
        if (!userRequest.isValidPassword()) throw new BadRequestException("Password must be least 5 characters!");
        if (!userRequest.isValidConfirmPassword()) throw new BadRequestException("Confirm password not match!");
        UserJPA userJPA = userRequest.toUserJPA();
        //need to encrypt password before to save
        userJPA.setPassword(passwordEncoder.encode(userJPA.getPassword()));
        RoleJPA roleJPA = roleJPARepository.getByRoleString("USER");
        System.out.println("ROLE JPA = "+roleJPA.getDescription());
        userJPA.getRoles().add(roleJPA);
        userJPA.getRoles().stream().forEach(x->{
            System.out.println("role of user "+x.getRoleId()+" - "+x.getRoleString());
        });
        return userJPARepository.save(userJPA).toAuthorizationResponse();
    }

    @Override
    public AuthorizationResponse getById(String userId) {
        if (!userJPARepository.existsById(UUIDHelper.toUUID(userId))) {
            logger.error("Not found an user with id " + userId);
            logUtil.setLogUtil("Not found an user with id " + userId, logger);
            throw new NotFoundException("Not found an user with id " + userId);
        } else {
            return userJPARepository.getById(UUIDHelper.toUUID(userId)).toAuthorizationResponse();
        }
    }

    @Override
    public UserJPA getByUsername(String username) {
        UserJPA userJPA;
        if (null == (userJPA = userJPARepository.findByUsername(username))) {
            throw new NotFoundException("Not found a user with username \'" + username + "\'");
        } else {
            return userJPA;
        }
    }
}

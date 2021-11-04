package com.tma.demo.services.jpa;

import com.tma.demo.dtos.requests.UserRequest;
import com.tma.demo.dtos.responses.AuthorizationResponse;
import com.tma.demo.entities.jpa.UserJPA;

public interface IUserJPAService {
    AuthorizationResponse save(UserRequest userRequest);
    AuthorizationResponse getById(String userId);
    UserJPA getByUsername(String username);
}

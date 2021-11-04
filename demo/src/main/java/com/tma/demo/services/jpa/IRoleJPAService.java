package com.tma.demo.services.jpa;

import com.tma.demo.dtos.requests.RoleRequest;
import com.tma.demo.entities.jpa.RoleJPA;

import java.util.List;

public interface IRoleJPAService {
    RoleJPA save(RoleRequest roleRequest);
    List<RoleJPA> getAllRole();
}

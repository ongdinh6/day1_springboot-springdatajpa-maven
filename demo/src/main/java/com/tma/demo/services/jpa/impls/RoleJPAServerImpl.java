package com.tma.demo.services.jpa.impls;

import com.tma.demo.dtos.requests.RoleRequest;
import com.tma.demo.entities.jpa.RoleJPA;
import com.tma.demo.repositories.jpa.IRoleJPARepository;
import com.tma.demo.services.jpa.IRoleJPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleJPAServerImpl implements IRoleJPAService {
    @Autowired
    IRoleJPARepository roleJPARepository;

    @Override
    public RoleJPA save(RoleRequest roleRequest) {
        return roleJPARepository.save(roleRequest.toRoleJPA());
    }

    @Override
    public List<RoleJPA> getAllRole() {
        return roleJPARepository.findAll();
    }
}

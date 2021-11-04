package com.tma.demo.controllers.jpa;

import com.tma.demo.dtos.DataResponse;
import com.tma.demo.dtos.requests.RoleRequest;
import com.tma.demo.entities.jpa.RoleJPA;
import com.tma.demo.services.jpa.IRoleJPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleJPAController {
    @Autowired
    IRoleJPAService roleJPAService;

    @PostMapping("")
    public DataResponse<RoleJPA> addNewRole(@RequestBody RoleRequest roleRequest){
        return new DataResponse<>(roleJPAService.save(roleRequest), HttpStatus.OK, "Add new a role is successful!");
    }

    @GetMapping("")
    public DataResponse<List<RoleJPA>> getListRole(){
        return new DataResponse<>(roleJPAService.getAllRole(), HttpStatus.OK, "Get all role is successful.");
    }
}

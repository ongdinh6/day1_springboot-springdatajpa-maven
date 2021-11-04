package com.tma.demo.dtos.requests;

import com.tma.demo.entities.jpa.RoleJPA;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    private String role;
    private String description;

    public RoleJPA toRoleJPA(){
        RoleJPA roleJPA = new RoleJPA();
        roleJPA.setRoleId(UUID.randomUUID());
        roleJPA.setRoleString(role);
        roleJPA.setDescription(description);

        return roleJPA;
    }
}

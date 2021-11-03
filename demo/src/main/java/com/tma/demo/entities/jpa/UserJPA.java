package com.tma.demo.entities.jpa;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserJPA {
    @Id
    @Column(name = "user_id")
    private UUID userId;
    private String username;
    private String password;
    @ManyToMany(mappedBy = "users")
    private Set<RoleJPA> roles;

    public boolean isAdmin() {
        boolean[] rs = new boolean[1];
        roles.forEach(x -> {
            if (x.getRoleString().equalsIgnoreCase("admin")) {
                rs[0] = true;
            }
        });
        return rs[0];
    }
    public boolean isUser() {
        boolean[] rs = new boolean[1];
        roles.forEach(x -> {
            if (x.getRoleString().equalsIgnoreCase("user")) {
                rs[0] = true;
            }
        });
        return rs[0];
    }
}

package com.tma.demo.entities.jpa;


import com.tma.demo.dtos.responses.AuthorizationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserJPA {
    @Id
    @Type(type = "pg-uuid")
    @Column(name = "user_id", columnDefinition = "uuid")
    private UUID userId;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<RoleJPA> roles = new HashSet<>();

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

    public AuthorizationResponse toAuthorizationResponse(){
        AuthorizationResponse author = new AuthorizationResponse();
        author.setUserId(userId);
        author.setUsername(username);
        author.setRoles(roles);

        return author;
    }
}

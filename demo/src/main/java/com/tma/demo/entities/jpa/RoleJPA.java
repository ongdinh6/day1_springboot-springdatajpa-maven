package com.tma.demo.entities.jpa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class RoleJPA {
    @Id
    @Column(name = "role_id")
    private UUID roleId;
    @Column(name = "role", nullable = false, unique = true)
    private String roleString;
    @Column(name = "description")
    private String description;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<UserJPA> users = new HashSet<>();
}

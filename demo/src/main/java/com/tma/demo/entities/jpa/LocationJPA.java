package com.tma.demo.entities.jpa;


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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "locations")
@Entity
public class LocationJPA {
    @Id
    @Type(type = "pg-uuid")
    @Column(name = "location_id", columnDefinition = "uuid")
    private UUID locationId;
    private String country;
    private String city;
    @OneToMany(
            mappedBy = "locationJPA",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<SaleJPA> saleJPAS = new HashSet<>();
}

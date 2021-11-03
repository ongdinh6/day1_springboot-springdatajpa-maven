package com.tma.demo.entities.jpa;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "location")
@Entity
public class LocationJPA {
    @Id
    @Column(name = "location_id")
    private UUID locationId;
    private String country;
    private String city;
    @ManyToOne(fetch = FetchType.LAZY)
    private SaleJPA saleJPA;
}

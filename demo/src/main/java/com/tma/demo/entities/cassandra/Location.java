package com.tma.demo.entities.cassandra;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("locations")
public class Location {
    @PrimaryKey
    @CassandraType(type = CassandraType.Name.UUID)
    @Column("location_id")
    private UUID locationId;
    private String country;
    private String city;
}

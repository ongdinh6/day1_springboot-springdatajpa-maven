package com.tma.demo.entities.cassandra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("time")
public class Time {
    @PrimaryKey
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID timeId;
    private int month;
    private int quarter;
    private int year;
}

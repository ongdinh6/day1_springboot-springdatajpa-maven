package com.tma.demo.entities.cassandra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("sales")
public class Sale {
    @PrimaryKey
    @CassandraType(type = CassandraType.Name.UUID)
    @Column("product_id")
    private UUID saleId;
    @CassandraType(type = CassandraType.Name.UUID)
    @Column("product_id")
    private UUID productId;
    @CassandraType(type = CassandraType.Name.UUID)
    @Column("time_id")
    private UUID timeId;
    @CassandraType(type = CassandraType.Name.UUID)
    @Column("location_id")
    private UUID locationId;
    private BigDecimal dollars;
}

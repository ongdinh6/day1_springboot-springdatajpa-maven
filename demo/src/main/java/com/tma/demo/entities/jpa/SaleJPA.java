package com.tma.demo.entities.jpa;

import com.fasterxml.jackson.databind.deser.BuilderBasedDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sales")
@Entity
public class SaleJPA {
    @Id
    @Type(type = "pg-uuid")
    @Column(name = "sale_id", columnDefinition = "uuid")
    private UUID saleId;
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductJPA productJPA;
    @ManyToOne(fetch = FetchType.LAZY)
    private LocationJPA locationJPA;
    @ManyToOne(fetch = FetchType.LAZY)
    private TimeJPA timeJPA;
    private BigDecimal dollars;
}

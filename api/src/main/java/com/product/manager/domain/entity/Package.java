package com.product.manager.domain.entity;

import com.product.manager.enumeration.ExchangeRate;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

import static org.hibernate.engine.spi.CascadeStyles.ALL;
import static org.hibernate.engine.spi.CascadeStyles.ALL_DELETE_ORPHAN;

@Entity
@Data
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String name;
    private String description;
    private BigDecimal totalPrice;
    private ExchangeRate exchangeRate;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name ="productIds")
    private Set<String> products;
}

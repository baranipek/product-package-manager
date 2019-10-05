package com.product.manager.domain.request;

import com.product.manager.enumeration.ExchangeRate;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class PackageUpdateRequest  implements Serializable {
    private Long id;
    private String description;
    private Set<String> products;
    public String name;
    ExchangeRate exchangeRate;
}

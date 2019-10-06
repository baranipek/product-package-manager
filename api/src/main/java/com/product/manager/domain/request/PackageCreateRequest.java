package com.product.manager.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.product.manager.enumeration.ExchangeRate;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;


@Data
public class PackageCreateRequest implements Serializable {
    private String description;
    private Set<String> products;
    public String name;
    ExchangeRate exchangeRate;
}

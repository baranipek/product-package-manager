package com.product.manager.domain.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private String id;
    private String name;
    private BigDecimal usdPrice;
}

package com.product.manager.domain.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExchangeRateResponse {
    private Map<String, BigDecimal> rates;
}

package com.product.manager.service;

import com.product.manager.client.ExchangeClient;
import com.product.manager.domain.request.PackageCreateRequest;
import com.product.manager.domain.response.ExchangeRateResponse;
import com.product.manager.enumeration.ExchangeRate;
import com.product.manager.exception.RateExternalServiceNoContentException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;

@Service
public class ValidationService {

    private final ExchangeClient exchangeClient;

    public ValidationService(ExchangeClient exchangeClient) {
        this.exchangeClient = exchangeClient;
    }

    void validate(PackageCreateRequest request) {
        if (CollectionUtils.isEmpty(request.getProducts())) {
            throw new IllegalArgumentException("Product can not be empty");
        }
        if (request.getDescription() == null || request.getName() == null) {
            throw new IllegalArgumentException("Product description and name can not be empty");
        }
    }

    BigDecimal checkRate(ExchangeRate exchangeRate) {
        ExchangeRateResponse rateResponse = exchangeClient.getExchangeRates().getBody();
        if (rateResponse == null || rateResponse.getRates() == null) {
            throw new RateExternalServiceNoContentException("Rate service provider do not return information");
        }

        BigDecimal rate = rateResponse.getRates().get(exchangeRate.name());
        if (rate == null) {
            throw new IllegalArgumentException("Currency is not recognized");
        }
        return rate;
    }
}

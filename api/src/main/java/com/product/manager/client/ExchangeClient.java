package com.product.manager.client;

import com.product.manager.configuration.ClientConfiguration;
import com.product.manager.domain.response.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "exchangeClient", url = "${rate.api.base.url}", configuration = ClientConfiguration.class)
public interface ExchangeClient {
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<ExchangeRateResponse> getExchangeRates();
}

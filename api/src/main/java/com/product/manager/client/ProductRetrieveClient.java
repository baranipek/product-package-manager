
package com.product.manager.client;


import com.product.manager.configuration.ClientConfiguration;
import com.product.manager.domain.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "productRetrieveClient", url =  "${product.api.url}", configuration = ClientConfiguration.class)
public interface ProductRetrieveClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{productId}")
    ResponseEntity<ProductResponse> getProduct(@PathVariable("productId") String productId);

}


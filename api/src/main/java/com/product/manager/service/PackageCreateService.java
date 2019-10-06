package com.product.manager.service;

import com.product.manager.client.ExchangeClient;
import com.product.manager.client.ProductRetrieveClient;
import com.product.manager.domain.entity.Package;
import com.product.manager.domain.request.PackageCreateRequest;
import com.product.manager.domain.response.ExchangeRateResponse;
import com.product.manager.domain.response.ProductResponse;
import com.product.manager.enumeration.ExchangeRate;
import com.product.manager.exception.RateExternalServiceNoContentException;
import com.product.manager.repository.PackageRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageCreateService {

    private final PackageRepository packageRepository;
    private final ProductRetrieveClient productRetrieveClient;
    private final ValidationService validationService;

    public PackageCreateService(PackageRepository repository, ProductRetrieveClient productRetrieveClient, ValidationService validationService) {
        this.packageRepository = repository;
        this.productRetrieveClient = productRetrieveClient;
        this.validationService = validationService;
    }

    public Package save(PackageCreateRequest request) {
        validationService.validate(request);
        if (request.getExchangeRate() == null) {
            request.setExchangeRate(ExchangeRate.USD);
        }

        List<ProductResponse> productResponses = request.getProducts().stream().map(productId -> productRetrieveClient.getProduct(productId).getBody()).
                collect(Collectors.toList());

        if (CollectionUtils.isEmpty(productResponses)) {
            throw new IllegalArgumentException("product is not recognized");
        }

        Package productPackage = this.decoratePackage(request, productResponses);
        return packageRepository.save(productPackage);
    }

    private Package decoratePackage(PackageCreateRequest request, List<ProductResponse> productResponses) {
        BigDecimal productPackageTotal = productResponses.stream()
                .map(ProductResponse::getUsdPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Package productPackage = new Package();
        productPackage.setDescription(request.getDescription());
        productPackage.setName(request.getName());
        productPackage.setExchangeRate(request.getExchangeRate());
        productPackage.setProducts(request.getProducts());

        if (request.getExchangeRate() == ExchangeRate.USD) {
            productPackage.setTotalPrice(productPackageTotal);
        } else {
            BigDecimal rate = validationService.checkRate(request.getExchangeRate());
            productPackage.setTotalPrice(productPackageTotal.multiply(rate));
        }

        return productPackage;
    }


}

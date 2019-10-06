package com.product.manager.service;

import com.product.manager.client.ExchangeClient;
import com.product.manager.client.ProductRetrieveClient;
import com.product.manager.domain.entity.Package;
import com.product.manager.domain.request.PackageUpdateRequest;
import com.product.manager.domain.response.ProductResponse;
import com.product.manager.enumeration.ExchangeRate;
import com.product.manager.repository.PackageRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackageUpdateService {

    private final PackageRepository repository;
    private final ProductRetrieveClient productRetrieveClient;
    private final ValidationService validationService;


    public PackageUpdateService(PackageRepository repository, ProductRetrieveClient productRetrieveClient, ValidationService validationService) {
        this.repository = repository;
        this.validationService = validationService;
        this.productRetrieveClient = productRetrieveClient;
    }

    public void update(PackageUpdateRequest request) {
        if (request.getExchangeRate() == null) {
            request.setExchangeRate(ExchangeRate.USD);
        }
        Optional<Package> packageOptional = repository.findById(request.getId());
        if (!packageOptional.isPresent()) {
            throw new IllegalArgumentException("no package to update" + request.getId());
        }

        List<ProductResponse> productResponses = request.getProducts().stream().map(productId -> productRetrieveClient.getProduct(productId).getBody()).
                collect(Collectors.toList());

        if (CollectionUtils.isEmpty(productResponses)) {
            throw new IllegalArgumentException("product is not recognized");
        }

        Package productPackage = this.updatePackage(request, packageOptional, productResponses);
        repository.save(productPackage);
    }

    private Package updatePackage(PackageUpdateRequest request, Optional<Package> packageOptional, List<ProductResponse> productResponses) {
        Package productPackage = packageOptional.get();
        productPackage.setName(request.getName());
        productPackage.setDescription(request.getDescription());

        BigDecimal productPackageTotal = productResponses.stream().map(ProductResponse::getUsdPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (request.getExchangeRate() == ExchangeRate.USD) {
            productPackage.setTotalPrice(productPackageTotal);
        } else {
            BigDecimal rate = validationService.checkRate(request.getExchangeRate());
            BigDecimal productPackageTotalRateConverted = productPackageTotal.multiply(rate);
            productPackage.setTotalPrice(productPackageTotalRateConverted);
        }

        productPackage.setProducts(request.getProducts());
        productPackage.setExchangeRate(request.getExchangeRate());
        return productPackage;
    }

    public void delete(Long packageId) {
        repository.deleteById(packageId);
    }
}

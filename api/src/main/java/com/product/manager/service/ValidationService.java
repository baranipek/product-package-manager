package com.product.manager.service;

import com.product.manager.domain.request.PackageCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ValidationService {
    void validate(PackageCreateRequest request) {
        if (CollectionUtils.isEmpty(request.getProducts())) {
            throw new IllegalArgumentException("Product can not be empty");
        }
        if (request.getDescription() == null || request.getName() == null) {
            throw new IllegalArgumentException("Product description and name can not be empty");
        }
    }
}

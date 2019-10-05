package com.product.manager.api;

import com.product.manager.domain.entity.Package;
import com.product.manager.domain.request.PackageCreateRequest;
import com.product.manager.service.PackageCreateService;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/packages")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8080" })
public class PackageCreateApi {
    private final PackageCreateService createService;

    public PackageCreateApi(PackageCreateService createService) {
        this.createService = createService;
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody PackageCreateRequest request) {
        Package productPackage = createService.save(request);
        return new ResponseEntity<>(productPackage.getId(),HttpStatus.CREATED);
    }
}

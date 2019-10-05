package com.product.manager.api;

import com.product.manager.domain.entity.Package;
import com.product.manager.service.PackageReadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/packages")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8080" })
public class PackageReadApi {

    private final PackageReadService packageReadService;

    public PackageReadApi(PackageReadService packageReadService) { this.packageReadService = packageReadService; }

    @GetMapping("/{packageId}")
    ResponseEntity<?> getPackage(@PathVariable Long packageId) {
        Package readServicePackage = packageReadService.getPackage(packageId);
        return new ResponseEntity<>(readServicePackage, HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<?>> getPackages() {
        List<Package> readServicePackages = packageReadService.getPackages();
        return new ResponseEntity<>(readServicePackages, HttpStatus.OK);
    }
}

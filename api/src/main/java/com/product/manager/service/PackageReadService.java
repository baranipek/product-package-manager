package com.product.manager.service;

import com.product.manager.domain.entity.Package;
import com.product.manager.repository.PackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageReadService {
    private final PackageRepository packageRepository;

    public PackageReadService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public Package getPackage(Long packageId) {
       return  packageRepository.findById(packageId).orElse(null);
    }

    public List<Package> getPackages() {
        return  packageRepository.findAll();
    }
}

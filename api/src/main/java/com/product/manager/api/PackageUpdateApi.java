package com.product.manager.api;

import com.product.manager.domain.request.PackageUpdateRequest;
import com.product.manager.service.PackageUpdateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/packages")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8080" })
public class PackageUpdateApi {

    private final PackageUpdateService updateService;

    public PackageUpdateApi(PackageUpdateService updateService) { this.updateService = updateService; }

    @PutMapping("/{packageId}")
    ResponseEntity<?> update(@PathVariable  Long packageId, @RequestBody PackageUpdateRequest request) {
        request.setId(packageId);
        updateService.update(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{packageId}")
    ResponseEntity<?> delete(@PathVariable  Long packageId) {
        updateService.delete(packageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

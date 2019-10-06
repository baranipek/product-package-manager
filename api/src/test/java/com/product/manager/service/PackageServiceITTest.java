package com.product.manager.service;

import com.product.manager.ProductPackageManagerApplication;
import com.product.manager.domain.entity.Package;
import com.product.manager.domain.request.PackageCreateRequest;
import com.product.manager.domain.request.PackageUpdateRequest;
import com.product.manager.enumeration.ExchangeRate;
import feign.FeignException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductPackageManagerApplication.class)
public class PackageServiceITTest {

    @Autowired
    private PackageCreateService createService;

    @Autowired
    private PackageReadService readService;

    @Autowired
    private PackageUpdateService updateService;

    @Test(expected = IllegalArgumentException.class)
    public void givenPackageCreteRequestNameIsNull_WhenSavePackage_ThrowsException() {
        PackageCreateRequest request = new PackageCreateRequest();
        request.setDescription("desc");

        createService.save(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenPackageCreteRequestDescriptionIsNull_WhenSavePackage_ThrowsException() {
        PackageCreateRequest request = new PackageCreateRequest();
        request.setName("name");

        createService.save(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenPackageCreteRequestDescriptionAndNameIsNull_WhenSavePackage_ThrowsException() {
        PackageCreateRequest request = new PackageCreateRequest();

        createService.save(request);
    }

    @Test
    public void givenPackageCreteRequestWithoutExchangeRate_WhenSavePackage_DefaultUsdIsSetAsExchangeRate() {
        PackageCreateRequest request = new PackageCreateRequest();
        request.setName("name");
        request.setDescription("desc");
        Set<String> products = new HashSet<>();
        products.add("VqKb4tyj9V6i");
        request.setProducts(products);

        createService.save(request);
        assertEquals(request.getExchangeRate(), ExchangeRate.USD);
    }

    @Test
    public void givenPackageCreteRequest_WhenSavePackage_PackageIsSavedAsExpectedIdIsReturned() {
        PackageCreateRequest request = new PackageCreateRequest();
        request.setName("name");
        request.setDescription("desc");
        Set<String> products = new HashSet<>();
        products.add("VqKb4tyj9V6i");
        products.add("DXSQpv6XVeJm");
        request.setProducts(products);

        Package expected= createService.save(request);

        assertEquals(request.getExchangeRate(), ExchangeRate.USD);
        assertEquals(expected.getName(),"name");
        assertEquals(expected.getDescription(),"desc");
        assertEquals(expected.getProducts().size(),2);
        assertEquals(expected.getTotalPrice(),BigDecimal.valueOf(2148));

    }

    @Test(expected = FeignException.class)
    public void givenPackageCreteRequestWrongProductId_WhenSavePackage_ThrowException() {
        PackageCreateRequest request = new PackageCreateRequest();
        request.setName("name");
        request.setDescription("desc");
        Set<String> products = new HashSet<>();
        products.add("wrong");
        products.add("DXSQpv6XVeJm");
        request.setProducts(products);

        Package expected= createService.save(request);

        assertEquals(request.getExchangeRate(), ExchangeRate.USD);
        assertEquals(expected.getName(),"name");
        assertEquals(expected.getDescription(),"desc");
        assertEquals(expected.getProducts().size(),2);
        assertEquals(expected.getTotalPrice(),BigDecimal.valueOf(2148));

    }


    @Test(expected = IllegalArgumentException.class)
    public void givenPackageCreteRequestWithoutProductId_WhenSavePackage_ThrowException() {
        PackageCreateRequest request = new PackageCreateRequest();
        request.setName("name");
        request.setDescription("desc");

        Package expected= createService.save(request);

        assertEquals(request.getExchangeRate(), ExchangeRate.USD);
        assertEquals(expected.getName(),"name");
        assertEquals(expected.getDescription(),"desc");
        assertEquals(expected.getProducts().size(),2);
        assertEquals(expected.getTotalPrice(),BigDecimal.valueOf(2148));

    }

    @Test
    public void givenPackageCreteRequestWithGBP_WhenSavePackage_PackageIsSavedAsExpectedPackageIsReturned() {
        PackageCreateRequest request = new PackageCreateRequest();
        request.setName("name");
        request.setDescription("desc");
        Set<String> products = new HashSet<>();
        products.add("VqKb4tyj9V6i");
        products.add("DXSQpv6XVeJm");
        request.setProducts(products);
        request.setExchangeRate(ExchangeRate.GBP);

        Package expected = createService.save(request);

        assertEquals(expected.getExchangeRate(), ExchangeRate.GBP);
        assertEquals(expected.getName(),"name");
        assertEquals(expected.getDescription(),"desc");
        assertEquals(expected.getProducts().size(),2);

    }

    @Test
    public void givenPackageUpdateRequest_WhenUpdatePackage_PackageIsUpdated() {
        PackageCreateRequest request = new PackageCreateRequest();
        request.setName("name");
        request.setDescription("desc");
        Set<String> products = new HashSet<>();
        products.add("VqKb4tyj9V6i");
        products.add("DXSQpv6XVeJm");

        request.setProducts(products);
        request.setExchangeRate(ExchangeRate.GBP);
        Package packageSaved= createService.save(request);

        PackageUpdateRequest updateRequest = new PackageUpdateRequest();
        updateRequest.setName("changedName");
        updateRequest.setDescription("changedDesc");

        Set<String> productIds = new HashSet<>();
        productIds.add("VqKb4tyj9V6i");
        productIds.add("PKM5pGAh9yGm");
        productIds.add("500R5EHvNlNB");
        updateRequest.setProducts(productIds);
        updateRequest.setExchangeRate(ExchangeRate.USD);
        updateRequest.setId(packageSaved.getId());

        updateService.update(updateRequest);
        Package expected = readService.getPackage(packageSaved.getId());

        assertEquals(expected.getExchangeRate(), ExchangeRate.USD);
        assertEquals(expected.getName(),"changedName");
        assertEquals(expected.getDescription(),"changedDesc");
        assertEquals(expected.getProducts().size(),3);
    }

}
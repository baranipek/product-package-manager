package com.product.manager.domain.entity;

import com.product.manager.ProductPackageManagerApplication;
import com.product.manager.repository.PackageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductPackageManagerApplication.class)
public class RepositoryITTest {

    @Autowired
    private  PackageRepository packageRepository;

    @Test
    public void givenPackageCreateRequest_whenCreatePackage_thenPackageIsCreated() {
       Package givenPackage = new Package();
       givenPackage.setDescription("desc");
       givenPackage.setTotalPrice(BigDecimal.valueOf(13));
       givenPackage.setProducts(null);
       givenPackage.setName("name");

       packageRepository.save(givenPackage);
       Optional<Package> expected = packageRepository.findById(1L);

       assertTrue(expected.isPresent());
       assertEquals(expected.get().getId(),Long.valueOf(1));
       assertEquals(expected.get().getName(),"name");
    }

}

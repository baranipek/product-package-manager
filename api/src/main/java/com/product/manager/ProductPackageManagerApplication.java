package com.product.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class ProductPackageManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProductPackageManagerApplication.class, args);
	}
}

package com.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

	Product findByName(String name);
	
	Product findByCustomerID(String customerID);

}

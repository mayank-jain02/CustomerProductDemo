package com.spring.service;

import com.spring.model.Product;
import com.spring.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl {

    @Autowired
    private ProductRepo repository;

    public List<Product> findAllProducts() {
        return repository.findAll();
    }

    public Product findById(long id) {
        return repository.getOne(id);
    }

    public Product findByName(String name) {
        return repository.findByName(name);
    }

    public Product findByCustomerID(String cid) {
        return repository.findByCustomerID(cid);
    }

    public void saveProduct(Product product) {
        repository.save(product);
    }

    public void updateProduct(Product product) {
        saveProduct(product);
    }

    public void deleteProductById(long id) {
        repository.deleteById(id);
    }

    public boolean doesExist(Product product) {
        return findByName(product.getName()) != null;
    }

    public void deleteAllProducts() {
        repository.deleteAll();
    }

}

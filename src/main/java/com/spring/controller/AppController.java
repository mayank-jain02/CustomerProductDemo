package com.spring.controller;

import com.spring.model.Product;
import com.spring.service.ProductServiceImpl;
import com.spring.util.Error;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {

    private static final Logger LOGGER = LogManager.getLogger(AppController.class);

    private ProductServiceImpl productService;

    @Autowired
    public AppController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    // Retrieves All Products
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> listAllProducts() {
        List<Product> products = productService.findAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Retrieve Single Product
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@PathVariable("id") long id) {
        try {
            Product product = productService.findById(id);
            if (product == null) {
                return new ResponseEntity<>(new Error("Product with id " + id + " not found"), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception thrown by API: ", e);
        }

        return new ResponseEntity<>(new Error("Error Thrown By API"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Retrieve Product by CustomerID
    @RequestMapping(value = "/products/{customerID}", method = RequestMethod.GET)
    public ResponseEntity<?> getProductByCustomerID(@PathVariable("customerID") String customerID) {
        Product product = productService.findByCustomerID(customerID);
        if (product == null) {
            return new ResponseEntity<>(new Error("Product with customer id " + customerID + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Add Product
    @RequestMapping(value = "/products/", method = RequestMethod.POST)
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        if (productService.doesExist(product)) {
            return new ResponseEntity<>(
                    new Error("Unable to create. A Product with name " + product.getName() + " already exist."),
                    HttpStatus.CONFLICT);
        }
        productService.saveProduct(product);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // Update a Product
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        Product currentProduct = productService.findById(id);

        if (currentProduct == null) {
            return new ResponseEntity<>(new Error("Unable to upate. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentProduct.setName(product.getName());
        currentProduct.setPrice(product.getPrice());

        productService.updateProduct(currentProduct);
        return new ResponseEntity<>(currentProduct, HttpStatus.OK);
    }

    // Delete a Product
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return new ResponseEntity<>(new Error("Unable to delete. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        productService.deleteProductById(id);
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }

    // Delete All Products
    @RequestMapping(value = "/product/", method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteAllProducts() {
        productService.deleteAllProducts();
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }

}
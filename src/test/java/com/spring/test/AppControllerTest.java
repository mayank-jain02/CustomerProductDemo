package com.spring.test;

import com.spring.controller.AppController;
import com.spring.model.Product;
import com.spring.service.ProductServiceImpl;
import com.spring.util.Error;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AppControllerTest {

    @InjectMocks
    private AppController controller;

    @Mock
    private ProductServiceImpl productServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new AppController(productServiceImpl);
    }

    @Test
    public void testListAllProducts_whenDataAvailable() {
        Product product = new Product();
        product.setId(1);
        product.setName("a");

        List<Product> products = new ArrayList<>();
        products.add(product);

        Mockito.when(productServiceImpl.findAllProducts()).thenReturn(products);

        ResponseEntity<List<Product>> result = controller.listAllProducts();

        verify(productServiceImpl, times(1)).findAllProducts();
        Assert.assertTrue(result != null);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assert.assertTrue(result.getBody() != null);
        Assert.assertTrue(result.getBody().size() == 1);
    }

    @Test
    public void testListAllProducts_whenDataNotAvailable() {
        Mockito.when(productServiceImpl.findAllProducts()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Product>> result = controller.listAllProducts();

        verify(productServiceImpl, times(1)).findAllProducts();

        Assert.assertTrue(result != null);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertTrue(result.getBody() != null);
        Assert.assertTrue(result.getBody().size() == 0);
    }

    @Test
    public void testGetProduct_whenProductAvailable() {
        Product product = new Product();
        product.setId(1);
        product.setName("a");

        Mockito.when(productServiceImpl.findById(1)).thenReturn(product);

        ResponseEntity<?> result = controller.getProduct(1);
        verify(productServiceImpl, times(1)).findById(1);

        Assert.assertTrue(result != null);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assert.assertTrue(result.getBody() != null);
        Assert.assertTrue(result.getBody().equals(product));
    }

    @Test
    public void testGetProduct_whenProductNotAvailable() {
        Mockito.when(productServiceImpl.findById(anyLong())).thenReturn(null);

        ResponseEntity<?> result = controller.getProduct(1);
        verify(productServiceImpl, times(1)).findById(1);

        Assert.assertTrue(result != null);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertTrue(result.getBody() != null);

        Error errorResponse = (Error) result.getBody();

        Assert.assertEquals("Product with id 1 not found", errorResponse.getErrorMessage());
    }


    @Test
    public void testGetProduct_whenServiceThrowsException() {
        Mockito.doThrow(NullPointerException.class).when(productServiceImpl).findById(anyLong());

        ResponseEntity<?> result = controller.getProduct(1);
        verify(productServiceImpl, times(1)).findById(1);

        Assert.assertTrue(result != null);
        Assert.assertEquals(result.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        Assert.assertTrue(result.getBody() != null);

        Error errorResponse = (Error) result.getBody();

        Assert.assertEquals("Error Thrown By API", errorResponse.getErrorMessage());
    }

}

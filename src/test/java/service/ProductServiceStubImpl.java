package service;

import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl();
    }

    @Test
    void testGetProductExists() {
        Product product = productService.getProduct("Carpet");

        assertNotNull(product);
        assertEquals("Carpet", product.getProductType());
        assertEquals(new BigDecimal("2.25"), product.getCostPerSquareFoot());
        assertEquals(new BigDecimal("2.10"), product.getLaborCostPerSquareFoot());
    }

    @Test
    void testNoProductsFails() {
        try {
            productService.getProduct("Nada");
            fail("Expected IllegalArgumentException to be thrown.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Product not found: Nada", ex.getMessage());
        }
    }

}
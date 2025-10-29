package com.sg.flooringmastery.service;

import com.sg.flooringmastery.model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class ProductServiceImpl implements ProductService{


    private static final String PRODUCTS_FILE = "Data/Products.txt";
    private static final String DELIMITER = ",";
    private final Map<String, Product> productMap = new HashMap<>();

    public ProductServiceImpl() {
        loadProducts();
    }

    private void loadProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            String line = reader.readLine();
            if (line != null && line.trim().startsWith("ProductType")) {
                line = reader.readLine();
            }

            while (line != null) {
                String[] parts = line.split(DELIMITER);
                if (parts.length >= 3) {
                    String productType = parts[0].trim();
                    BigDecimal costPerSqFt = new BigDecimal(parts[1].trim());
                    BigDecimal laborCostPerSqFt = new BigDecimal(parts[2].trim());

                    Product product = new Product(productType, costPerSqFt, laborCostPerSqFt);
                    productMap.put(productType, product);
                }
                line = reader.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not load products from file: " + PRODUCTS_FILE, e);
        }
    }

    @Override
    public Product getProduct(String productType) {
        Product product = productMap.get(productType);
        if (product == null) {
            throw new IllegalArgumentException("Product not found: " + productType);
        }
        return product;
    }

    public Map<String, Product> getAllProducts() {
        return productMap;
    }


}

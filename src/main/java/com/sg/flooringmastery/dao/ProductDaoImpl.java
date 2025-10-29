package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class ProductDaoImpl implements ProductDao {

    private static final String PRODUCT_FILE = "Products.txt";
    private static final String DELIMITER = ",";
    private Map<String, Product> products = new HashMap<>();

    public ProductDaoImpl() {
        loadProducts();
    }

    @Override
    public Product getProduct(String productType) {
        return products.get(productType);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    private void loadProducts() {
        products.clear();
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not load product data.", e);
        }

        // skip header
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            String[] tokens = currentLine.split(DELIMITER);

            String type = tokens[0];
            BigDecimal cost = new BigDecimal(tokens[1]);
            BigDecimal laborCost = new BigDecimal(tokens[2]);

            Product product = new Product(type, cost, laborCost);
            products.put(type, product);
        }

        scanner.close();
    }
}


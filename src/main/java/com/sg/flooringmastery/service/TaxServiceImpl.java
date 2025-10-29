package com.sg.flooringmastery.service;

import com.sg.flooringmastery.model.Tax;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


@Component
public class TaxServiceImpl implements TaxService{

    private static final String TAXES_FILE = "Data/Taxes.txt"; // your file path
    private static final String DELIMITER = ",";
    private final Map<String, Tax> taxMap = new HashMap<>();

    public TaxServiceImpl() {
        loadTaxes();
    }

    private void loadTaxes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TAXES_FILE))) {
            String line = reader.readLine(); // skip header if it exists
            if (line != null && line.trim().startsWith("State")) {
                line = reader.readLine();
            }

            while (line != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(DELIMITER);
                    if (parts.length >= 2) {
                        try {
                            String state = parts[0].trim(); // Gets the first column (State)
                            BigDecimal taxRate = new BigDecimal(parts[parts.length - 1].trim()); // Gets the final column (tax rate)
                            Tax tax = new Tax(state, taxRate);
                            taxMap.put(state, tax);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid number in line: " + line);
                        }
                    } else {
                        System.err.println("Invalid format in line: " + line);
                    }
                }
                line = reader.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Couldn't load taxes from file: " + TAXES_FILE, e);
        }
    }

    @Override
    public Tax getTax(String state) {
        Tax tax = taxMap.get(state);
        if (tax == null) {
            throw new IllegalArgumentException("No tax data found for state: " + state);
        }
        return tax;
    }
    //Obtain a map for easy lookups
    public Map<String, Tax> getAllTaxes() {
        return taxMap;
    }
}

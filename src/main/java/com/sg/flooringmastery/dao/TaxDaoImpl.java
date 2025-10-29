package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Tax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class TaxDaoImpl implements TaxDao{

    private final String TAX_FILE = "Taxes.txt";
    private final String DELIMITER = ",";
    private Map<String, Tax> taxes = new HashMap<>();

    @Override
    public Tax getTax(String stateAbr) {
        loadTaxes();
        return taxes.get(stateAbr);
    }

    @Override
    public List<Tax> getAllTaxes() {
        loadTaxes();
        return new ArrayList<>(taxes.values());
    }

    private void loadTaxes() {
        taxes.clear();
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not load tax data.", e);
        }

        // skip header
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            String[] tokens = currentLine.split(DELIMITER);

            String stateAbr = tokens[0];
            BigDecimal taxRate = new BigDecimal(tokens[2]); // ignoring state name for now

            Tax tax = new Tax(stateAbr, taxRate);
            taxes.put(stateAbr, tax);
        }

        scanner.close();
    }
}

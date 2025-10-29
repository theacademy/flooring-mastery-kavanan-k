package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Tax;

import java.util.List;

public interface TaxDao {
    Tax getTax(String stateAbr);
    List<Tax> getAllTaxes();
}

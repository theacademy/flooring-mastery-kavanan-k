package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;

import java.time.LocalDate;
import java.util.Map;

public interface ExportDao {
    void exportAllOrders(Map<LocalDate, Map<Integer, Order>> allOrders);
}

package com.sg.flooringmastery.service;

import com.sg.flooringmastery.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public interface OrderService {

    Order createOrder(Order order) throws FlooringMasteryValidationException;

    Order getOrder(LocalDate date, int orderNumber);

    List<Order> getOrdersForDate(LocalDate date);

    Order editOrder(LocalDate date, int orderNumber, Order updatedOrder) throws FlooringMasteryValidationException;

    Order removeOrder(LocalDate date, int orderNumber);

    Map<LocalDate, Map<Integer, Order>> getAllOrdersByDate();

    boolean isValidCustomerName(String name);

    boolean isValidState(String state);

    boolean isValidProduct(String productType);

    boolean isValidArea(BigDecimal area);


}
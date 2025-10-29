package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderDao {

    Order addOrder(Order order);

    Order getOrder(LocalDate date, int orderNumber);

    Order editOrder(LocalDate date, int orderNumber, Order updatedOrder);

    List<Order> getOrdersForDate(LocalDate date);

    Map<LocalDate, Map<Integer, Order>> getAllOrders();

    Order removeOrder(LocalDate date, int orderNumber);


}

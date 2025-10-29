package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.OrderDao;

import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.model.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final ProductService productService;
    private final TaxService taxService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, ProductService productService, TaxService taxService) {
        this.orderDao = orderDao;
        this.productService = productService;
        this.taxService = taxService;
    }

    @Override
    public Order createOrder(Order order) throws FlooringMasteryValidationException {
        validateOrderData(order);
        calculateCosts(order);
        orderDao.addOrder(order);
        return order;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) {
        return orderDao.getOrder(date, orderNumber);
    }

    @Override
    public List<Order> getOrdersForDate(LocalDate date) {
        return orderDao.getOrdersForDate(date);
    }

    @Override
    public Order editOrder(LocalDate date, int orderNumber, Order updatedOrder) throws FlooringMasteryValidationException {
        validateOrderData(updatedOrder);
        calculateCosts(updatedOrder);
        return orderDao.editOrder(date, orderNumber, updatedOrder);
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) {
        return orderDao.removeOrder(date, orderNumber);
    }

    @Override
    public Map<LocalDate, Map<Integer, Order>> getAllOrdersByDate() {
        return orderDao.getAllOrders(); // calls your existing DAO method
    }



    private void validateOrderData(Order order) throws FlooringMasteryValidationException{
        if (order.getCustomerName() == null || order.getCustomerName().trim().isEmpty()) {
            throw new FlooringMasteryValidationException("Customer name is required.");
        }
        if (order.getState() == null || taxService.getTax(order.getState()) == null) {
            throw new FlooringMasteryValidationException("Invalid state entered.");
        }
        if (order.getProductType() == null || productService.getProduct(order.getProductType()) == null) {
            throw new FlooringMasteryValidationException("Invalid product type entered.");
        }
        if (order.getArea() == null || order.getArea().compareTo(new BigDecimal("100")) < 0) {
            throw new FlooringMasteryValidationException("Area must be at least 100 sq ft.");
        }
    }

    private void calculateCosts(Order order) {
        Product product = productService.getProduct(order.getProductType());
        Tax tax = taxService.getTax(order.getState());

        BigDecimal area = order.getArea();
        BigDecimal costPerSqFt = product.getCostPerSquareFoot();
        BigDecimal laborPerSqFt = product.getLaborCostPerSquareFoot();

        BigDecimal materialCost = area.multiply(costPerSqFt);
        BigDecimal laborCost = area.multiply(laborPerSqFt);
        BigDecimal subTotal = materialCost.add(laborCost);
        BigDecimal taxAmt = subTotal.multiply(tax.getTaxRate().divide(new BigDecimal("100")));
        BigDecimal total = subTotal.add(taxAmt);

        order.setTaxRate(tax.getTaxRate());
        order.setCostPerSquareFoot(costPerSqFt);
        order.setLaborCostPerSquareFoot(laborPerSqFt);
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(taxAmt);
        order.setTotal(total);
    }
}

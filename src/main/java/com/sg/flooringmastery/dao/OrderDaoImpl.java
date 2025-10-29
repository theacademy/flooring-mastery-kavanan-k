package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Component
public class OrderDaoImpl implements OrderDao{


    private static final String ORDER_FOLDER = "Orders";
    private static final String DELIMITER = ",";
    private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("MMddyyyy");

    private final Map<LocalDate, Map<Integer, Order>> orders = new HashMap<>();

    @Override
    public Order addOrder(Order order) {
        LocalDate date = order.getOrderDate();
        loadOrdersForDate(date);
        Map<Integer, Order> ordersForDate = orders.getOrDefault(date, new HashMap<>());

        int nextOrderNumber = ordersForDate.keySet().stream()
                .max(Integer::compareTo)
                .orElse(0) + 1;
        order.setOrderNumber(nextOrderNumber);

        ordersForDate.put(order.getOrderNumber(), order);
        orders.put(date, ordersForDate);

        writeOrdersForDate(date);
        return order;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) {
        loadOrdersForDate(date);
        Map<Integer, Order> ordersForDate = orders.get(date);
        return (ordersForDate != null) ? ordersForDate.get(orderNumber) : null;
    }

    @Override
    public Order editOrder(LocalDate date, int orderNumber, Order updatedOrder) {
        loadOrdersForDate(date);

        Map<Integer, Order> ordersForDate = orders.get(date);
        if (ordersForDate != null && ordersForDate.containsKey(orderNumber)) {
            ordersForDate.put(orderNumber, updatedOrder);
            writeOrdersForDate(date);
            return updatedOrder;
        }
        return null;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) {
        loadOrdersForDate(date);
        Map<Integer, Order> ordersForDate = orders.get(date);

        if (ordersForDate != null) {
            Order removed = ordersForDate.remove(orderNumber);
            writeOrdersForDate(date);
            return removed;
        }
        return null;
    }

    @Override
    public List<Order> getOrdersForDate(LocalDate date) {
        loadOrdersForDate(date);
        Map<Integer, Order> ordersForDate = orders.get(date);
        return (ordersForDate != null) ? new ArrayList<>(ordersForDate.values()) : new ArrayList<>();
    }

    @Override
    public Map<LocalDate, Map<Integer, Order>> getAllOrders() {
        File folder = new File(ORDER_FOLDER);
        if (!folder.exists()) folder.mkdir();

        File[] files = folder.listFiles((dir, name) -> name.startsWith("Orders_") && name.endsWith(".txt"));
        if (files != null) {
            for (File file : files) {
                try {
                    String datePart = file.getName().substring(7, 15); // Orders_MMddyyyy.txt
                    LocalDate date = LocalDate.parse(datePart, FILE_DATE_FORMAT);
                    loadOrdersForDate(date);
                } catch (Exception e) {
                    System.err.println("Skipping invalid file: " + file.getName());
                }
            }
        }
        return orders;
    }

    // ===================== Helper Methods =====================

    private void loadOrdersForDate(LocalDate date) {
        // Avoid re-loading the same date multiple times
        if (orders.containsKey(date)) return;

        Map<Integer, Order> ordersForDate = new HashMap<>();

        File folder = new File(ORDER_FOLDER);
        if (!folder.exists()) folder.mkdir();

        File file = new File(folder, "Orders_" + date.format(FILE_DATE_FORMAT) + ".txt");
        if (!file.exists()) {
            orders.put(date, ordersForDate); // Empty date, but still recorded
            return;
        }

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // Skip header line

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(DELIMITER);

                int orderNumber = Integer.parseInt(tokens[0]);
                String customerName = tokens[1];
                String state = tokens[2];
                BigDecimal taxRate = new BigDecimal(tokens[3]);
                String productType = tokens[4];
                BigDecimal area = new BigDecimal(tokens[5]);
                BigDecimal costPerSquareFoot = new BigDecimal(tokens[6]);
                BigDecimal laborCostPerSquareFoot = new BigDecimal(tokens[7]);
                BigDecimal materialCost = new BigDecimal(tokens[8]);
                BigDecimal laborCost = new BigDecimal(tokens[9]);
                BigDecimal tax = new BigDecimal(tokens[10]);
                BigDecimal total = new BigDecimal(tokens[11]);

                Order order = new Order(orderNumber);
                order.setCustomerName(customerName);
                order.setState(state);
                order.setTaxRate(taxRate);
                order.setProductType(productType);
                order.setArea(area);
                order.setCostPerSquareFoot(costPerSquareFoot);
                order.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
                order.setMaterialCost(materialCost);
                order.setLaborCost(laborCost);
                order.setTax(tax);
                order.setTotal(total);
                order.setOrderDate(date);

                ordersForDate.put(orderNumber, order);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load orders for date: " + date, e);
        }

        orders.put(date, ordersForDate);
    }

    private void writeOrdersForDate(LocalDate date) {
        Map<Integer, Order> ordersForDate = orders.get(date);
        if (ordersForDate == null) return;

        File folder = new File(ORDER_FOLDER);
        if (!folder.exists()) folder.mkdir();

        File file = new File(folder, "Orders_" + date.format(FILE_DATE_FORMAT) + ".txt");

        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,"
                    + "CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

            for (Order order : ordersForDate.values()) {
                String line = order.getOrderNumber() + DELIMITER
                        + order.getCustomerName() + DELIMITER
                        + order.getState() + DELIMITER
                        + order.getTaxRate() + DELIMITER
                        + order.getProductType() + DELIMITER
                        + order.getArea() + DELIMITER
                        + order.getCostPerSquareFoot() + DELIMITER
                        + order.getLaborCostPerSquareFoot() + DELIMITER
                        + order.getMaterialCost() + DELIMITER
                        + order.getLaborCost() + DELIMITER
                        + order.getTax() + DELIMITER
                        + order.getTotal();
                out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not write orders for date: " + date, e);
        }
    }




}

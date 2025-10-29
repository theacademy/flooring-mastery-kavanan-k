package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

//Handles the export all feature.
@Component
public class ExportDaoImpl implements ExportDao {

    private static final String EXPORT_FILE = "Backup/Backup.txt";
    private static final String DELIMITER = ",";

    @Override
    public void exportAllOrders(Map<LocalDate, Map<Integer, Order>> allOrders) {
        try (PrintWriter out = new PrintWriter(new FileWriter(EXPORT_FILE))) {
            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area," +
                    "CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            for (LocalDate date : allOrders.keySet()) {
                Map<Integer, Order> ordersForDate = allOrders.get(date);
                for (Order order : ordersForDate.values()) {
                    String line = order.getOrderNumber() + DELIMITER +
                            order.getCustomerName() + DELIMITER +
                            order.getState() + DELIMITER +
                            order.getTaxRate() + DELIMITER +
                            order.getProductType() + DELIMITER +
                            order.getArea() + DELIMITER +
                            order.getCostPerSquareFoot() + DELIMITER +
                            order.getLaborCostPerSquareFoot() + DELIMITER +
                            order.getMaterialCost() + DELIMITER +
                            order.getLaborCost() + DELIMITER +
                            order.getTax() + DELIMITER +
                            order.getTotal() + DELIMITER +
                            formatter.format(order.getOrderDate());
                    out.println(line);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not export orders.", e);
        }
    }
}

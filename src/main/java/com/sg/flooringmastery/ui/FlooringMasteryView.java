package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.model.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class FlooringMasteryView {


    private UserIO io = new UserIOConsoleImpl();

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Flooring Program");
        io.print("1. Display orders");
        io.print("2. Add a new order");
        io.print("3. Edit an order");
        io.print("4. Remove an order");
        io.print("5. Export all data");
        io.print("6. Quit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public LocalDate getOrderDate() {
        return io.readLocalDate("Enter order date (MMDDYYYY): ");
    }

    public int getOrderNumber() {
        return io.readInt("Enter order number: ");
    }

    public Order getNewOrderInfo() {
        String name = io.readString("Enter customer name: ");
        String state = io.readString("Enter state: ");
        String product = io.readString("Enter product type: ");
        BigDecimal area = io.readBigDecimal("Enter area in sq ft: ");

        Order order = new Order();
        order.setCustomerName(name);
        order.setState(state);
        order.setProductType(product);
        order.setArea(area);
        order.setOrderDate(LocalDate.now()); // could also be user input
        return order;
    }

    public Order getEditedOrder(Order existingOrder) {
        String name = io.readString("Enter customer name (" + existingOrder.getCustomerName() + "): ");
        String state = io.readString("Enter state (" + existingOrder.getState() + "): ");
        String product = io.readString("Enter product type (" + existingOrder.getProductType() + "): ");
        String areaInput = io.readString("Enter area (" + existingOrder.getArea() + "): ");

        Order updatedOrder = new Order(existingOrder.getOrderNumber());
        updatedOrder.setOrderDate(existingOrder.getOrderDate());
        updatedOrder.setCustomerName(name.isBlank() ? existingOrder.getCustomerName() : name);
        updatedOrder.setState(state.isBlank() ? existingOrder.getState() : state);
        updatedOrder.setProductType(product.isBlank() ? existingOrder.getProductType() : product);
        updatedOrder.setArea(areaInput.isBlank() ? existingOrder.getArea() : new BigDecimal(areaInput));
        return updatedOrder;
    }

    public void displayOrderList(List<Order> orders) {
        for (Order o : orders) {
            io.print(o.getOrderNumber() + ": " + o.getCustomerName() + " | " + o.getProductType() + " | $" + o.getTotal());
        }
        io.readString("Press Enter to continue...");
    }

    public void displayMessage(String message) {
        io.print(message);
    }

    public void displayExportSuccessMessage() {
        System.out.println("All orders have been exported successfully!");
    }

    public void displayExportErrorMessage(String msg) {
        System.out.println("Error exporting orders: " + msg);
    }

}

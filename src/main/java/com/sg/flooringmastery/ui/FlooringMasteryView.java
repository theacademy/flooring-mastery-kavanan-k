package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.Product;
import com.sg.flooringmastery.service.OrderService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class FlooringMasteryView {


    private UserIO io = new UserIOConsoleImpl();
    private OrderService service;


    public FlooringMasteryView(UserIO io, OrderService service) {
        this.io = io;
        this.service = service;
    }

    //Generate the menu

    public int printMenuAndGetSelection() {
        io.print("*************************************************************************");
        io.print("Floor Time.");
        io.print("1. Display orders");
        io.print("2. Add a new order");
        io.print("3. Edit an order");
        io.print("4. Remove an order");
        io.print("5. Export all data");
        io.print("6. Quit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public LocalDate getOrderDate() {
        return io.readLocalDate("Enter the order date with no spaces (MMDDYYYY): ");
    }

    public int getOrderNumber() {
        return io.readInt("Enter the order number: ");
    }

    public Order getNewOrderInfo() {

        //Validate each line the user inputs
        String name;
        do {
            name = io.readString("Enter customer name: ").trim();
            if (!service.isValidCustomerName(name)) {
                io.print("Customer name cannot be empty, and must only contain letters from a-z, numbers from 0-9, periods and commas.");
            }
        } while (!service.isValidCustomerName(name));

        String state;
        do {
            state = io.readString("Enter state abbreviation (eg. TX, WA, KY, CA): ").trim();
            if (!service.isValidState(state)) {
                io.print("Invalid state. Please enter a valid one (eg. TX, WA, KY, CA)");
            }
        } while (!service.isValidState(state));

        String product;
        do {
            product = io.readString("Please choose a product type. Available options are:  \nWood, costing 5.15 per sqft and 4.75 for installation per sqft. \nTile, costing 3.50 per sqft and 4.15 for installation per sqft. \nLaminate, costing 1.75 per sqft and 2.10 for installation per sqft. \nCarpet, costing 2.25 per sqft and 2.10 for installation per sqft. ").trim();
            if (!service.isValidProduct(product)) {
                io.print("Invalid product type.\n------------------------------------------------");
            }
        } while (!service.isValidProduct(product));

        BigDecimal area;
        do {
            area = io.readBigDecimal("Enter area in sq ft (Minimum order of 100): ");
            if (!service.isValidArea(area)) {
                io.print("Invalid. Please enter a valid number, and note a minimum order of 100.");
            }
        } while (!service.isValidArea(area));


        //Add the new order
        Order order = new Order();
        order.setCustomerName(name);
        order.setState(state);
        order.setProductType(product);
        order.setArea(area);
        order.setOrderDate(LocalDate.now());

        return order;
    }

    public Order getEditedOrder(Order existingOrder) {

        String name;
        do {
            name = io.readString("Enter customer name (" + existingOrder.getCustomerName() + "): ").trim();
            if (name.isEmpty()) {
                name = existingOrder.getCustomerName();
            }
            if (!service.isValidCustomerName(name)) {
                io.print("Customer name cannot be empty, and must only contain letters from a-z, numbers from 0-9, periods and commas.");
            }
        } while (!service.isValidCustomerName(name));

        String state;
        do {
            state = io.readString("Enter state abbreviation (eg. TX, WA, KY, CA): (" + existingOrder.getState() + "): ").trim();
            if (state.isEmpty()) {
                state = existingOrder.getState();
            }
            if (!service.isValidState(state)) {
                io.print("Invalid state. Please enter a valid one (eg. TX, WA, KY, CA)");
            }
        } while (!service.isValidState(state));

        String product;
        do {
            product = io.readString("Please choose a product type. Available options are:  \nWood, costing 5.15 per sqft and 4.75 for installation per sqft. \nTile, costing 3.50 per sqft and 4.15 for installation per sqft. \nLaminate, costing 1.75 per sqft and 2.10 for installation per sqft. \nCarpet, costing 2.25 per sqft and 2.10 for installation per sqft. (" + existingOrder.getProductType() + "): ").trim();
            if (product.isEmpty()) {
                product = existingOrder.getProductType();
            }
            if (!service.isValidProduct(product)) {
                io.print("Invalid product type.\n-----------------------------------------");
            }
        } while (!service.isValidProduct(product));

        BigDecimal area;
        do {
            String areaInput = io.readString("Enter area in sq ft and note a minimum of 100 (" + existingOrder.getArea() + "): ").trim();
            if (areaInput.isEmpty()) {
                area = existingOrder.getArea();
            } else {
                try {
                    area = new BigDecimal(areaInput);
                    if (!service.isValidArea(area)) {
                        io.print("Area must be at least 100 sq ft.");
                        area = null;
                    }
                } catch (NumberFormatException e) {
                    io.print("Invalid number. Please enter a valid number, and note that the minimum is 100.");
                    area = null;
                }
            }
        } while (area == null);


        Order updatedOrder = new Order();
        updatedOrder.setCustomerName(name);
        updatedOrder.setState(state);
        updatedOrder.setProductType(product);
        updatedOrder.setArea(area);
        updatedOrder.setOrderDate(existingOrder.getOrderDate());
        updatedOrder.setOrderNumber(existingOrder.getOrderNumber());

        return updatedOrder;
    }

    public void displayOrderList(List<Order> orders) {
        for (Order o : orders) {
            io.print(o.getOrderNumber() + ": " + o.getCustomerName() + " | " + o.getProductType() + " | $" + o.getTotal().setScale(2, RoundingMode.HALF_UP));
        }
        io.readString("Press Enter to continue...");
    }

    public void displayMessage(String message) {
        io.print(message);
    }



    //Unused
    public void displayExportSuccessMessage() {
        System.out.println("All orders have been exported successfully!");
    }

    public void displayExportErrorMessage(String msg) {
        System.out.println("There was an error exporting orders: " + msg);
    }

}

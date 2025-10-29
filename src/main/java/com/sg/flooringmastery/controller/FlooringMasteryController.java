package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.ExportDao;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.service.FlooringMasteryValidationException;
import com.sg.flooringmastery.service.OrderService;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import com.sg.flooringmastery.ui.UserIO;
import com.sg.flooringmastery.ui.UserIOConsoleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;


@Component
public class FlooringMasteryController {

    private FlooringMasteryView view;
    private OrderService service;
    private UserIO io = new UserIOConsoleImpl();
    private ExportDao exportDao;

    @Autowired
    public FlooringMasteryController(FlooringMasteryView view, OrderService service, ExportDao exportDao){
        this.view = view;
        this.service = service;
        this.exportDao = exportDao;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    displayOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    exportAllOrders();
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    io.print("invalid");
            }

        }
        io.print("GOOD BYE");
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void displayOrders() {
        LocalDate date = view.getOrderDate();
        List<Order> orders = service.getOrdersForDate(date);
        view.displayOrderList(orders);
    }

    private void addOrder() {
        try {
            Order newOrder = view.getNewOrderInfo();
            service.createOrder(newOrder);
            view.displayMessage("Order successfully added.");
        } catch (FlooringMasteryValidationException e) {
            view.displayMessage(e.getMessage());
        }
    }

    private void editOrder() {
        LocalDate date = view.getOrderDate();
        int orderNumber = view.getOrderNumber();
        Order existingOrder = service.getOrder(date, orderNumber);

        if (existingOrder == null) {
            view.displayMessage("No order found for that date and number.");
            return;
        }

        Order updatedOrder = view.getEditedOrder(existingOrder);
        try {
            service.editOrder(date, orderNumber, updatedOrder);
            view.displayMessage("Order successfully updated.");
        } catch (FlooringMasteryValidationException e) {
            view.displayMessage(e.getMessage());
        }
    }

    private void removeOrder() {
        LocalDate date = view.getOrderDate();
        int orderNumber = view.getOrderNumber();

        Order removed = service.removeOrder(date, orderNumber);
        if (removed != null) {
            view.displayMessage("Order removed.");
        } else {
            view.displayMessage("No order found.");
        }
    }

    private void exportAllOrders() {
        try {
            Map<LocalDate, Map<Integer, Order>> allOrders = service.getAllOrdersByDate();
            exportDao.exportAllOrders(allOrders);
            view.displayMessage("All orders have been exported successfully!");
        } catch (Exception e) {
            view.displayMessage("Failed to export orders: " + e.getMessage());
        }
    }

}

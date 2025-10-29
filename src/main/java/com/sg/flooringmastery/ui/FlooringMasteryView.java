package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FlooringMasteryView {


    private UserIO io = new UserIOConsoleImpl();

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



    public Order getOrderInfo(){
        String customerName = io.readString("What is the customer's name?");
        String state = io.readString("In which state is the sale?");
        String productType = io.readString("Please enter the product type:");
        String areaString = io.readString("Please enter the area you're looking to cover in sqft:");
        BigDecimal area = new BigDecimal(areaString);
        LocalDate time = LocalDate.now();
        Student currentStudent = new Student(studentId);
        currentStudent.setFirstName(firstName);
        currentStudent.setLastName(lastName);
        currentStudent.setCohort(cohort);
        return currentStudent;
    }
}

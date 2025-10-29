package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import com.sg.flooringmastery.dao.*;
import com.sg.flooringmastery.service.*;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import com.sg.flooringmastery.ui.UserIO;
import com.sg.flooringmastery.ui.UserIOConsoleImpl;

public class App {

    public static void main(String[] args) {


        // Step 1: Create IO handler
        UserIO io = new UserIOConsoleImpl();

        // Step 2: Create the View
        FlooringMasteryView view = new FlooringMasteryView(io);

        // Step 3: Create DAOs
        OrderDao orderDao = new OrderDaoImpl();
        ExportDao exportDao = new ExportDaoImpl();
        //ProductDao productDao = new ProductDaoImpl();
        //TaxDao taxDao = new TaxDaoImpl();


        ProductService productService = new ProductServiceImpl();
        TaxService taxService = new TaxServiceImpl();




        // Step 4: Create Service Layer
        OrderService service = new OrderServiceImpl(orderDao, productService, taxService);

        // Step 5: Create Controller
        FlooringMasteryController controller = new FlooringMasteryController(view, service, exportDao);

        // Step 6: Run the program
        controller.run();
    }


}

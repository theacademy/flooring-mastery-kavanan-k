package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import com.sg.flooringmastery.dao.*;
import com.sg.flooringmastery.service.*;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import com.sg.flooringmastery.ui.UserIO;
import com.sg.flooringmastery.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {


        ApplicationContext context = new AnnotationConfigApplicationContext("com.sg.flooringmastery");
        FlooringMasteryController controller = context.getBean(FlooringMasteryController.class);

//        UserIO io = new UserIOConsoleImpl();
//        FlooringMasteryView view = new FlooringMasteryView(io);
//        OrderDao orderDao = new OrderDaoImpl();
//        ExportDao exportDao = new ExportDaoImpl();
//        ProductService productService = new ProductServiceImpl();
//        TaxService taxService = new TaxServiceImpl();
//        OrderService service = new OrderServiceImpl(orderDao, productService, taxService);
//        FlooringMasteryController controller = new FlooringMasteryController(view, service, exportDao);
        controller.run();
    }
}

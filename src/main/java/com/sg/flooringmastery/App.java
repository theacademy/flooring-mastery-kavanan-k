package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import com.sg.flooringmastery.dao.*;
import com.sg.flooringmastery.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext("com.sg.flooringmastery");
        FlooringMasteryController controller = context.getBean(FlooringMasteryController.class);
        controller.run();
    }
}

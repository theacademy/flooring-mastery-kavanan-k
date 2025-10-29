package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.ui.UserIO;
import com.sg.flooringmastery.ui.UserIOConsoleImpl;

public class FlooringMasteryController {

    private UserIO io = new UserIOConsoleImpl();

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {
            io.print("Flooring Program");
            io.print("1. Display orders");
            io.print("2. Add a new order");
            io.print("3. Edit an order");
            io.print("4. Remove an order");
            io.print("5. Export all data");
            io.print("6. Quit");

            menuSelection = io.readInt("Please select from the"
                    + " above choices.", 1, 5);

            switch (menuSelection) {
                case 1:
                    io.print("display");
                    break;
                case 2:
                    io.print("add");
                    break;
                case 3:
                    io.print("edit");
                    break;
                case 4:
                    io.print("remove");
                    break;
                case 5:
                    io.print("export");
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

}

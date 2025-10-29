package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.ui.FlooringMasteryView;
import com.sg.flooringmastery.ui.UserIO;
import com.sg.flooringmastery.ui.UserIOConsoleImpl;

public class FlooringMasteryController {

    private FlooringMasteryView view = new FlooringMasteryView();
    private UserIO io = new UserIOConsoleImpl();

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {

            menuSelection = getMenuSelection();

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

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

}

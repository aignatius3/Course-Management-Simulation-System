package controller;

import fxapp.Main;
import model.Scratchpad;

/**
 * Created by Ashwin Ignatius on 12/2/2017.
 */
public class Controller {

    Main myApp;
    Scratchpad myScratchpad;
    Integer cycle;

    public void setMainApp(Main myApp) {
        this.myApp = myApp;
        this.myScratchpad = Main.getScratchpad();
        this.cycle = Main.getCycle();
    }
}

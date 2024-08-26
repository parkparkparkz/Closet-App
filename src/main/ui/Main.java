package ui;


import model.EventLog;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        //try {
        EventLog.getInstance().clear();
        new ClosetAppGui();
        //} catch (FileNotFoundException e) {
        //    System.out.println("Unable to run application: file not found");
       // }
    }
}

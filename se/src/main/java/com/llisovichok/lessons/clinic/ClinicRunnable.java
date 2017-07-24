package com.llisovichok.lessons.clinic;

/**
 * This class contains main method running the program
 * Created by ALEKSANDR KUDIN on 23.01.2017.
 * Version 1.0
 */
public class ClinicRunnable {
    public static void main(String[] args) {
        new GreetingDialogWindow(null);
        new RestoreDataClass();
        new ClinicProgramWindow();
    }
}

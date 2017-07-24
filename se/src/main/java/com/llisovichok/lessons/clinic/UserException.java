package com.llisovichok.lessons.clinic;

/**
 * Created by ALEKSANDR KUDIN on 21.01.2017.
 */
public class UserException extends Exception{
    public UserException(final String message){
        new ClinicMessageWindow(message,2);
    }
}

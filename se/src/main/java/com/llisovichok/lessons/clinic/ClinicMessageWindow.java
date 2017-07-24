package com.llisovichok.lessons.clinic;

import javax.swing.*;
import java.awt.*;

/**
 * Shows messages during running of the program.
 * Created by ALEKSANDR KUDIN on 14.01.2017.
 * Version 1.0
 */
public class ClinicMessageWindow extends JOptionPane{

    public ClinicMessageWindow(String text, int messageType){


        try {
            if(messageType == 1){showMessageDialog( null, text, "MESSAGE",1);}
            if(messageType == 2){showMessageDialog( null, text, "ERROR",0);}
            if(messageType == 3){showMessageDialog( null, text, "WARNING",2);}

            } catch (HeadlessException e) {
            e.printStackTrace();
            }
            catch (Exception ex){
            ex.printStackTrace();
            }

    }
}

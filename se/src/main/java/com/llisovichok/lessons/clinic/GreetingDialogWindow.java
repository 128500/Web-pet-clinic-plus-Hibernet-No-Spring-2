package com.llisovichok.lessons.clinic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class shows a greeting dialog when the program starts
 * Created by ALEKSANDR KUDIN on 15.01.2017.
 */
public class GreetingDialogWindow extends JDialog {

    GreetingDialogWindow(Frame owner){

    super(owner,"ПРИВЕТСТВИЕ",true);
    try{
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1,1));

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BorderLayout());
        dialogPanel.setBorder(BorderFactory.createLineBorder(Color.RED,8));
        dialogPanel.setBackground(Color.DARK_GRAY);

        JLabel imageLabel = new JLabel(new ImageIcon("pictures\\pets.jpg"));
        dialogPanel.add(imageLabel, BorderLayout.WEST);

        JPanel centralPanel = new JPanel();
        centralPanel.setBackground(Color.DARK_GRAY);
        centralPanel.setLayout(new GridLayout(3,1, 5,10));

        JLabel textLabel = new JLabel("Приветствуем в", JLabel.CENTER);
        JLabel textLabel2 = new JLabel("\"КЛИНИКЕ ДОМАШНИХ ЖИВОТНЫХ\"!", JLabel.CENTER);
        textLabel.setFont(new Font("Times New Roman",1,18));
        textLabel2.setFont(new Font("Times New Roman",1,18));
        textLabel.setForeground(Color.WHITE);
        textLabel2.setForeground(Color.WHITE);
        centralPanel.add(textLabel);
        centralPanel.add(textLabel2);

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        ok.setBackground(Color.DARK_GRAY);
        ok.setForeground(Color.WHITE);
        centralPanel.add(ok);

        dialogPanel.add(centralPanel, BorderLayout.CENTER);

        add(dialogPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);
    } catch(HeadlessException e){
        e.printStackTrace();
    }
    }
}


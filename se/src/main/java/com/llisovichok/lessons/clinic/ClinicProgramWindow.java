package com.llisovichok.lessons.clinic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Shows program main window.
 * Created by ALEKSANDR KUDIN on 12.01.2017.
 * Version 1.0
 */
public class ClinicProgramWindow extends JFrame {

    private final JFrame clinicFrame;
    private final JTextArea textArea;
    final ProcessingGUI pGUI;

    /**
     * Constructs main frame.
     */
    public ClinicProgramWindow() {
        pGUI = new ProcessingGUI();

        clinicFrame = new JFrame("Клиника домашних животных");
        clinicFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel westPanel = new JPanel();
        westPanel.setBackground(Color.BLACK);
        westPanel.setLayout(new GridLayout(8, 2, 0, 3));

        addTextLabel("Добавить клиента", westPanel);
        JButton addClientButton = createImageButton("pictures\\add-user.png");
        addClientButton.addActionListener(new AddClientListener());
        westPanel.add(addClientButton);

        addTextLabel("Изменить имя клиента", westPanel);
        JButton clientNameButton = createImageButton("pictures\\user-name.png");
        clientNameButton.addActionListener(new ChangeClientNameListener());
        westPanel.add(clientNameButton);

        addTextLabel("Изменить имя питомца", westPanel);
        JButton petNameButton = createImageButton("pictures\\name-tag.png");
        petNameButton.addActionListener(new ChangePetNameListener());
        westPanel.add(petNameButton);

        addTextLabel("Найти клиента", westPanel);
        JButton findClientButton = createImageButton("pictures\\client-searching.png");
        findClientButton.addActionListener(new FindClientByPetNameListener());
        westPanel.add(findClientButton);

        addTextLabel("Найти питомца", westPanel);
        JButton findPetButton = createImageButton("pictures\\pet-searching.png");
        findPetButton.addActionListener(new FindPetByClientNameListener());
        westPanel.add(findPetButton);

        addTextLabel("Удалить клиента", westPanel);
        JButton removeClientButton = createImageButton("pictures\\remove-user.png");
        removeClientButton.addActionListener(new RemoveClientListener());
        westPanel.add(removeClientButton);

        addTextLabel("Удалить питомца", westPanel);
        JButton removePetButton = createImageButton("pictures\\dog.png");
        removePetButton.addActionListener(new RemovePetListener());
        westPanel.add(removePetButton);

        addTextLabel("Отобразить всех клиентов", westPanel);
        JButton showClientsButton = createImageButton("pictures\\clients-list.png");
        showClientsButton.addActionListener(new ShowClientListener());
        westPanel.add(showClientsButton);

        westPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 8));
        clinicFrame.getContentPane().add(BorderLayout.WEST, westPanel);

        textArea = new JTextArea(10, 25);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.YELLOW);
        textArea.setFont(new Font("Times New Roman", 1, 22));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.RED, 8));

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.setBackground(Color.BLACK);
        jMenuBar.setForeground(Color.GRAY);
        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setFont(new Font("Times New Roman",Font.PLAIN, 22));
        fileMenu.setBackground(Color.BLACK);
        fileMenu.setForeground(Color.RED);
        jMenuBar.add(fileMenu);
        JMenuItem saveItem = new JMenuItem("Сохранить");
        saveItem.setBackground(Color.BLACK);
        saveItem.setForeground(Color.RED);
        saveItem.setFont(new Font("Times New Roman",Font.PLAIN, 22));
        saveItem.addActionListener(new SaveListener());
        JMenuItem exitItem = new JMenuItem("Выйти");
        exitItem.setBackground(Color.BLACK);
        exitItem.setForeground(Color.RED);
        exitItem.setFont(new Font("Times New Roman",Font.PLAIN, 22));
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SaveListener();
                System.exit(0);
            }
        });
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        clinicFrame.addWindowListener(new ClosingWindowListener());

        clinicFrame.getContentPane().add(BorderLayout.NORTH, jMenuBar);
        clinicFrame.getContentPane().add(BorderLayout.CENTER, scrollPane);
        clinicFrame.setResizable(false);
        clinicFrame.pack();
        clinicFrame.setVisible(true);

    }

    /**
     * AddClientButton inner listener class. Shows a new
     * window where you can create a client and adds it to the list.
     */
    class AddClientListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            final JDialog addClientDialog = new JDialog( clinicFrame, "Ввод данных о клиенте", true);
            try {
                addClientDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(4, 2, 5, 10));
                dialogPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                dialogPanel.setBackground(Color.DARK_GRAY);

                JLabel addClientImageLabel = new JLabel(new ImageIcon("pictures\\add-user.png"));
                JPanel imagePanel = new JPanel();
                imagePanel.setBackground(Color.DARK_GRAY);
                imagePanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                imagePanel.add(addClientImageLabel);

                JLabel clientNameLabel = new JLabel("Введите имя клиента", JLabel.RIGHT);
                JLabel petNameLabel = new JLabel("Введите имя питоца", JLabel.RIGHT);
                JLabel petKindLabel = new JLabel("Выбирете вид питоца", JLabel.RIGHT);
                clientNameLabel.setForeground(Color.WHITE);
                petNameLabel.setForeground(Color.WHITE);
                petKindLabel.setForeground(Color.WHITE);

                final JTextField clientNameField = new JTextField(20);
                clientNameField.setBackground(Color.BLACK);
                clientNameField.setForeground(Color.YELLOW);
                clientNameField.setFont(new Font("Times New Roman", 1, 15));
                clientNameField.setCaretColor(Color.WHITE);

                final JTextField petNameField = new JTextField(20);
                petNameField.setBackground(Color.BLACK);
                petNameField.setForeground(Color.YELLOW);
                petNameField.setFont(new Font("Times New Roman", 1, 15));
                petNameField.setCaretColor(Color.WHITE);

                final Choice petChoice;
                petChoice = new Choice();
                petChoice.add("собака");
                petChoice.add("кот");
                petChoice.add("хомяк");
                petChoice.setBackground(Color.BLACK);
                petChoice.setForeground(Color.YELLOW);
                petChoice.setFont(new Font("Times New Roman", 1, 15));

                dialogPanel.add(clientNameLabel);
                dialogPanel.add(clientNameField);
                dialogPanel.add(petNameLabel);
                dialogPanel.add(petNameField);
                dialogPanel.add(petKindLabel);
                dialogPanel.add(petChoice);

                JButton ok = new JButton("OK");
                ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String clientName = clientNameField.getText();
                        String petName = petNameField.getText();
                        String petKind = petChoice.getSelectedItem();
                        if (clientName.equals("") || petName.equals("")) {
                            new ClinicMessageWindow("Имя клиента(питомца) должо содержать хотябы 1 символ!", 3);
                            return;
                        } else try {
                            pGUI.addClient(clientName, petName, petKind);
                        } catch (UserException e1) {
                            e1.printStackTrace();
                            return;
                        }
                        addClientDialog.setVisible(false);

                    }
                });
                ok.setBackground(Color.DARK_GRAY);
                ok.setForeground(Color.WHITE);
                dialogPanel.add(ok);

                JButton cancel = new JButton("Отмена");
                cancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addClientDialog.setVisible(false);
                    }
                });
                cancel.setBackground(Color.DARK_GRAY);
                cancel.setForeground(Color.WHITE);
                dialogPanel.add(cancel);

                addClientDialog.add(imagePanel, BorderLayout.WEST);
                addClientDialog.add(dialogPanel, BorderLayout.CENTER);
                addClientDialog.setResizable(false);
                addClientDialog.pack();
                addClientDialog.setLocationRelativeTo(null);
                addClientDialog.setVisible(true);
            } catch (HeadlessException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * ClientNameButton inner listener class. Shows a new
     * window where you can change a name of the client.
     */
    class ChangeClientNameListener implements  ActionListener{
        public void actionPerformed(ActionEvent event) {

            if(pGUI.getClinic().getClientsList().isEmpty()) {
                    new ClinicMessageWindow("В списке нет ни одного клиента!\n Пожалуста внесите данные о клиентах\n нажав на кнопку \"ДОБАВИТЬ КЛИЕНТА\"", 3);
                    return;
            }
            textArea.setText("");
            final JDialog changeClientNameDialog = new JDialog(clinicFrame,"Изменение имени клиента", true);
            try {
                changeClientNameDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(3, 2, 5, 10));
                dialogPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                dialogPanel.setBackground(Color.DARK_GRAY);

                JLabel addClientImageLabel = new JLabel(new ImageIcon("pictures\\user-name.png"));
                JPanel imagePanel = new JPanel();
                imagePanel.setBackground(Color.DARK_GRAY);
                imagePanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                imagePanel.add(addClientImageLabel);

                JLabel oldNameLabel = new JLabel("Выберите старое имя клиента", JLabel.RIGHT);
                JLabel newNameLabel = new JLabel("Введите новое имя клиента", JLabel.RIGHT);
                oldNameLabel.setForeground(Color.WHITE);
                newNameLabel.setForeground(Color.WHITE);

                final JTextField newNameField = new JTextField(15);
                newNameField.setBackground(Color.BLACK);
                newNameField.setForeground(Color.YELLOW);
                newNameField.setFont(new Font("Times New Roman", 1, 18));
                newNameField.setCaretColor(Color.WHITE);

                final Choice nameChoice;
                nameChoice = new Choice();
                ArrayList<String> clientsNames = pGUI.getAllClientsNames(pGUI.getClinic().getClientsList());
                for (String s : clientsNames){
                    nameChoice.add(s);
                }
                nameChoice.setBackground(Color.BLACK);
                nameChoice.setForeground(Color.YELLOW);
                nameChoice.setFont(new Font("Times New Roman", 1, 16));

                dialogPanel.add(oldNameLabel);
                dialogPanel.add(nameChoice);
                dialogPanel.add(newNameLabel);
                dialogPanel.add(newNameField);

                JButton ok = new JButton("OK");
                ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String oldName = nameChoice.getSelectedItem();
                        String newName = newNameField.getText();

                        if (oldName.equals("") || newName.equals("")) {
                            new ClinicMessageWindow("Имя клиента должо содержать хотябы 1 символ!", 1);
                            return;
                        } else try {
                            pGUI.changeClientNameByUser(oldName, newName);
                            textArea.setText("");
                        } catch (UserException e1) {
                            e1.printStackTrace();
                            return;
                        }
                        changeClientNameDialog.setVisible(false);

                    }
                });
                ok.setBackground(Color.DARK_GRAY);
                ok.setForeground(Color.WHITE);
                dialogPanel.add(ok);

                JButton cancel = new JButton("Отмена");
                cancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        textArea.setText("");
                        changeClientNameDialog.setVisible(false);
                    }
                });
                cancel.setBackground(Color.DARK_GRAY);
                cancel.setForeground(Color.WHITE);
                dialogPanel.add(cancel);

                changeClientNameDialog.add(imagePanel, BorderLayout.WEST);
                changeClientNameDialog.add(dialogPanel, BorderLayout.CENTER);
                changeClientNameDialog.setResizable(false);
                changeClientNameDialog.pack();
                changeClientNameDialog.setLocationRelativeTo(null);
                if (!pGUI.getClinic().getClientsList().isEmpty()){
                    textArea.append("Вот список имен существующих клиентов:\n");
                    for(String name : clientsNames){
                        textArea.append(name + "\n");
                    }
                    changeClientNameDialog.setVisible(true);
                }
            } catch (HeadlessException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * PetNameButton inner listener class. Shows a new
     * window where you can change a name of the pet.
     */
    class ChangePetNameListener implements  ActionListener{
        public void actionPerformed(ActionEvent event) {
            if(pGUI.getClinic().getClientsList().isEmpty()) {
                new ClinicMessageWindow("В списке нет ни одного клиента!\n Пожалуста внесите данные о клиентах\n нажав на кнопку \"ДОБАВИТЬ КЛИЕНТА\"", 3);
                return;
            }
            textArea.setText("");
            final JDialog changeClientNameDialog = new JDialog(clinicFrame,"Изменение имени питомца",true);
            try {
                changeClientNameDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(3, 2, 5, 10));
                dialogPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                dialogPanel.setBackground(Color.DARK_GRAY);

                JLabel addClientImageLabel = new JLabel(new ImageIcon("pictures\\name-tag.png"));
                JPanel imagePanel = new JPanel();
                imagePanel.setBackground(Color.DARK_GRAY);
                imagePanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                imagePanel.add(addClientImageLabel);

                JLabel oldNameLabel = new JLabel("Выберите старое имя питомца", JLabel.RIGHT);
                JLabel newNameLabel = new JLabel("Введите новое имя питомца", JLabel.RIGHT);
                oldNameLabel.setForeground(Color.WHITE);
                newNameLabel.setForeground(Color.WHITE);

                final JTextField newNameField = new JTextField(15);
                newNameField.setBackground(Color.BLACK);
                newNameField.setFont(new Font("Times New Roman", 1, 18));
                newNameField.setForeground(Color.YELLOW);
                newNameField.setCaretColor(Color.YELLOW);

                final Choice nameChoice;
                nameChoice = new Choice();
                ArrayList<String> petsNames = pGUI.getAllPetsNames(pGUI.getClinic().getClientsList());
                for (String s : petsNames){
                    nameChoice.add(s);
                }
                nameChoice.setBackground(Color.BLACK);
                nameChoice.setForeground(Color.YELLOW);
                nameChoice.setFont(new Font("Times New Roman", 1, 16));

                dialogPanel.add(oldNameLabel);
                dialogPanel.add(nameChoice);
                dialogPanel.add(newNameLabel);
                dialogPanel.add(newNameField);

                JButton ok = new JButton("OK");
                ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String oldName = nameChoice.getSelectedItem();
                        String newName = newNameField.getText();

                        if (newName.equals("")) {
                            new ClinicMessageWindow("Имя питомца должо содержать хотябы 1 символ!", 3);
                            return;
                        } else try {
                            pGUI.changePetNameByUser(oldName, newName);
                        } catch (UserException e1) {
                            e1.printStackTrace();
                            return;
                        }
                        textArea.setText("");
                        changeClientNameDialog.setVisible(false);

                    }
                });
                ok.setBackground(Color.DARK_GRAY);
                ok.setForeground(Color.WHITE);
                dialogPanel.add(ok);

                JButton cancel = new JButton("Отмена");
                cancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        textArea.setText("");
                        changeClientNameDialog.setVisible(false);
                    }
                });
                cancel.setBackground(Color.DARK_GRAY);
                cancel.setForeground(Color.WHITE);
                dialogPanel.add(cancel);

                changeClientNameDialog.add(imagePanel, BorderLayout.WEST);
                changeClientNameDialog.add(dialogPanel, BorderLayout.CENTER);
                changeClientNameDialog.setResizable(false);
                changeClientNameDialog.pack();
                changeClientNameDialog.setLocationRelativeTo(null);
                if (!pGUI.getClinic().getClientsList().isEmpty()){
                    ArrayList<Pet> sortedPets = new ArrayList<Pet>();
                    try{
                    sortedPets = pGUI.getAllSortedByNamePets(pGUI.getClinic().getClientsList());}
                    catch (UserException e){
                        new ClinicMessageWindow("Ошибка!",2);
                    }
                    textArea.append("Вот список имен питомцев:\n");
                    for (Pet pet : sortedPets){
                        textArea.append(pet.getName() + " (" + pet.getKind()+ ")\n");}
                    changeClientNameDialog.setVisible(true);
                }
            } catch (HeadlessException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * FindClientButton inner listener class. Shows a new
     * window where you can find a client using a name of the pet.
     */
    class FindClientByPetNameListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            if(pGUI.getClinic().getClientsList().isEmpty()) {
                new ClinicMessageWindow("В списке нет ни одного клиента!\n Пожалуста внесите данные о клиентах\n нажав на кнопку \"ДОБАВИТЬ КЛИЕНТА\"", 3);
                return;
            }
            textArea.setText("");
            final JDialog changeClientNameDialog = new JDialog(clinicFrame,"Поиск питомца по имени клиента",true);
            try {
                changeClientNameDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(2, 2, 5, 10));
                dialogPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                dialogPanel.setBackground(Color.DARK_GRAY);

                JLabel addClientImageLabel = new JLabel(new ImageIcon("pictures\\client-searching.png"));
                JPanel imagePanel = new JPanel();
                imagePanel.setBackground(Color.DARK_GRAY);
                imagePanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                imagePanel.add(addClientImageLabel);

                JLabel petNameLabel = new JLabel("Выберите имя питомца", JLabel.RIGHT);
                petNameLabel.setForeground(Color.WHITE);

                final Choice nameChoice;
                nameChoice = new Choice();
                ArrayList<String> petsNames = pGUI.getAllPetsNames(pGUI.getClinic().getClientsList());
                for (String s : petsNames){
                    nameChoice.add(s);
                }
                nameChoice.setBackground(Color.BLACK);
                nameChoice.setForeground(Color.YELLOW);
                nameChoice.setFont(new Font("Times New Roman", 1, 16));

                dialogPanel.add(petNameLabel);
                dialogPanel.add(nameChoice);

                JButton ok = new JButton("OK");
                ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String petName = nameChoice.getSelectedItem();
                       try {
                            pGUI.findClientByUser(petName);
                        } catch (UserException e1) {
                            e1.printStackTrace();
                            return;
                        }
                        finally {
                           textArea.setText("");
                           changeClientNameDialog.setVisible(false);
                       }
                    }
                });
                ok.setBackground(Color.DARK_GRAY);
                ok.setForeground(Color.WHITE);
                dialogPanel.add(ok);

                JButton cancel = new JButton("Отмена");
                cancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        textArea.setText("");
                        changeClientNameDialog.setVisible(false);
                    }
                });
                cancel.setBackground(Color.DARK_GRAY);
                cancel.setForeground(Color.WHITE);
                dialogPanel.add(cancel);

                changeClientNameDialog.add(imagePanel, BorderLayout.WEST);
                changeClientNameDialog.add(dialogPanel, BorderLayout.CENTER);
                changeClientNameDialog.setResizable(false);
                changeClientNameDialog.pack();
                changeClientNameDialog.setLocationRelativeTo(null);
                if (!pGUI.getClinic().getClientsList().isEmpty()){
                    ArrayList<Pet> sortedPets = new ArrayList<Pet>();
                    try{
                        sortedPets = pGUI.getAllSortedByNamePets(pGUI.getClinic().getClientsList());}
                    catch (UserException e){
                        new ClinicMessageWindow("Ошибка!",2);
                    }
                    textArea.append("Вот список имен питомцев:\n");
                    for (Pet pet : sortedPets){
                        textArea.append(pet.getName() + " (" + pet.getKind()+ ")\n");}
                    changeClientNameDialog.setVisible(true);
                }
            } catch (HeadlessException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * FindPetButton inner listener class. Shows a new
     * window where you can find a pet using the name of the client.
     */
    class FindPetByClientNameListener implements  ActionListener{
        public void actionPerformed(ActionEvent event) {
            if(pGUI.getClinic().getClientsList().isEmpty()) {
                new ClinicMessageWindow("В списке нет ни одного клиента!\n Пожалуста внесите данные о клиентах\n нажав на кнопку \"ДОБАВИТЬ КЛИЕНТА\"", 3);
                return;
            }
            textArea.setText("");
            final JDialog changeClientNameDialog = new JDialog(clinicFrame,"Найти питомца по имени клиента",true);
            try {
                changeClientNameDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(2, 2, 5, 10));
                dialogPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                dialogPanel.setBackground(Color.DARK_GRAY);

                JLabel addClientImageLabel = new JLabel(new ImageIcon("pictures\\pet-searching.png"));
                JPanel imagePanel = new JPanel();
                imagePanel.setBackground(Color.DARK_GRAY);
                imagePanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                imagePanel.add(addClientImageLabel);

                JLabel clientNameLabel = new JLabel("Выберите имя клиента", JLabel.RIGHT);
                clientNameLabel.setForeground(Color.WHITE);

                final JTextField clientNameField = new JTextField(15);
                clientNameField.setBackground(Color.BLACK);
                clientNameField.setForeground(Color.YELLOW);
                clientNameField.setFont(new Font("Times New Roman", 1, 18));
                clientNameField.setCaretColor(Color.WHITE);

                final Choice nameChoice;
                nameChoice = new Choice();
                ArrayList<String> clientsNames = pGUI.getAllClientsNames(pGUI.getClinic().getClientsList());
                for (String s : clientsNames){
                    nameChoice.add(s);
                }
                nameChoice.setBackground(Color.BLACK);
                nameChoice.setForeground(Color.YELLOW);
                nameChoice.setFont(new Font("Times New Roman", 1, 16));

                dialogPanel.add(clientNameLabel);
                dialogPanel.add(nameChoice);

                JButton ok = new JButton("OK");
                ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String clientName = nameChoice.getSelectedItem();
                        try {
                            pGUI.findPetByUser(clientName);

                        } catch (UserException e1) {
                            e1.printStackTrace();
                            return;
                        }
                        finally{
                             textArea.setText("");
                            changeClientNameDialog.setVisible(false);}

                    }
                });
                ok.setBackground(Color.DARK_GRAY);
                ok.setForeground(Color.WHITE);
                dialogPanel.add(ok);

                JButton cancel = new JButton("Отмена");
                cancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        textArea.setText("");
                        changeClientNameDialog.setVisible(false);
                    }
                });
                cancel.setBackground(Color.DARK_GRAY);
                cancel.setForeground(Color.WHITE);
                dialogPanel.add(cancel);

                changeClientNameDialog.add(imagePanel, BorderLayout.WEST);
                changeClientNameDialog.add(dialogPanel, BorderLayout.CENTER);
                changeClientNameDialog.setResizable(false);
                changeClientNameDialog.pack();
                changeClientNameDialog.setLocationRelativeTo(null);
                if (!pGUI.getClinic().getClientsList().isEmpty()){
                    textArea.append("Вот список имен существующих клиентов:\n");
                    for(String name : clientsNames){
                        textArea.append(name + "\n");
                    }
                    changeClientNameDialog.setVisible(true);
                }
                else new ClinicMessageWindow("В списке нет клиентов!", 2);
            } catch (HeadlessException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * RemoveClientButton inner listener class. Shows a new
     * window where you can remove the client from the list.
     */
    class RemoveClientListener implements  ActionListener{
        public void actionPerformed(ActionEvent event) {
            if(pGUI.getClinic().getClientsList().isEmpty()) {
                new ClinicMessageWindow("В списке нет ни одного клиента!\n Пожалуста внесите данные о клиентах\n нажав на кнопку \"ДОБАВИТЬ КЛИЕНТА\"", 3);
                return;
            }
            textArea.setText("");
            final JDialog changeClientNameDialog = new JDialog(clinicFrame,"Удаление клиента",true);
            try {
                changeClientNameDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(2, 2, 5, 10));
                dialogPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                dialogPanel.setBackground(Color.DARK_GRAY);

                JLabel addClientImageLabel = new JLabel(new ImageIcon("pictures\\remove-user.png"));
                JPanel imagePanel = new JPanel();
                imagePanel.setBackground(Color.DARK_GRAY);
                imagePanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                imagePanel.add(addClientImageLabel);

                JLabel clientNameLabel = new JLabel("Выберите имя клиента", JLabel.RIGHT);
                clientNameLabel.setForeground(Color.WHITE);

                final JTextField clientNameField = new JTextField(15);
                clientNameField.setBackground(Color.BLACK);
                clientNameField.setForeground(Color.YELLOW);
                clientNameField.setFont(new Font("Times New Roman", 1, 18));
                clientNameField.setCaretColor(Color.WHITE);

                final Choice nameChoice;
                nameChoice = new Choice();
                ArrayList<String> clientsNames = pGUI.getAllClientsNames(pGUI.getClinic().getClientsList());
                for (String s : clientsNames){
                    nameChoice.add(s);
                }
                nameChoice.setBackground(Color.BLACK);
                nameChoice.setForeground(Color.YELLOW);
                nameChoice.setFont(new Font("Times New Roman", 1, 16));

                dialogPanel.add(clientNameLabel);
                dialogPanel.add(nameChoice);

                JButton ok = new JButton("OK");
                ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String clientName = nameChoice.getSelectedItem();
                        try {
                            pGUI.removeClient(clientName);

                        } catch (UserException e1) {
                            e1.printStackTrace();
                            return;
                        }
                        finally{
                            textArea.setText("");
                            changeClientNameDialog.setVisible(false);}

                    }
                });
                ok.setBackground(Color.DARK_GRAY);
                ok.setForeground(Color.WHITE);
                dialogPanel.add(ok);

                JButton cancel = new JButton("Отмена");
                cancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        textArea.setText("");
                        changeClientNameDialog.setVisible(false);
                    }
                });
                cancel.setBackground(Color.DARK_GRAY);
                cancel.setForeground(Color.WHITE);
                dialogPanel.add(cancel);

                changeClientNameDialog.add(imagePanel, BorderLayout.WEST);
                changeClientNameDialog.add(dialogPanel, BorderLayout.CENTER);
                changeClientNameDialog.setResizable(false);
                changeClientNameDialog.pack();
                changeClientNameDialog.setLocationRelativeTo(null);
                if (!pGUI.getClinic().getClientsList().isEmpty()){
                    textArea.append("Вот список имен существующих клиентов:\n");
                    for(String name : clientsNames){
                        textArea.append(name + "\n");
                    }
                    changeClientNameDialog.setVisible(true);
                }
                else new ClinicMessageWindow("В списке нет клиентов!", 2);
            } catch (HeadlessException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * RemovePetButton inner listener class. Shows a new
     * window where you can remove the pet from the list.
     */
    class RemovePetListener implements  ActionListener{
        public void actionPerformed(ActionEvent event) {
            if(pGUI.getClinic().getClientsList().isEmpty()) {
                new ClinicMessageWindow("В списке нет ни одного клиента!\n Пожалуста внесите данные о клиентах\n нажав на кнопку \"ДОБАВИТЬ КЛИЕНТА\"", 3);
                return;
            }
            textArea.setText("");
            final JDialog changeClientNameDialog = new JDialog(clinicFrame,"Удаление питомца",true);
            try {
                changeClientNameDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(2, 2, 5, 10));
                dialogPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                dialogPanel.setBackground(Color.DARK_GRAY);

                JLabel addClientImageLabel = new JLabel(new ImageIcon("pictures\\dog.png"));
                JPanel imagePanel = new JPanel();
                imagePanel.setBackground(Color.DARK_GRAY);
                imagePanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                imagePanel.add(addClientImageLabel);

                JLabel petNameLabel = new JLabel("Выберите имя питомца", JLabel.RIGHT);
                petNameLabel.setForeground(Color.WHITE);

                final Choice nameChoice;
                nameChoice = new Choice();
                ArrayList<String> petsNames = pGUI.getAllPetsNames(pGUI.getClinic().getClientsList());
                for (String s : petsNames){
                    nameChoice.add(s);
                }
                nameChoice.setBackground(Color.BLACK);
                nameChoice.setForeground(Color.YELLOW);
                nameChoice.setFont(new Font("Times New Roman", 1, 16));

                dialogPanel.add(petNameLabel);
                dialogPanel.add(nameChoice);

                JButton ok = new JButton("OK");
                ok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String petName = nameChoice.getSelectedItem();
                        try {
                            pGUI.removePet(petName);
                        } catch (UserException e1) {
                            e1.printStackTrace();
                            return;
                        }
                        finally {
                            textArea.setText("");
                            changeClientNameDialog.setVisible(false);
                        }
                    }
                });
                ok.setBackground(Color.DARK_GRAY);
                ok.setForeground(Color.WHITE);
                dialogPanel.add(ok);

                JButton cancel = new JButton("Отмена");
                cancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        textArea.setText("");
                        changeClientNameDialog.setVisible(false);
                    }
                });
                cancel.setBackground(Color.DARK_GRAY);
                cancel.setForeground(Color.WHITE);
                dialogPanel.add(cancel);

                changeClientNameDialog.add(imagePanel, BorderLayout.WEST);
                changeClientNameDialog.add(dialogPanel, BorderLayout.CENTER);
                changeClientNameDialog.setResizable(false);
                changeClientNameDialog.pack();
                changeClientNameDialog.setLocationRelativeTo(null);
                if (!pGUI.getClinic().getClientsList().isEmpty()){
                    ArrayList<Pet> sortedPets = new ArrayList<Pet>();
                    try{
                        sortedPets = pGUI.getAllSortedByNamePets(pGUI.getClinic().getClientsList());}
                    catch (UserException e){
                        new ClinicMessageWindow("Ошибка!",2);
                    }
                    textArea.append("Вот список имен питомцев:\n");
                    for (Pet pet : sortedPets){
                        textArea.append(pet.getName() + " (" + pet.getKind()+ ")\n");}
                    changeClientNameDialog.setVisible(true);
                }
            } catch (HeadlessException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * ShowClientButton inner listener class. Shows
     * the full list of clients in the text area.
     */
    class ShowClientListener implements ActionListener{
        public void actionPerformed(ActionEvent event){

            if(pGUI.getClinic().getClientsList().isEmpty()) {
                    new ClinicMessageWindow("В списке нет ни одного клиента!\n Пожалуста внесите данные о клиентах\n нажав на кнопку \"ДОБАВИТЬ КЛИЕНТА\"", 3);
                    return;
            }
            textArea.setText("");
            textArea.append("Список клиентов приведен ниже:\n");
            ArrayList<Client> sortedClientsList = pGUI.showAllClientsWithPets(pGUI.getClinic().getClientsList());
            for(Client client : sortedClientsList){
                textArea.append(client.toString());
            }
            //new ClinicMessageWindow("Для продолжения работы нажмите\"ОК\"", 1);
            //textArea.setText("");
        }
    }

    /**
     * SaveItemMenuButton inner listener class. Saves
     * current data about clients.
     */
    class SaveListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            pGUI.saveCurrentConfiguration(pGUI.getClinic());
        }
    }

    /**
     * CloseWindowButton inner listener class. Offers
     * to save data before closing the window of the program.
     */
    class ClosingWindowListener extends WindowAdapter {
        public void windowClosing(WindowEvent event){
            int dialogInput = JOptionPane.showConfirmDialog(clinicFrame, "Вы хотите сохранить данные о клиентах перед выходом?","", JOptionPane.YES_NO_OPTION);
            if(dialogInput == JOptionPane.YES_OPTION){
                pGUI.saveCurrentConfiguration(pGUI.getClinic());
            }
            else return;
        }
    }

    public JFrame getClinicFrame() {
        return this.clinicFrame;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    /**
     * Creates a new button with image on it.
     * @param fileName a name of the file containing the image.
     * @return created button.
     */
    private JButton createImageButton(final String fileName) {
        JButton button = new JButton(new ImageIcon(fileName));
        button.setBackground(Color.DARK_GRAY);
        return button;
    }

    /**
     * Creates a new label with text on it.
     * @param text  a text on the label
     * @param panel the panel to add a label.
     */
    private void addTextLabel(final String text, final JPanel panel) {
        JLabel label = new JLabel(text, JLabel.RIGHT);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Times New Roman", 0, 20));

        panel.add(label);
    }


}

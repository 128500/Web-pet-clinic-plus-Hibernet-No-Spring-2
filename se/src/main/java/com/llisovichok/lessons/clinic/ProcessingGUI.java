package com.llisovichok.lessons.clinic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

import static java.util.Collections.EMPTY_LIST;

/**
 * This class manipulates with the data or
 * actions that the user adds/performs
 * Created by ALEKSANDR KUDIN on 14.01.2017.
 */
public class ProcessingGUI {


    Clinic clinic = null;

    ProcessingGUI() {
        this.clinic = new Clinic();
    }

    public Clinic getClinic() {
        return this.clinic;
    }

     /**
     * Creates a new client
     * @return new client created by user
     * @throws UserException
     */
    public void addClient(String clientName, String petName, String petKind) throws UserException {

        clientName = clientName.trim();
        petName = petName.trim();
        if(!clinic.getClientsList().isEmpty()){
            for(Client client : clinic.getClientsList()){
                if(clientName.equals(client.getFirstName())){
                    new ClinicMessageWindow("Клиент с именем уже существует!\nК имени будет добавлен идентификатор '(1)'", 3);
                    clientName = clientName.concat("(1)");
                }
            }
        }
        Pet tempPet = createPet(petName, petKind);
        clinic.addClient(new Client(clientName, tempPet));
        new ClinicMessageWindow("Вы добавили в список нового клиента и его питомца. Это: \n" +
                clinic.getClientsList().get(clinic.getClientsCount()).toString() + "\n Теперь в списке " + (clinic.getClientsCount()+1) + " клиентов.", 1);
        clinic.setClientsCount(clinic.getClientsCount()+1);
    }

    /**
     * Creates a new pet (dog, cat, or hamster)
     * @return new Pet created by user
     */
    public Pet createPet(final String petName, final String petKind) {

        Pet p;
        if (petKind.equals("собака") || petKind.equals("dog")) {
            p = new Dog(petName);
        } else if (petKind.equals("кот") || petKind.equals("cat")) {
            p = new Cat(petName);
        } else if (petKind.equals("хомяк") || petKind.equals("hamster")) {
            p = new Hamster(petName);
        } else p = new Pet();

        return p;
    }

    /**
     * Changes the name of the client according to user input
     * @throws UserException
     */
    public void changeClientNameByUser(final String oldName, final String newName) throws UserException {
        clinic.resetClientName(oldName, newName);
    }

    /**
     * Changes the name of the pet according to user input
     * @param oldName
     * @param newName
     * @throws UserException
     */
    public void changePetNameByUser(final String oldName, final String newName) throws UserException {
        clinic.resetPetName(oldName, newName);
    }

    /**
     * Finds the client by the pet's name
     * @throws UserException
     */
    public void findClientByUser(final String petName) throws UserException {
        new ClinicMessageWindow(clinic.findClientByPetName(petName).toString(),1);
    }

    /**
     * Finds the pet by the client's name
     *
     * @throws UserException
     */
    public void findPetByUser(final String clientName) throws UserException {
        new ClinicMessageWindow(clinic.findPetByClientName(clientName).toString(), 1);
    }

    /**
     * Removes the client out of the list
     * @param clientName the name of the client
     * @throws UserException
     */
    public void removeClient(final String clientName) throws UserException{
        clinic.removeClient(clientName);
        clinic.setClientsCount(clinic.getClientsCount()-1);
        new ClinicMessageWindow("\n Теперь в списке " + (clinic.getClientsCount()) + " клиентов.", 1);
    }
    /**
     * Removes the client out of the list
     * @param petName the name of the pet
     * @throws UserException
     */
    public void removePet(final String petName)throws UserException{
       clinic.removePet(petName);
    }

    /**
     * Shows all the current clients that are in the list
     * sorting them in alphabetic order (ignores case)
     * @param clients current clients' list
     * @return sorted list of the clients
     */
    public ArrayList<Client> showAllClientsWithPets(final ArrayList<Client> clients) {

        ArrayList<String> sortedClientsNames = getAllClientsNames(clients);
        ArrayList<Client> sortedClientsList = new ArrayList<Client>();

        for (String name : sortedClientsNames){
            for(Client client : clients){
                if (name.equals(client.getFirstName())) sortedClientsList.add(client);
            }
        }
        return sortedClientsList;
    }

    /**
     * Manipulates with the names of the clients that are in the list
     * sorting them in alphabetic order (ignores case)
     * @param clients current clients' list
     * @return sorted list of the clients' names
     */
    public ArrayList<String> getAllClientsNames(final ArrayList<Client> clients) {
        ArrayList<String> clientsNames = new ArrayList<String>();
        ArrayList<String> clientsSortedNames = new ArrayList<String>();
        if (clients.isEmpty()) {
            new ClinicMessageWindow("В списке нет клиентов!", 3);
            clientsNames = new ArrayList<String>(EMPTY_LIST);
        } else {
            for (Client c : clients) {
                clientsSortedNames.add(c.getFirstName().toLowerCase());
            }
            Collections.sort(clientsSortedNames);

            for (String name : clientsSortedNames){
                for (Client client : clients){
                    if (name.equalsIgnoreCase(client.getFirstName()))
                        clientsNames.add(client.getFirstName());
                }
            }
        }
        return clientsNames;
    }

    /**
     * Manipulates with the names of the pets that are in the list
     * sorting then in alphabetic order (ignores case)
     * @param clients current clients' list
     * @return sorted list of the pets' names
     */
    public ArrayList<String> getAllPetsNames(ArrayList<Client> clients) {
        ArrayList<String> petsNames = new ArrayList<String>();
        ArrayList<String> petsSortedNames = new ArrayList<String>();
        if (clients.isEmpty()) {
            new ClinicMessageWindow("В списке нет клиентов!", 3);
            petsNames = new ArrayList<String>(EMPTY_LIST);
        } else {
            for (Client c : clients) {
                petsSortedNames.add(c.getPet().getName().toLowerCase());
            }
            Collections.sort(petsSortedNames);

            for (String name : petsSortedNames) {
                for (Client client : clients) {
                    if (name.equalsIgnoreCase(client.getPet().getName()))
                        petsNames.add(client.getPet().getName());
                }
            }

        }
        return petsNames;
    }

    /**
     * Shows all the current pets that are in the list
     * sorting them in alphabetic order (ignores case)
     * @param clients current clients' list
     * @return sorted list of the clients
     */
    public ArrayList<Pet> getAllSortedByNamePets(ArrayList<Client> clients) throws UserException{

        ArrayList<Pet> pets = new ArrayList<Pet>();
        ArrayList<String> petNames = getAllPetsNames(clients);

        for (String petName : petNames){
            for (Client client : clients){
                if (petName.equals(client.getPet().getName())){
                    pets.add(client.getPet());
                }
            }
        }
        return pets;
    }

    /**
     * Saves current configuration of the clients' list
     * @param clinic clinic
     */
    public void saveCurrentConfiguration(Clinic clinic){

       try{
           FileOutputStream fos = new FileOutputStream("Clinic.ser");
           ObjectOutputStream oos = new ObjectOutputStream(fos);
           oos.writeObject(clinic);
           oos.close();
       } catch(FileNotFoundException e){
           new ClinicMessageWindow("ОШИБКА!\nНевозможно найти файл",2);
       }catch(IOException ex){
           new ClinicMessageWindow("ОШИБКА!\nНевозможно найти файл",2);
       }
        new ClinicMessageWindow("Данные успешно сохранены!",1);
    }
}

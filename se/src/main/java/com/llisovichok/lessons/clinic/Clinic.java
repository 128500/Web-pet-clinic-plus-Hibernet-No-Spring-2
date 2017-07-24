package com.llisovichok.lessons.clinic;

import java.io.Serializable;
import java.util.*;

/**
 *This class describes database of a pets' clinic
 *and contains methods to operate with it.
 */

public class Clinic implements Serializable{

	/*A list of the clinic's clients.*/
	private final ArrayList<Client> clients;
	/*Shows how many clients are in the list now*/
	private int clientsCount = 0;

	/**
	 * Class constructor. If there is any saved data of previous
	 * sessions, it restores it.
	 */
	Clinic(){
		if (RestoreDataClass.restoredClinic == null){this.clients = new ArrayList<Client>();}
		else {
			this.clients = RestoreDataClass.restoredClinic.getClientsList();
			this.clientsCount = RestoreDataClass.restoredClinic.getClientsCount();
		}
	}

/**
*Gets the clients list.
*@return an array of the clients.
*/
	public ArrayList<Client> getClientsList(){
		return this.clients;
	}

/**
*Adds a new client to the list.
*@param client	a client to add.
*/	
	public void addClient(final Client client){
		this.clients.add(client);
	}

/**
*Finds a client by a pet name.
*@param name 	a name of a pet
*@return 		a seeking client or 'unknown' client(if there's no client with such pet's name)
*/	
	public Client findClientByPetName(final String name)throws UserException{
		
		Client c;
		int seeking = -1;
		
		for (int i = 0; i < clients.size(); i++){
			if(clients.get(i) == null) continue;
			else if(name.equals(clients.get(i).getPet().getName()))
				seeking = i;
			else continue;
		}
		if (seeking < 0){
			new ClinicMessageWindow("Нет клиента с таким именем питомца.", 1);
			c = new Client("неизвестно", new Pet(name));
		}
		else c = clients.get(seeking);
		return c;
	}

/**
*Finds a pet by a client's name
*@param name a client's name
*@return a seeking pet or 'unknown' pet if there's no such client
*/
	public Pet findPetByClientName(final String name){
		Pet p;
		int seeking = -1;
		for (int i = 0; i < clients.size(); i++){
			if(clients.get(i) == null) continue;
			else if (name.equals(clients.get(i).getFirstName())){
				seeking = i;
			}
			else continue;
		}
		
		if (seeking < 0){
			new ClinicMessageWindow("Нет такого клиента!.",3);
			p = new Pet("неизвестен");
			}
		else p = clients.get(seeking).getPet();
		
		return p;
	}

/**
*Resets a client's name. Shows a dialog window if resetting was successful.
*@param oldName		the name that must be reset
*@param newName		the name that must be set
*/	
	public void resetClientName(final String oldName, final String newName) throws UserException{
		int counter = 0;
		int seeking = 0;
		for (int i = 0; i < clients.size(); i++){
			if(clients.get(i) == null) continue;
			else if(oldName.equals(clients.get(i).getFirstName())){
				counter++;
				seeking = i;
			}
			else continue;
		}
		if(counter == 0){
			new ClinicMessageWindow("Нет клиента с таким именем!",3);
			return;
		}
		else if (counter == 1){
			Pet temp = clients.get(seeking).getPet();
			Client tem = new Client(newName, temp);
			clients.set(seeking, tem);
			new ClinicMessageWindow("Имя клиента было изменено на '" + newName+"'",1);
		}
		else new ClinicMessageWindow("\nЕсть два (или более) клиента с таким именем! \n Что делать???\n",3);
	}

/**
*Resets a pet's name. Shows a dialog window if resetting was successful.
*@param oldName		the name that must be reset
*@param newName		the name that must be set
*/	
	public void resetPetName(final String oldName, final String newName) throws UserException {
		int counter = 0;
		int seeking = 0;
		for (int i = 0; i < clients.size(); i++){
			if(clients.get(i) == null) continue;
			else if(oldName.equals(clients.get(i).getPet().getName())){
				counter++;
				seeking = i;
			}
			else continue;
		}
		if(counter == 0){
			new ClinicMessageWindow("Нет питомца с таким именем.",3);
			return;
		}
		else if (counter == 1){
			String temp = clients.get(seeking).getFirstName();

			if(clients.get(seeking).getPet().getKind().equals("dog") || clients.get(seeking).getPet().getKind().equals("собака")){
				clients.set(seeking, new Client(temp, new Dog(newName)));
			}
			else if(clients.get(seeking).getPet().getKind().equals("cat") || clients.get(seeking).getPet().getKind().equals("кот")){
				clients.set(seeking, new Client(temp, new Cat(newName)));
			}
			else if(clients.get(seeking).getPet().getKind().equals("hamster") || clients.get(seeking).getPet().getKind().equals("хомяк")){
				clients.set(seeking, new Client(temp, new Hamster(newName)));
			}
			else clients.set(seeking, new Client(temp, new Pet(newName)));

			new ClinicMessageWindow("Имя питомца было изменено на '" + newName + "'",1);
		}
		else new ClinicMessageWindow("Есть два (или более) питомца с таким именем! \n Что делать??" , 3);
	}

/**
*Removes a client from the clients list
*@param clientName 	client's name that must be removed
*/
	public void removeClient(final String clientName)throws UserException{
		int counter = 0;
		int seeking = 0;
		for (int i = 0; i < clients.size(); i++){
			if(clients.get(i) == null) continue;
			else if(clientName.equals(clients.get(i).getFirstName())){
				counter++;
				seeking = i;
			}
			else continue;
		}
		if(counter == 0){
			new ClinicMessageWindow("Нет клиента с таким именем!",3);
			return;
		}
		else if (counter == 1){
			clients.remove(seeking);
			new ClinicMessageWindow("Клиент '" + clientName +"' \nбыл удален из списка", 1);
		}
		else new ClinicMessageWindow("Есть два (или более) клиета с таким именем! \n Что делать???",3);
	
	}

/**
*Removes a pet from the clients list
*@param petName 	client's name that must be removed
*/
	public void removePet(final String petName)throws UserException{
		int counter = 0;
		int seeking = 0;
		for (int i = 0; i < clients.size(); i++){
			if(clients.get(i) == null) continue;
			else if(petName.equals(clients.get(i).getPet().getName())){
				counter++;
				seeking = i;
			}
			else continue;
		}
		if(counter == 0){
			new ClinicMessageWindow("Нет питомца с таким именем!", 3);
			return;
		}
		else if (counter == 1){
			String temp = clients.get(seeking).getFirstName();
			int index = (int)((Math.random()*1000)+1);
			clients.set(seeking, new Client(temp, new Pet("питомец был удален (" + index +")")));

			new ClinicMessageWindow("Питомец '" + petName + "' был удален",1);
		}
		else new ClinicMessageWindow("Есть два (или более) питомца с таким именем! \n Что делать???", 3);
	}

	public int getClientsCount(){
		return this.clientsCount;
	}

	public void setClientsCount(int value){
		this.clientsCount = value;
	}

}



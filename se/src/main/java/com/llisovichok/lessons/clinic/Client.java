package com.llisovichok.lessons.clinic;

import java.io.Serializable;

/**
*This class describes a client and his pet
*/
public class Client implements Serializable {

	private Integer id;

	/**
	*A first name of the client
	*/
	private  String firstName;

	/**
	 * A last name of the client
	 */
	private String lastName;

	/**
	 * Client's address
	 */
	private String address;

	/**
	 * Client's phone number
	 */
	private long phoneNumber;
	
	/**
	*A pet that the client has
	*/
	private  Pet pet;

	public Client(){}

	public Client(String firstName){
		this.firstName =  firstName;
	}

	public Client(final String firstName, final Pet pet) {
		this.firstName = firstName;
		this.pet = pet;
	}

	public Client(final String firstName, final String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Client(final String firstName, final String lastName, final String address, final long phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public Client(final String firstName, final String lastName, final Pet pet) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.pet = pet;
	}

	public Client(final String firstName, final String lastName, final String address, final long phoneNumber, final Pet pet) {
		 this.firstName = firstName;
		 this.lastName = lastName;
		 this.pet = pet;
		 this.address = address;
		 this.phoneNumber = phoneNumber;
	}


	public Integer getId() {
		return id;
	}

	public String getFirstName(){
		return this.firstName;
	}

	public String getLastName(){return  this.lastName;}

	public String getAddress(){return this.address;}

	public long getPhoneNumber(){
		return this.phoneNumber;
	}

	public Pet getPet(){
		return this.pet;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;

	}

	public void setAddress(String address){
		this.address = address;
	}

	public void setPet(Pet pet){
		this.pet = pet;
	}

	public void setPhoneNumber(long phoneNumber){
		this.phoneNumber = phoneNumber;
	}


	@Override
	public String toString() {
		return "Client{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", address='" + address + '\'' +
				", phoneNumber=" + phoneNumber +
				", pet=" + pet +
				'}';
	}
}
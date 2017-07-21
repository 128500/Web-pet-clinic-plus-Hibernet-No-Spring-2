package com.llisovichok.lessons.clinic;

import java.io.Serializable;

/**
*This class represents a client's pet
*/

public class Pet implements Serializable {

	private Integer id;

	/**
	*A name of the pet
	*/
	private String name;
	/**
	 * A kind of the pet
	 */
	private String kind = "pet";
	/**
	 * Age of the pet
	 */
	private int age;
	/**
	 * Detailed information about the pet, containing photo and health problems description
	 */
	private DetailedInfo detailedInfo;

	public Pet(){}

	public Pet(final String name){
		this.name = name;
	}

	public Pet(final String name, final String kind){
		this.name = name;
		this.kind = kind;
	}

	public Pet(final String name, final String kind, final int age){
		this.name = name;
		this.kind = kind;
		this.age = age;
	}

	public Pet(final String name, final String kind, final int age, final DetailedInfo df){
		this.name = name;
		this.kind = kind;
		this.age = age;
		this.detailedInfo = df;
	}


	public Integer getId() {
		return id;
	}

	public String getName(){
		return this.name;
	}

	public String getKind(){
		return this.kind;
	}

	public int getAge(){
		return this.age;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAge(final int age){
		this.age = age;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setKind(final String kind){
		this.kind =kind;
	}

	public DetailedInfo getDetailedInfo(){
		return this.detailedInfo;
	}

	public void setDetailedInfo(final  DetailedInfo detailedInfo) {
		this.detailedInfo = detailedInfo;
	}


	@Override
	public String toString(){
		
		return	String.format("Имя питомца '%s' (%s)", this.name, this.kind);
	}
				
}
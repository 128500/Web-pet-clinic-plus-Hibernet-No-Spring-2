package com.llisovichok.lessons.clinic;
/**
* This class is an example of the Pet class
 * and represents a cat as a client's pet
*/

public class Dog extends Pet{

	private static final String KIND = "dog";//"dog"

/**
*Class constructor
*@param name a dog's name
*/	
	public Dog(final String name){
		super(name);
	}

/**
*Class constructor with no parameters
*/
	public Dog(){
		super();
	}

	public String getName(){
		return super.getName();
	}


	public String getKind(){
		return this.KIND;
	}

	@Override
	public String toString(){
		return String.format("Питомец '%s'.\nИмя питомца '%s'. \n\n", getKind(), getName());
	}

}
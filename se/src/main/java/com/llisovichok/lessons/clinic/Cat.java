package com.llisovichok.lessons.clinic;
/**
*This class is an example of the Pet class
 * and represents a cat as a client's pet
*/
public class Cat extends Pet{


	private static final String KIND = "cat";/*cat*/
/**
*Main class constructor
*@param name a cat's name
*/
	public Cat(final String name){
		super(name);
	}

/**
*Class constructor with no parameters
*/
	public Cat(){
		super();
	}

	public String getName(){
		return super.getName();
	}

	public String getKind(){
		return KIND;
	}

	@Override
	public String toString(){
		return String.format("Питомец '%s'.\nИмя питомца '%s'. \n\n", getKind(), getName());
	}
}
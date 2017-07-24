package com.llisovichok.lessons.clinic;

/**
 *  This class is an example of the Pet class
 * and represents a hamster as a client's pet
 * Created by ALEKSANDR KUDIN on 31.12.2016.
 */
public class Hamster extends Pet {

    private static final String KIND = "hamster";//"hamster"

    /**
     *Class constructor
     *@param name a hamster's name
     */
    public Hamster(final String name){
        super(name);
    }

    /**
     *Class constructor with no parameters
     */
    public Hamster(){
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

package com.llisovichok.models;

/**
 * Created by KUDIN ALEKSANDR on 15.07.2017.
 */
public class Role extends Base{

    private String name;

    public Role(){}
    public Role(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

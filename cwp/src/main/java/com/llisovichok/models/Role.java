package com.llisovichok.models;


import javax.persistence.*;
/**
 * Created by KUDIN ALEKSANDR on 15.07.2017.
 */

@Entity
@Table(name = "ROLE_T")
public class Role extends Base{

    @Column(name  = "name")
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

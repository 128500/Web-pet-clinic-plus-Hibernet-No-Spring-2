package com.llisovichok.models;

import javax.persistence.*;

/**
 * Created by KUDIN ALEKSANDR on 21.07.2017.
 */
@MappedSuperclass
public abstract class Base {

    @Id @GeneratedValue
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

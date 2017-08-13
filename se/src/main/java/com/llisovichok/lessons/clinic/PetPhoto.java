package com.llisovichok.lessons.clinic;

import com.llisovichok.lessons.clinic.Pet;

import java.io.Serializable;

/**
 * Created by KUDIN ALEKSANDR on 02.08.2017.
 */
public class PetPhoto implements Serializable {

    private Integer id;
    private Pet pet;
    private byte[] image;

    public PetPhoto(){}

    public PetPhoto(final byte[] image){
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public Pet getPet() {
        return pet;
    }

    public byte[] getImage() {
        return image;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

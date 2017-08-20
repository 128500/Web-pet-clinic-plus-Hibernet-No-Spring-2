package com.llisovichok.models;

import com.llisovichok.lessons.clinic.Pet;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;


/**
 * Created by ALEKSANDR KUDIN on 23.03.2017.
 */
public class User extends com.llisovichok.lessons.clinic.Client implements Serializable {

    private Role role;

    private Set<Message> messages;

    public User() {
        super();
    }

    public User(String firstName) {
        super(firstName);
    }

    public User(String firstName, Pet pet) {
        super(firstName, pet);
    }

    public User(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public User(String firstName, String lastName, String address, long phoneNumber) {
        super(firstName, lastName, address, phoneNumber);
    }

    public User(String firstName, String lastName, Pet pet) {
        super(firstName, lastName, pet);
    }

    public User(String firstName, String lastName, String address, long phoneNumber, Pet pet) {
        super(firstName, lastName, address, phoneNumber, pet);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public Set<Message> getMessages() {
        if(messages != null) return messages;
        else return Collections.emptySet();
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }


    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public long getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public Pet getPet() {
        return super.getPet();
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    @Override
    public void setAddress(String address) {
        super.setAddress(address);
    }

    @Override
    public void setPet(Pet pet) {
        super.setPet(pet);
    }

    @Override
    public void setPhoneNumber(long phoneNumber) {
        super.setPhoneNumber(phoneNumber);
    }

    @Override
    public String toString() {
        return super.toString() +"User{" +
                "role=" + role +
                ", messages=" + messages +
                '}';
    }
}

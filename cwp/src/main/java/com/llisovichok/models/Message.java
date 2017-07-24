package com.llisovichok.models;

/**
 * Created by KUDIN ALEKSANDR on 21.07.2017.
 */
public class Message {

    private Integer id;
    private User user;
    private String text;

    public Message(){}

    public Message(String text){
        this.text = text;
    }

    public Integer getId(){
        return this.id;
    }

    public User getUser(){
        return this.user;
    }

    public String getText(){
        return this.text;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setText(String text){
        this.text = text;
    }
}

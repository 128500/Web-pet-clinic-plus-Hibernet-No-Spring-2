package com.llisovichok.models;


import javax.persistence.*;
/**
 * Created by KUDIN ALEKSANDR on 21.07.2017.
 */
@Entity
@Table(name="MESSAGE_T")
public class Message {

    @Id @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "text")
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

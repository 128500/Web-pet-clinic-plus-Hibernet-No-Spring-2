package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.Message;
import com.llisovichok.models.Role;
import com.llisovichok.models.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by KUDIN ALEKSANDR on 15.07.2017.
 */


public class HibernateStorageTest {

    public static void main(String[] args) {
        HibernateStorage HIBERNATE_STORAGE  = HibernateStorage.getInstance();
        User user = new User();
        user.setFirstName("Nathan");
        user.setLastName("Parker");
        user.setAddress("Fatal st., 10");
        user.setPhoneNumber(2589639632L);
        user.setPet(new Pet("Garry", "hamster", 5));

        Role role = new Role();
        role.setName("admin");

        user.setRole(role);

        Message message = new Message();
        message.setText("First text message");
        message.setUser(user);

        Set<Message> messages = new HashSet<>();
        messages.add(message);
        user.setMessages(messages);

        User user2 = new User();
        user2.setFirstName("Dora");
        user2.setLastName("Ramona");
        user2.setPhoneNumber(1258478950L);
        user2.setAddress("Gimo st., 8");
        user2.setPet(new Pet("Ghyom", "dog", 7));

        Role role2 = new Role();
        role.setName("user");

        user2.setRole(role2);

        Message message2 = new Message();
        message2.setText("Second text message");
        message.setUser(user2);

        Set<Message> messages2 = new HashSet<>();
        messages2.add(message2);
        user2.setMessages(messages2);


        HIBERNATE_STORAGE.add(user);
        HIBERNATE_STORAGE.add(user2);

        Collection<User> users =  HIBERNATE_STORAGE.values();

        for(User u : users){
            System.out.println(u.toString());
        }

    }
}

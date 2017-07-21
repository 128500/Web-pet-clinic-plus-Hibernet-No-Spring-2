package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.User;

import java.util.Collection;

/**
 * Created by KUDIN ALEKSANDR on 15.07.2017.
 */

/**
public class HibernateStorageTest {

    public static void main(String[] args) {
        HibernateStorage HIBERNATE_STORAGE  = HibernateStorage.getInstance();
        User user = new User();
        //user.setId(1);
        user.setFirstName("Nathan");
        user.setLastName("Parker");
        user.setAddress("Fatal st., 10");
        user.setPhoneNumber(2589639632L);
        user.setPet(new Pet("Garry", "hamster", 5));

        User user2 = new User();
        user2.setFirstName("Dora");
        user2.setLastName("Ramona");
        user2.setPhoneNumber(1258478950L);
        user2.setAddress("Gimo st., 8");
        user2.setPet(new Pet("Ghyom", "dog", 7));
        HIBERNATE_STORAGE.add(user);
        HIBERNATE_STORAGE.add(user2);

        Collection<User> users =  HIBERNATE_STORAGE.values();

        for(User u : users){
            System.out.println(u.toString());
        }

    }
}*/

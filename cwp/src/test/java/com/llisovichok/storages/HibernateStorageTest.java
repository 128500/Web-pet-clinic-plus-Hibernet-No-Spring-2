package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.User;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by KUDIN ALEKSANDR on 16.07.2017.
 */
public class HibernateStorageTest {
    HibernateStorage H_STORAGE = HibernateStorage.getInstance();
    @Test
    public void values() throws Exception {
        for(int i = 0; i < 10; i++){
            User u = new User("Test", "Test", "Test", 1201251455454L, new Pet("Test", "test",3));
            H_STORAGE.add(u);
        }

        ArrayList<User> users = (ArrayList<User>) H_STORAGE.values();
        assertTrue(users.size() == 10);
        for(User user : users){
            assertTrue(user.getFirstName().equals("Test"));
            assertTrue(user.getLastName().equals("Test"));
            assertTrue(user.getAddress().equals("Test"));
            assertTrue(user.getPhoneNumber() == 1201251455454L);
            assertTrue(user.getPet().getName().equals("Test"));
            assertTrue(user.getPet().getKind().equals("test"));
            assertTrue(user.getPet().getAge() == 3);
        }
    }

    @Test
    public void add() throws Exception {
        User user = new User("Gordon", "Dannison", "Test", 11111111111L,
                new Pet("Harpy", "harpy", 5));
        int number = 0;
        number  = H_STORAGE.add(user);
        System.out.println("Number  = " + number);
        assertTrue(number > 0);

        User retrieved  = H_STORAGE.getUser(number);
        assertTrue(retrieved.getFirstName().equals("Gordon"));
        assertTrue(retrieved.getLastName().equals("Dannison"));
        assertTrue(retrieved.getAddress().equals("Test"));
        assertTrue(retrieved.getPhoneNumber() == 11111111111L);
        assertTrue(retrieved.getPet().getName().equals("Harpy"));
        assertTrue(retrieved.getPet().getKind().equals("harpy"));
        assertTrue(retrieved.getPet().getAge() == 5);
    }

    @Test
    public void edit() throws Exception {
        User u = new User("Test", "Test", "Test", 1201251455454L,
                new Pet("Test", "test",3));
        Integer number = H_STORAGE.add(u);

        User user = new User("Gordon", "Dannison", "Test", 11111111111L,
                new Pet("Harpy", "harpy", 5));
        H_STORAGE.edit(number, user);

        User retrieved  = H_STORAGE.getUser(number);
        assertTrue(number == retrieved.getId());
        assertTrue(retrieved.getFirstName().equals("Gordon"));
        assertTrue(retrieved.getLastName().equals("Dannison"));
        assertTrue(retrieved.getAddress().equals("Test"));
        assertTrue(retrieved.getPhoneNumber() == 11111111111L);
        assertTrue(retrieved.getPet().getName().equals("Harpy"));
        assertTrue(retrieved.getPet().getKind().equals("harpy"));
        assertTrue(retrieved.getPet().getAge() == 5);
    }

    @Test
    public void getUser() throws Exception {
        User user1  = new User("Test", "Test", "Test", 1201251455454L,
                new Pet("Test", "test",3));
        Integer number1 = H_STORAGE.add(user1);

        User user2 = new User("Gordon", "Dannison", "Test", 11111111111L,
                new Pet("Harpy", "harpy", 5));
        Integer number2 = H_STORAGE.add(user2);

        User retrieved2  = H_STORAGE.getUser(number2);
        assertTrue(number2 == retrieved2.getId());
        assertTrue(retrieved2.getFirstName().equals("Gordon"));
        assertTrue(retrieved2.getLastName().equals("Dannison"));
        assertTrue(retrieved2.getAddress().equals("Test"));
        assertTrue(retrieved2.getPhoneNumber() == 11111111111L);
        assertTrue(retrieved2.getPet().getName().equals("Harpy"));
        assertTrue(retrieved2.getPet().getKind().equals("harpy"));
        assertTrue(retrieved2.getPet().getAge() == 5);

        User retrieved1  = H_STORAGE.getUser(number1);
        assertTrue(number1 == retrieved1.getId());
        assertTrue(retrieved1.getFirstName().equals("Test"));
        assertTrue(retrieved1.getLastName().equals("Test"));
        assertTrue(retrieved1.getAddress().equals("Test"));
        assertTrue(retrieved1.getPhoneNumber() == 1201251455454L);
        assertTrue(retrieved1.getPet().getName().equals("Test"));
        assertTrue(retrieved1.getPet().getKind().equals("test"));
        assertTrue(retrieved1.getPet().getAge() == 3);
    }

    @Test
    public void removeUser() throws Exception {

        ArrayList<User> users_before = (ArrayList<User>)H_STORAGE.values();

        User user1  = new User("Test", "Test", "Test", 1201251455454L,
                new Pet("Test", "test",3));
        Integer number1 = H_STORAGE.add(user1);

        User user2 = new User("Gordon", "Dannison", "Test", 11111111111L,
                new Pet("Harpy", "harpy", 5));
        Integer number2 = H_STORAGE.add(user2);

        H_STORAGE.removeUser(number1);
        H_STORAGE.removeUser(number2);
        ArrayList<User> users = (ArrayList<User>)H_STORAGE.values();
        assertTrue(users.size() == users_before.size());

    }

    @Test
    public void addPhoto() throws Exception {
    }

    @Test
    public void findUsers() throws Exception {
    }

}
package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.Message;
import com.llisovichok.models.Role;
import com.llisovichok.models.User;
import junit.framework.AssertionFailedError;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by KUDIN ALEKSANDR on 16.07.2017.
 */
public class HibernateStorageTest {
    private final static HibernateStorage H_STORAGE = HibernateStorage.getInstance();
    private final static Message m1 = new Message("first");
    private final static Message m2 = new Message("second");
    private final static Set<Message> messages = new HashSet<>();

    private User createUser1() {
        User u = new User("Test", "Test", "Test", 1201251455454L,
                new Pet("Test", "test",3));
        u.setRole(new Role ("user"));
        return u;
    }

    private User createUser2() {
        User u = new User("Gordon", "Dannison", "Test", 11111111111L,
                new Pet("Harpy", "harpy", 5));
        u.setRole(new Role("admin"));
        return u;
    }

    private boolean checkUser1(User user) throws AssertionFailedError {
        assertTrue(user.getFirstName().equals("Test"));
        assertTrue(user.getLastName().equals("Test"));
        assertTrue(user.getAddress().equals("Test"));
        assertTrue(user.getPhoneNumber() == 1201251455454L);
        assertTrue(user.getPet().getName().equals("Test"));
        assertTrue(user.getPet().getKind().equals("test"));
        assertTrue(user.getPet().getAge() == 3);
        assertTrue(user.getRole().getName().equals("user"));
        for (Message m : user.getMessages()) {
            assertTrue(m.getText().equals("first") || m.getText().equals("second"));
        }

        return true;
    }

    private boolean checkChangedUser(User user) throws AssertionFailedError {
        assertTrue(user.getFirstName().equals("Gordon"));
        assertTrue(user.getLastName().equals("Dannison"));
        assertTrue(user.getAddress().equals("Test"));
        assertTrue(user.getPhoneNumber() == 11111111111L);
        assertTrue(user.getPet().getName().equals("Harpy"));
        assertTrue(user.getPet().getKind().equals("harpy"));
        assertTrue(user.getPet().getAge() == 5);
        assertTrue(user.getRole().getName().equals("admin"));
        for (Message m : user.getMessages()) {
            assertTrue(m.getText().equals("first") || m.getText().equals("second"));
        }

        return true;
    }

    @Test
    public void values() throws Exception {
        for(int i = 0; i < 10; i++){

            User user = createUser1();

            final int id = H_STORAGE.add(user);
            User retrieved  = H_STORAGE.getUser(id);

            m1.setUser(retrieved);
            m2.setUser(retrieved);

            messages.add(m1);
            messages.add(m2);
            retrieved.setMessages(messages);

            H_STORAGE.edit(id, retrieved);
        }

        ArrayList<User> users = (ArrayList<User>) H_STORAGE.values();
        assertTrue(users.size() == 10);
        for(User u : users){
            assertEquals(true, checkUser1(u));
        }
    }

    @Test
    public void add() throws Exception {

        Integer id  = H_STORAGE.add(this.createUser1());
        System.out.println("Id : " + id);
        assertTrue(id > 0);

        User retrieved  = H_STORAGE.getUser(id);

        m1.setUser(retrieved);
        m2.setUser(retrieved);

        messages.add(m1);
        messages.add(m2);
        retrieved.setMessages(messages);

        H_STORAGE.edit(id, retrieved);

        retrieved  = H_STORAGE.getUser(id);

        assertEquals(true, this.checkUser1(retrieved));
    }

    @Test
    public void edit() throws Exception {

        Integer id = H_STORAGE.add(this.createUser1());

        User retrieved = H_STORAGE.getUser(id);

        assertTrue(retrieved.getMessages().isEmpty());
        assertEquals(true, this.checkUser1(retrieved));

        retrieved.setFirstName("Gordon");
        retrieved.setLastName("Dannison");
        retrieved.setAddress("Test");
        retrieved.setPhoneNumber(11111111111L);
        retrieved.getPet().setName("Harpy");
        retrieved.getPet().setKind("harpy");
        retrieved.getPet().setAge(5);
        retrieved.setRole(new Role("admin"));

        m1.setUser(retrieved);
        m2.setUser(retrieved);

        messages.add(m1);
        messages.add(m2);
        retrieved.setMessages(messages);

        H_STORAGE.edit(id, retrieved);

        retrieved  = H_STORAGE.getUser(id);
        assertEquals(true, checkChangedUser(retrieved));
    }

    @Test
    public void getUser() throws Exception {

        Integer id = H_STORAGE.add(this.createUser2());

        User retrieved  = H_STORAGE.getUser(id);

        m1.setUser(retrieved);
        m2.setUser(retrieved);

        messages.add(m1);
        messages.add(m2);
        retrieved.setMessages(messages);

        H_STORAGE.edit(id, retrieved);

        retrieved  = H_STORAGE.getUser(id);

        assertEquals(true, checkChangedUser(retrieved));
    }

    @Test
    public void removeUser() throws Exception {

        ArrayList<User> users_before = (ArrayList<User>)H_STORAGE.values();

        Integer id1 = H_STORAGE.add(createUser1());
        Integer id2 = H_STORAGE.add(createUser2());

        ArrayList<User> users_current = (ArrayList<User>)H_STORAGE.values();
        assertTrue(users_current.size() > users_before.size());
        H_STORAGE.removeUser(id1);
        H_STORAGE.removeUser(id2);
        ArrayList<User> users_after = (ArrayList<User>)H_STORAGE.values();
        assertTrue(users_after.size() == users_before.size());

    }

    @Test
    public void findUsers() throws Exception {

        User gordon = createUser1();
        gordon.setFirstName("Sam");
        gordon.setLastName("Edison");
        gordon.getPet().setName("Darko");
        gordon.setAddress("Fifth av.");

        User don = createUser1();
        don.setFirstName("Sam");
        don.setLastName("Ramsy");
        don.getPet().setName("Darko");
        don.setAddress("Fifth av.");

        User bob = createUser1();
        bob.setFirstName("Bob");
        bob.setLastName("Ramsy");
        bob.getPet().setName("Diana");
        bob.setAddress("Passadina st.");

        H_STORAGE.add(gordon);
        H_STORAGE.add(don);
        H_STORAGE.add(bob);

        /* Searching in users' first names */
        /* Two matches found*/
        ArrayList<User> users = (ArrayList<User>) H_STORAGE.findUsers("sam", true, false, false);
        assertTrue(users.size() == 2);
        for(User u : users){
            assertTrue(u.getFirstName().equals("Sam"));
        }

        /* One match found*/
        users = (ArrayList<User>) H_STORAGE.findUsers("Bob", true, false, false);
        assertTrue(users.size() == 1);
        assertTrue(users.get(0).getFirstName().equals("Bob"));

        /* No matches found*/
        users = (ArrayList<User>) H_STORAGE.findUsers("111", true, false, false);
        assertTrue(users.size() == 0);


        /* Searching in users' last names*/
        /* Two matches found*/
        users = (ArrayList<User>) H_STORAGE.findUsers("Ramsy", false, true, false);
        assertTrue(users.size() == 2);
        for(User u : users){
            assertTrue(u.getLastName().equals("Ramsy"));
        }

        /* One match found*/
        users = (ArrayList<User>) H_STORAGE.findUsers("Edison", false, true, false);
        assertTrue(users.size() == 1);
        assertTrue(users.get(0).getLastName().equals("Edison"));

        /* No matches found*/
        users = (ArrayList<User>) H_STORAGE.findUsers("111", false, true, false);
        assertTrue(users.size() == 0);



        /* Searching in pets' names*/
        /* Two matches found*/
        users = (ArrayList<User>) H_STORAGE.findUsers("Darko", false, false, true);
        assertTrue(users.size() == 2);
        for(User u : users){
            assertTrue(u.getPet().getName().equals("Darko"));
        }

        /* One match found*/
        users = (ArrayList<User>) H_STORAGE.findUsers("Diana", false, false, true);
        assertTrue(users.size() == 1);
        assertTrue(users.get(0).getPet().getName().equals("Diana"));

        /* No matches found*/
        users = (ArrayList<User>) H_STORAGE.findUsers("111", false, false, true);
        assertTrue(users.size() == 0);



        /* Searching in addresses */
        /* Two matches found */
        users = (ArrayList<User>) H_STORAGE.findUsers("Fifth av.", false, false, false);
        assertTrue(users.size() == 2);
        for(User u : users){
            assertTrue(u.getAddress().equals("Fifth av."));
        }

        /* One match found */
        users = (ArrayList<User>) H_STORAGE.findUsers("Passadina st.", false, false, false);
        assertTrue(users.size() == 1);
        assertTrue(users.get(0).getAddress().equals("Passadina st."));

        /* No matches found */
        users = (ArrayList<User>) H_STORAGE.findUsers("111", false, false, false);
        assertTrue(users.size() == 0);
    }
}
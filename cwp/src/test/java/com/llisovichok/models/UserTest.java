package com.llisovichok.models;

import com.llisovichok.lessons.clinic.Pet;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by KUDIN ALEKSANDR on 16.09.2017.
 */
public class UserTest {

    @Test
    public void getMessages() throws Exception {
        User u = new User("Gordon", "Dannison", "Test", 11111111111L,
                new Pet("Harpy", "harpy", 5));
        u.setRole(new Role("admin"));
        Message m = new Message("first");
        Message m2 = new Message("second");
        Set<Message> messages = new HashSet<>();
        messages.add(m);
        messages.add(m2);
        u.setMessages(messages);
        Set<Message> mes = u.getMessages();
        assertTrue(mes.containsAll(messages));
    }

}
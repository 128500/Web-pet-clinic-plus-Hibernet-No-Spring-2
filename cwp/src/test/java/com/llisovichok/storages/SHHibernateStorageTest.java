package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.Role;
import com.llisovichok.models.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by KUDIN ALEKSANDR on 13.10.2017.
 */
public class SHHibernateStorageTest {

    private final ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
    private final Storages storages = context.getBean(Storages.class);

    @Test
    public void values() throws Exception {
    }

    @Test
    public void addUser() throws Exception {
    }

    @Test
    public void editUser() throws Exception {
    }

    @Test
    public void getUser() throws Exception {
        User user = new User("Danni",
                "Groover",
                "Princess Diana st., 19",
                582369258410L,
                new Pet("Fuco", "dog", 5));
        user.setRole(new Role("admin"));

        Integer id  = storages.shHiberStorage.addUser(user);
        User retrieved =  storages.shHiberStorage.getUser(id);

        assertEquals("Danni", retrieved.getFirstName());
        assertEquals("Groover", retrieved.getLastName());
        assertEquals("Princess Diana st., 19", retrieved.getAddress());
        assertEquals(582369258410L, retrieved.getPhoneNumber());
        assertEquals("Fuco", retrieved.getPet().getName());
        assertEquals("dog", retrieved.getPet().getKind());
        assertEquals(5, retrieved.getPet().getAge());
        assertEquals("admin", retrieved.getRole().getName());
    }

    @Test
    public void removeUser() throws Exception {
    }

    @Test
    public void addUser1() throws Exception {
    }

    @Test
    public void addPhoto() throws Exception {
    }

    @Test
    public void findUsers() throws Exception {
    }

}
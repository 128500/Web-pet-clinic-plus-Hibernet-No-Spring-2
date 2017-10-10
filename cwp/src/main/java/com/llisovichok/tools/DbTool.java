package com.llisovichok.tools;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.Role;
import com.llisovichok.models.User;
import com.llisovichok.storages.SHHibernateStorage;
import com.llisovichok.storages.Storages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;

/**
 * Created by KUDIN ALEKSANDR on 23.09.2017.
 */
public class DbTool {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        Storages storages = context.getBean(Storages.class);

        User user = new User("Mark",
                "Timmerberg",
                "Glase st., 258-4",
                125477447448l,
                new Pet("Hippo", "iguana", 2));
        user.setRole(new Role("user"));
        //int idHibernate = storages.hibernateStorage.addUser(user);
        int idJdbc = storages.jdbcStorage.addUser(user);
        user.setId(1);
        storages.memoryStorage.addUser(user);
        int hiberNumber = storages.shHiberStorage.addUser(user);
        ArrayList<User> users = (ArrayList<User>)storages.shHiberStorage.values();

        System.out.println(users.iterator().next().toString());
        System.out.println(storages.jdbcStorage.getUser(idJdbc).toString());
        System.out.println(storages.memoryStorage.getUser(1).toString());
    }
}

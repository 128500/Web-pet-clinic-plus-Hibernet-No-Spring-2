package com.llisovichok.tools;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.Role;
import com.llisovichok.models.User;
import com.llisovichok.storages.Storages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by KUDIN ALEKSANDR on 23.09.2017.
 */
public class DbTool {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        Storages storages = context.getBean(Storages.class);
        //System.out.println(storages.hibernateStorage.values());
        User user = new User("Mark",
                "Timmerberg",
                "Glase st., 258-4",
                125477447448l,
                new Pet("Hippo", "iguana", 2));
        user.setRole(new Role("user"));
        int idHibernate = storages.hibernateStorage.addUser(user);
        int idJdbc = storages.jdbcStorage.addUser(user);
        user.setId(1);
        storages.memoryStorage.addUser(user);

        System.out.println(storages.hibernateStorage.getUser(idHibernate).toString());
        System.out.println(storages.jdbcStorage.getUser(idJdbc).toString());
        System.out.println(storages.memoryStorage.getUser(1).toString());
    }
}

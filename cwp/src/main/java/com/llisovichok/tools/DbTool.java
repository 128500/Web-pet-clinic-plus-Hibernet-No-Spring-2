package com.llisovichok.tools;

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
        System.out.println(storages.hibernateStorage.values());
        storages.hibernateStorage.addUser(new User());
    }

}

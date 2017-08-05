package com.llisovichok.storages;

import com.llisovichok.models.User;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ALEKSANDR KUDIN on 12.04.2017.
 */
public interface Storage {

    Collection<User> values();

    int addUser(User user);

    void editUser(Integer id, User user);

    ConcurrentHashMap<Integer, User> getUsers();

    User getUser(Integer id);

    void removeUser(Integer userId);

    void addUser(Integer id, User user);

    void addPhoto(Integer userId, ByteArrayInputStream photoBytes, int streamSize);

    Collection<User> findUsers(String input, boolean lookInFirstName, boolean lookInLastName, boolean lookInPetName);

    void close();
}

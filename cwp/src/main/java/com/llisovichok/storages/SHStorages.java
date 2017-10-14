package com.llisovichok.storages;

import com.llisovichok.models.User;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by KUDIN ALEKSANDR on 08.10.2017.
 */
public interface SHStorages <T> {

    Collection<T> values();

    int addUser(T user);

    boolean editUser(Integer id, User user);

    T getUser(Integer id);

    void removeUser(Integer userId);

    Collection<T> findUsers(String input, boolean lookInFirstName, boolean lookInLastName, boolean lookInPetName);
}

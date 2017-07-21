package com.llisovichok.storages;

import com.llisovichok.models.User;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ALEKSANDR KUDIN on 12.04.2017.
 */
public class UserCache implements Storage {

    private final static UserCache INSTANCE = new UserCache();

    private final Storage memoryStorage = new MemoryStorage();

    @Override
    public Collection<User> values() {
        return this.memoryStorage.values();
    }

    @Override
    public int add(final User user) {
       return  this.memoryStorage.add(user);

    }

    @Override
    public void addPhoto(Integer userId, ByteArrayInputStream photoBytes, int streamSize){
    }

    @Override
    public void edit(final Integer id, final User user) {
        this.memoryStorage.edit(id, user);

    }

    @Override
    public ConcurrentHashMap<Integer, User> getUsers() {
        return this.memoryStorage.getUsers();
    }

    @Override
    public User getUser(Integer id) {
        return this.memoryStorage.getUser(id);
    }

    @Override
    public void removeUser(Integer userId) {
        this.memoryStorage.removeUser(userId);

    }

    @Override
    public void add(Integer id, User user) {
        this.memoryStorage.add(id, user);
    }



    @Override
    public Collection<User> findUsers(String input, boolean lookInFirstName,
                                      boolean lookInLastName, boolean lookInPetName) {
        return null;
    }

    @Override
    public void close(){ this.memoryStorage.close();}
}

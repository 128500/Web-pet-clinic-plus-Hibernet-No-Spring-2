package com.llisovichok.storages;

import com.llisovichok.models.User;

import java.io.ByteArrayInputStream;
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
    public int addUser(final User user) {
       return  this.memoryStorage.addUser(user);

    }

    /* Not implemented here */
    @Override
    public void addPhoto(Integer userId, ByteArrayInputStream photoBytes, int streamSize){
    }

    @Override
    public void editUser(final Integer id, final User user) {
        this.memoryStorage.editUser(id, user);

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
    public void addUser(Integer id, User user) {
        this.memoryStorage.addUser(id, user);
    }



    @Override
    public Collection<User> findUsers(String input, boolean lookInFirstName,
                                      boolean lookInLastName, boolean lookInPetName) {
        return null;
    }

    @Override
    public void close(){ this.memoryStorage.close();}
}

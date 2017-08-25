package com.llisovichok.storages;

import com.llisovichok.models.User;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ALEKSANDR KUDIN on 12.04.2017.
 */
public class MemoryStorage implements Storage {

    private AtomicInteger ids = new AtomicInteger();
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    @Override
    public Collection<User> values() {
        return this.users.values();
    }

    @Override
    public int addUser(final User user) {
        this.users.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public void addPhoto(Integer userId, ByteArrayInputStream photoBytes, int streamSize){
    }


    @Override
    public boolean editUser(final Integer id, final User user) {
        return false;
    }

    @Override
    public ConcurrentHashMap<Integer, User> getUsers() {
        return this.users;
    }

    @Override
    public User getUser(Integer id) {
        return this.users.get(id);
    }

    @Override
    public void removeUser(Integer userId) {
        this.users.remove(userId);

    }

    @Override
    public void addUser(Integer id, User user) {
        this.users.put(this.ids.incrementAndGet(), user);
    }

    @Override
    public Collection<User> findUsers(String input, boolean lookInFirstName,
                                      boolean lookInLastName, boolean lookInPetName) {
        return null;
    }

    @Override
    public void close(){}
}

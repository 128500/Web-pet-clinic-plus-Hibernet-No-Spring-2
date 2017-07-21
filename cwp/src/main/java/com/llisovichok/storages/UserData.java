package com.llisovichok.storages;

import com.llisovichok.models.User;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ALEKSANDR KUDIN on 23.03.2017.
 */
public class UserData {

    private final static UserData INSTANCE = new UserData();

    private final static ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<Integer, User>();

    public static UserData getInstance() {
        return INSTANCE;
    }

    public Collection<User> values(){
        return this.users.values();
    }

    public ConcurrentHashMap<Integer, User> getUsers(){
        return this.users;
    }

    public static User getUser(Integer id) {
        return users.get(id);
    }

    public void removeUser(Integer userId){ this.users.remove(userId);}

    public void add( final Integer id, final User user){
        this.users.put(id, user);
    }

}

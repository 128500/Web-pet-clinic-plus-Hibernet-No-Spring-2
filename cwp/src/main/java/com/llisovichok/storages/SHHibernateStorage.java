package com.llisovichok.storages;

import com.llisovichok.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.List;

/**
 * Created by KUDIN ALEKSANDR on 08.10.2017.
 */

@Repository
public class SHHibernateStorage implements SHHiberStorage {

    public final HibernateTemplate template;

    @Autowired
    SHHibernateStorage(HibernateTemplate template){
        this.template = template;
    }


    @Override
    public Collection<User> values() {
        return (List<User>) this.template.find("FROM com.llisovichok.models.User u JOIN FETCH u.pet JOIN FETCH u.role ORDER BY u.id");
    }

    @Transactional
    @Override
    public int addUser(User user) {
        return (int)this.template.save(user);
    }

    @Override
    public boolean editUser(Integer id, User user) {
        return false;
    }

    @Override
    public User getUser(Integer id) {
        return null;
    }

    @Override
    public void removeUser(Integer userId) {

    }

    @Override
    public void addUser(Integer id, User user) {

    }

    @Override
    public void addPhoto(Integer userId, ByteArrayInputStream photoBytes, int streamSize) {

    }

    @Override
    public Collection<User> findUsers(String input, boolean lookInFirstName, boolean lookInLastName, boolean lookInPetName) {
        return null;
    }

    @Override
    public void close() {

    }
}

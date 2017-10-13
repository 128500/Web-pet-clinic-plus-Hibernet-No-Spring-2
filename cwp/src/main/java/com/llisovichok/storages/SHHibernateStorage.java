package com.llisovichok.storages;

import com.llisovichok.models.User;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
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
    public SHHibernateStorage(HibernateTemplate template){
        this.template = template;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<User> values() {
        return (List<User>) this.template.find("FROM com.llisovichok.models.User u JOIN FETCH u.pet JOIN FETCH u.role ORDER BY u.id");
    }

    @Transactional
    @Override
    public int addUser(User user) {
        return (int)this.template.save(user);
    }

    @Transactional
    @Override
    public boolean editUser(Integer id, User user) {
        boolean result = false;
        try {
            User retrievedUser = this.template.load(User.class, id);
            retrievedUser.setFirstName(user.getFirstName());
            retrievedUser.setLastName(user.getLastName());
            retrievedUser.setAddress(user.getAddress());
            retrievedUser.setPhoneNumber(user.getPhoneNumber());
            retrievedUser.getPet().setName(user.getPet().getName());
            retrievedUser.getPet().setKind(user.getPet().getKind());
            retrievedUser.getPet().setAge(user.getPet().getAge());
            if (user.getMessages() != null) {
                retrievedUser.getMessages().addAll(user.getMessages());
            }
            retrievedUser.getRole().setName(user.getRole().getName());
            this.template.saveOrUpdate(retrievedUser);
            result = true;
        } catch (HibernateException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User getUser(Integer id) {

        return (User)this.template.findByNamedParam("from User user join fetch " +
                "user.pet join fetch user.role where user.id= :id", "id", id).iterator().next();
    }

    @Transactional
    @Override
    public void removeUser(Integer userId) {
        User user = this.template.load(User.class, userId);
        if(user != null) this.template.delete(user);
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

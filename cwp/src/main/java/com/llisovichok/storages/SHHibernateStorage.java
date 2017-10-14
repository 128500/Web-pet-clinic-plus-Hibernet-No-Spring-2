package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.lessons.clinic.PetPhoto;
import com.llisovichok.models.Message;
import com.llisovichok.models.User;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @SuppressWarnings("unchecked")
    @Override
    public Collection<User> findUsers(String input, boolean lookInFirstName, boolean lookInLastName, boolean lookInPetName) {
        return (List<User>) this.template.find("FROM com.llisovichok.models.User user " +
                "JOIN FETCH user.pet JOIN FETCH user.role " +
                "WHERE lower(user.firstName) like ? " +
                "OR lower(user.lastName) like ? " +
                "OR lower(user.pet.name) like ? " +
                "OR lower(user.address) like ? " +
                "ORDER BY user.id ASC",
                lookInFirstName ? "%" + input.toLowerCase() + "%" : "",
                lookInLastName ? "%" + input.toLowerCase() + "%" : "",
                lookInPetName ? "%" + input.toLowerCase() + "%" : "",
                !lookInFirstName && !lookInLastName && !lookInPetName ? "%" + input.toLowerCase() + "%" : "");
    }

    @Transactional
    @Override
    public boolean addPhotoWithHibernate(Integer petId, byte[] photoBytes) {
        boolean result = false;
        try {
            Pet pet = getPetById(petId);
            PetPhoto petPhoto = new PetPhoto(photoBytes);
            petPhoto.setPet(pet);
            pet.setPhoto(petPhoto);
            this.template.saveOrUpdate(pet);
            result = true;
        }  catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Pet getPetById(Integer petId) {
        return (Pet)this.template.findByNamedParam("from Pet p " +
                "left join fetch p.photo where p.id = :id", "id", petId).
                iterator().next();
    }

    @Transactional
    @Override
    public void addMessage(Integer id, String messageText) {
        Message message = new Message(messageText);

        User user = this.template.load(User.class, id);
        message.setUser(user);
        if (user.getMessages() != null) {
            user.getMessages().add(message);
        } else {
            Set<Message> messages = new HashSet<Message>();
            messages.add(message);
            user.setMessages(messages);
        }
        this.template.saveOrUpdate(user);
    }

}

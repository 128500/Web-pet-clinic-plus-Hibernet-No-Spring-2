package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.lessons.clinic.PetPhoto;
import com.llisovichok.models.User;

import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO addUser description to addPhotoWithHibernate method, implement method
 * <p>
 * Created by KUDIN ALEKSANDR on 01.07.2017.
 */
public class HibernateStorage implements HiberStorage {

    private static final HibernateStorage INSTANCE = new HibernateStorage();


    private static SessionFactory factory;

    static {
        try {
            StandardServiceRegistry standardRegistry =
                    new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata metaData =
                    new MetadataSources(standardRegistry).getMetadataBuilder().build();
            factory = metaData.getSessionFactoryBuilder().build();
        } catch (Throwable th) {
            System.err.println("Initial SessionFactory creation failed" + th);
            throw new ExceptionInInitializerError(th);
        }
    }

    public static SessionFactory getFactory() {
        return factory;
    }

    public static HibernateStorage getInstance() {
        return INSTANCE;
    }

    /*Programming pattern - Command*/
    interface Command<T> {
        T execute(Session session);
    }

    /**
     * Executes actions (such as creating session and transaction)
     * before and after main action (e.g. CRUD) that executes
     * by anonymous class implementing <tt>Command interface</tt>
     *
     * @param command - a reference to generic type class object
     * @param <T>     returning generic type
     * @return an object acquired as a result of the transaction to the database
     * (may also be 'void' if a transaction returns void)
     */
    private <T> T transaction(Command<T> command) {
        Transaction tx = null;
        T genericType;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            genericType = command.execute(session);
            tx.commit();
            return genericType; //note (!) that return statement cannot be placed before committing the transaction
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        throw new IllegalStateException("Some problem occurred! Couldn't execute the transaction!");
    }

    /**
     * Retrieves the data of all users from the database
     *
     * @return collection of User.class objects
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<User> values() {
        return transaction((Session session) -> session.createQuery(
                "FROM com.llisovichok.models.User u " +
                        "JOIN FETCH u.pet JOIN FETCH u.role ORDER BY u.id").list());
    }

    /**
     * Adds new user into database
     *
     * @param user - an object of User.class that must be saved
     * @return id of saved user
     */
    @Override
    public int addUser(final User user) {
        return (Integer) transaction((Session session) -> session.save(user));
    }

    /**
     * Edits current data of the user
     *
     * @param id   - user's id number
     * @param user an object of User.class that must be saved
     */
    @Override
    public void editUser(final Integer id, final User user) {
        user.setId(id);
        transaction(
                (Session session) -> {
                    User retrievedUser = session.load(User.class, id);
                    retrievedUser.setFirstName(user.getFirstName());
                    retrievedUser.setLastName(user.getLastName());
                    retrievedUser.setAddress(user.getAddress());
                    retrievedUser.setPhoneNumber(user.getPhoneNumber());
                    retrievedUser.getPet().setName(user.getPet().getName());
                    retrievedUser.getPet().setKind(user.getPet().getKind());
                    retrievedUser.getPet().setAge(user.getPet().getAge());
                    retrievedUser.getMessages().addAll(user.getMessages());
                    retrievedUser.getRole().setName(user.getRole().getName());
                    session.saveOrUpdate(retrievedUser);
                    return null;
                }
        );
    }

    /**
     * Retrieves user from the database
     *
     * @param id - user's id number assigned in database
     * @return an object of User.class
     */
    @SuppressWarnings("unchecked")
    @Override
    public User getUser(Integer id) {
        User user;
        user = (User)transaction(session -> {
            Query query = session.createQuery("from User user join fetch user.pet " +
                    "join fetch user.role where user.id= :id");
            query.setParameter("id", id);
            return query.list().iterator().next();
        });
        if (user != null) return user;
        else throw new IllegalStateException("Couldn't find data with such 'id'");
    }

    /**
     * Deletes user from database
     *
     * @param userId - user's id number
     */
    @Override
    public void removeUser(Integer userId) {
        transaction(session -> {
            User user = session.load(User.class, userId);
            if(user != null) session.delete(user);
            return null;
        });
    }

    /**
     *Adds a photo of a pet to the database
     * @param userId - id of a user to whom pet it need to be set a photo
     * @param photoBytes - binary photograph data as <tt>ByteArrayInputStream</tt>
     * @param streamSize - size of <tt>ByteArrayInputStream</tt>
     */
    @Override
    public void addPhoto(Integer userId, ByteArrayInputStream photoBytes, int streamSize) {
/**
        Pet pet = getUser(userId).getPet();
        PetPhoto photo = new PetPhoto();

        byte[] photoBuffer = new byte[streamSize];
        try {
            photoBytes.read(photoBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        photo.setImage(photoBuffer);
        photo.setPet(pet);
        transaction(session -> session.save(photo));*/
    }

    /**
     * Adds a photo of a pet to the database
     * @param id - id of a pet it need to be set a photo
     * @param photo - binary photograph data as an array of bytes
     */
    @Override
    public void addPhotoWithHibernate(Integer id, byte[] photo){
        //User user = getUser(id);
        Pet pet = getPetById(id);
        PetPhoto petPhoto = new PetPhoto(photo);
        petPhoto.setPet(pet);
        pet.setPhoto(petPhoto);

        transaction((Session session) -> {
            session.saveOrUpdate(pet);
            return null;
        });
    }

    /**
     * Retrieve pet identity from the database according to the given pet's id
     * @param id - pet's id in the database
     * @return pet object
     */
    @Override
    public Pet getPetById(final Integer id){
        return (Pet)transaction((Session session) -> session.createQuery("FROM Pet pet " +
                "LEFT JOIN FETCH pet.photo WHERE pet.id = :id")
                .setParameter("id", id)
                .list()
                .iterator()
                .next()
        );
    }


    /**
     * Searches for matches in users' first names, last names,
     * pet's names or addresses according to inputted  value
     *
     * @param input           inputted value for searching
     * @param lookInFirstName marker for searching of matches in first name
     * @param lookInLastName  marker for searching of matches in last name
     * @param lookInPetName   marker for searching of matches in pet's name
     * @return results of searching as a collection of User.class objects
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<User> findUsers(String input, boolean lookInFirstName,
                                      boolean lookInLastName, boolean lookInPetName) {
        Collection<User> users;

        /*Solution with a criteria*/
        /**
         users = transaction (
         session ->{
         Criteria criteria = session.createCriteria(com.llisovichok.models.User.class, "user");
         criteria.createAlias("user.pet", "pet");
         Disjunction disjunction = Restrictions.disjunction();// to set disjunction mode e.g.'first' OR 'last' OR 'petName'

         if(lookInFirstName) {
         disjunction.addUser(Restrictions.ilike("user.firstName", input, MatchMode.ANYWHERE));
         }

         if(lookInLastName) {
         disjunction.addUser(Restrictions.ilike("user.lastName", input, MatchMode.ANYWHERE));
         }

         if(lookInPetName) {
         disjunction.addUser(Restrictions.ilike("pet.name", input, MatchMode.ANYWHERE));
         }

         if(!lookInFirstName && !lookInLastName && !lookInPetName){
         disjunction.addUser(Restrictions.ilike("user.address", input, MatchMode.ANYWHERE));
         }
         criteria.addUser(disjunction);
         return criteria.addOrder(Order.asc("id")).list();
         }
         );*/

        /*Solution with HQL*/
        users = transaction(session -> {
            Query query = session.createQuery("FROM com.llisovichok.models.User user " +
                    "JOIN FETCH user.pet JOIN FETCH user.role " +
                    "WHERE lower(user.firstName) like ? " +
                    "OR lower(user.lastName) like ? " +
                    "OR lower(user.pet.name) like ? " +
                    "OR lower(user.address) like ? " +
                    "ORDER BY user.id ASC");
            query.setParameter(0, lookInFirstName ? "%" + input.toLowerCase() + "%" : "");
            query.setParameter(1, lookInLastName ? "%" + input.toLowerCase() + "%" : "");
            query.setParameter(2, lookInPetName ? "%" + input.toLowerCase() + "%" : "");
            query.setParameter(3, !lookInFirstName && !lookInLastName && !lookInPetName ? "%" + input.toLowerCase() + "%" : "");
            return query.list();
        });

        if (users != null) return users;
        else return Collections.emptyList();
    }

    /**
     * Closes current session factory
     */
    @Override
    public void close() {
        factory.close();
    }

    /* Not implemented here*/
    @Override
    public ConcurrentHashMap<Integer, User> getUsers() {
        return null;
    }

    /*Not implemented here*/
    @Override
    public void addUser(Integer id, User user) {

    }

}

package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.User;

import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO add description to addPhoto method
 *
 * Created by KUDIN ALEKSANDR on 01.07.2017.
 */
public class HibernateStorage implements Storage {

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

    public static HibernateStorage getInstance() {
        return INSTANCE;
    }

    interface Command<T>{
        T execute(Session session);
    }

    private <T> T transaction(Command<T> command){
        Transaction tx = null;
        try(Session session = factory.openSession()){
                session.beginTransaction();
                return command.execute(session);
        } catch (HibernateException e){
            if(tx != null) tx.rollback();
            e.printStackTrace();
        } finally{
            if (tx!= null)tx.commit();
        }
        throw new IllegalStateException("Some problem occurred! Couldn't execute the process!");
    }


    /**
     * Retrieves the data of all users from the database
     * @return collection of User.class objects
     */
    @Override
    public Collection<User> values() {

        Transaction tx = null;
        List<User> hiberUsers = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            hiberUsers = session.createQuery("FROM com.llisovichok.models.User u INNER JOIN FETCH u.pet INNER JOIN FETCH u.role").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        if (hiberUsers != null) return hiberUsers;
        else return Collections.emptyList();
    }

    /**
     * Adds new user into database
     * @param user - an object of User.class that must be saved
     * @return id of saved user
     */
    @Override
    public int add(final User user) {
        return transaction(new Command<Integer>() {
            @Override
            public Integer execute(Session session) {
                return (Integer) session.save(user);
            }
        });
    }

    /**
     * Edits current data of the user
     * @param id - user's id number
     * @param user an object of User.class that must be saved
     */
    @Override
    public void edit(Integer id, User user) {

        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            user.setId(id);
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public ConcurrentHashMap<Integer, User> getUsers() {
        return null;
    }

    /**
     * Retrieves user from the database
     * @param id - user's id number assigned in database
     * @return an object of User.class
     */
    @Override
    public User getUser(Integer id) {

        Transaction tx = null;
        User user = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM com.llisovichok.models.User u INNER JOIN FETCH u.pet " +
                    "INNER JOIN FETCH u.role" +
                    " WHERE u.id =:id");
            q.setParameter("id", id);
            //user = (User) q.list().get(0);
            user  = session.get(User.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        if (user != null) return user;
        else throw new IllegalStateException("Couldn't find data with such 'id'");
    }

    /**
     * Deletes user from database
     * @param userId - user's id number
     */
    @Override
    public void removeUser(Integer userId) {

        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            Query q = session.createQuery("DELETE com.llisovichok.models.User u WHERE u.id = :id");
            q.setParameter("id", userId);
            q.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    /*Not implemented here*/
    @Override
    public void add(Integer id, User user) {

    }

    @Override
    public void addPhoto(Integer userId, ByteArrayInputStream photoBytes, int streamSize) {

    }

    /**
     * Searches for matches in users' first names, last names,
     * pet's names or addresses according to inputted  value
     * @param input inputted value for searching
     * @param lookInFirstName marker for searching of matches in first name
     * @param lookInLastName marker for searching of matches in last name
     * @param lookInPetName marker for searching of matches in pet's name
     * @return results of searching as a collection of User.class objects
     */
    @Override
    public Collection<User> findUsers(String input, boolean lookInFirstName,
                                      boolean lookInLastName, boolean lookInPetName) {

        Collection<User> users = null;
        Transaction tx = null;
        try(Session session = factory.openSession()){

            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(com.llisovichok.models.User.class, "user");
            criteria.createAlias("user.pet", "pet");

            Disjunction disjunction = Restrictions.disjunction();// to set disjunction mode e.g.'first' OR 'last' OR 'petName'

            if(lookInFirstName) {
                disjunction.add(Restrictions.ilike("user.firstName", input, MatchMode.ANYWHERE));
            }

            if(lookInLastName) {
                disjunction.add(Restrictions.ilike("user.lastName", input, MatchMode.ANYWHERE));
            }

            if(lookInPetName) {
                disjunction.add(Restrictions.ilike("pet.name", input, MatchMode.ANYWHERE));
            }

            if(!lookInFirstName && !lookInLastName && !lookInPetName){
                disjunction.add(Restrictions.ilike("user.address", input, MatchMode.ANYWHERE));
            }

            criteria.add(disjunction);
            users = criteria.addOrder(Order.asc("id")).list();

            tx.commit();
        } catch(HibernateException e){
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
        if(users != null) return users;
        else return Collections.emptyList();
    }

    /**
     * Closes current session factory
     */
    @Override
    public void close() {
        factory.close();
    }
}

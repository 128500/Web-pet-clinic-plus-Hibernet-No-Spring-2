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

    public static SessionFactory getFactory() {
        return factory;
    }


    public static HibernateStorage getInstance() {
        return INSTANCE;
    }


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

    @Override
    public int add(User user) {
        Transaction tx = null;
        Integer userId;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            userId = (Integer) session.save(user);
            session.getTransaction().commit();
            return userId;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        throw new IllegalStateException("Couldn't save data into database!");
    }

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
            user = (User) q.list().get(0);
            //user  = session.get(User.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        if (user != null) return user;
        else throw new IllegalStateException("Couldn't find data with such 'id'");
    }

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

    @Override
    public void add(Integer id, User user) {

    }

    @Override
    public void addPhoto(Integer userId, ByteArrayInputStream photoBytes, int streamSize) {

    }

    @Override
    public Collection<User> findUsers(String input, boolean lookInFirstName, boolean lookInLastName, boolean lookInPetName) {

        Collection<User> users = null;
        Transaction tx = null;
        try(Session session = factory.openSession()){

            boolean first = lookInFirstName; //should we look coincidences in first names
            boolean last = lookInLastName; //should we look coincidences in last names
            boolean pet = lookInPetName; //should we look coincidences in pet's names

            tx = session.beginTransaction();
            //Criteria criteria = session.createCriteria(com.llisovichok.models.User.class, "user");
            //Disjunction disjunction = Restrictions.disjunction();// to set disjunction mode e.g. 'first' OR 'last' OR 'petName'

            DetachedCriteria crit = DetachedCriteria.forClass(com.llisovichok.models.User.class, "user");
            crit.createAlias("user.pet", "pet");
            Disjunction disjunction = Restrictions.disjunction();// to set disjunction mode e.g. 'first' OR 'last' OR 'petName'


            if(first) {
                disjunction.add(Restrictions.ilike("user.firstName", input, MatchMode.ANYWHERE));
                //crit.add(Restrictions.or(Restrictions.ilike("user.firstName", input, MatchMode.ANYWHERE)));
            }

            if(last) {
                disjunction.add(Restrictions.ilike("user.lastName", input, MatchMode.ANYWHERE));
                //crit.add(Restrictions.or(Restrictions.ilike("user.lastName", input, MatchMode.ANYWHERE)));
            }

            if(pet) {
                /**Disjunction disj = Restrictions.disjunction();
                disj.add(Restrictions.ilike(Pet.getName(), input, MatchMode.ANYWHERE))
                criteria.createAlias("pet", "pet", JoinType.INNER_JOIN)
                        .add(Restrictions.ilike("pet.name", input, MatchMode.ANYWHERE));*/
                //crit.add(Restrictions.or(Restrictions.ilike("pet.name", input, MatchMode.ANYWHERE)));
                disjunction.add(Restrictions.or(Restrictions.ilike("pet.name", input, MatchMode.ANYWHERE)));
            }

            if(!first && !last && !pet){
                disjunction.add(Restrictions.ilike("user.address", input, MatchMode.ANYWHERE));
                //crit.add(Restrictions.or(Restrictions.ilike("address", input, MatchMode.ANYWHERE)));
            }

            /**if(disjunction.conditions() != null && disjunction.conditions().iterator().hasNext()){
                criteria.add(disjunction);
            }
            users = criteria.list();*/
            crit.add(disjunction);
            users = crit.getExecutableCriteria(session).addOrder(Order.asc("id")).list();

            tx.commit();
        } catch(HibernateException e){
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
        if(users != null) return users;
        else return Collections.emptyList();
    }

    @Override
    public void close() {
    }
}

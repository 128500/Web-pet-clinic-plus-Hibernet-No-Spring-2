package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.lessons.clinic.PetPhoto;
import com.llisovichok.models.Message;
import com.llisovichok.models.Role;
import com.llisovichok.models.User;

import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by KUDIN ALEKSANDR on 01.07.2017.
 *
 * This class contains methods (CRUD) to interact with the
 * database via Hibernate framework
 */
public class HibernateStorage implements HiberStorage {

    private static HibernateStorage INSTANCE;
    private static SessionFactory sessionFactory;

    static {
        try {
            /**sessionFactory = new AnnotationConfiguration().configure().
                    addPackage("com.llisovichok.models").
                    addAnnotatedClass(User.class).
                    addAnnotatedClass(Role.class).
                    addAnnotatedClass(Message.class).
                    addPackage("com.llisovichok.lessons.clinic").
                    addAnnotatedClass(Pet.class).
                    addAnnotatedClass(PetPhoto.class).
                    buildSessionFactory();*/

            StandardServiceRegistry standardRegistry =
                    new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata metaData =
                    new MetadataSources(standardRegistry).getMetadataBuilder().build();
            sessionFactory = metaData.getSessionFactoryBuilder().build();

        } catch (Throwable th) {
            System.err.println("Initial SessionFactory creation failed" + th);
            throw new ExceptionInInitializerError(th);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static HibernateStorage getInstance() {
        if(INSTANCE != null)return INSTANCE;
        else return new HibernateStorage();
    }

    /*Uses only in test purposes*/
    public static void setInstance(HibernateStorage storage){
        INSTANCE = storage;
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
     * (may also be 'void' if the transaction returns void)
     */
    private <T> T transaction(Command<T> command) {
        Transaction tx = null;
        T genericType;
        try (Session session = sessionFactory.openSession()) {
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
     *  @param id   - user's id number
     * @param user an object of User.class that must be saved
     */
    @Override
    public boolean editUser(final Integer id, final User user) {
        return transaction(
                (Session session) -> {
                    User retrievedUser = session.load(User.class, id);
                    retrievedUser.setFirstName(user.getFirstName());
                    retrievedUser.setLastName(user.getLastName());
                    retrievedUser.setAddress(user.getAddress());
                    retrievedUser.setPhoneNumber(user.getPhoneNumber());
                    retrievedUser.getPet().setName(user.getPet().getName());
                    retrievedUser.getPet().setKind(user.getPet().getKind());
                    retrievedUser.getPet().setAge(user.getPet().getAge());
                    if(user.getMessages() != null){
                        retrievedUser.getMessages().addAll(user.getMessages());
                    }
                    retrievedUser.getRole().setName(user.getRole().getName());
                    session.saveOrUpdate(retrievedUser);
                    return true;
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
    public User getUser(Integer id){
        try {
            return (User) transaction(session -> {
                Query query = session.createQuery("from User user join fetch user.pet " +
                        "join fetch user.role where user.id= :id");
                query.setParameter("id", id);
                return query.list().iterator().next();
            });
        } catch(NoSuchElementException e){
            //need to be added a logger!
            e.printStackTrace();
            return new User();
       }
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
    public boolean addPhotoWithHibernate(Integer id, byte[] photo){
        Pet pet = getPetById(id);
        PetPhoto petPhoto = new PetPhoto(photo);
        petPhoto.setPet(pet);
        pet.setPhoto(petPhoto);

        transaction((Session session) -> {
            session.saveOrUpdate(pet);
            return true;
        });
        return false;
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
     * Adds a message addressed to a certain user into the database
     * @param id - user's id to whom the message addressed
     * @param messageText - a message to store
     */
    @Override
    public void addMessage(final Integer id, final String messageText){
        Message message = new Message(messageText);
        transaction(session -> {
          User user =  session.load(User.class, id);
          message.setUser(user);
          if(user.getMessages() != null){
              user.getMessages().add(message);
          } else{
              Set<Message> messages = new HashSet<Message>();
              messages.add(message);
              user.setMessages(messages);
          }
          session.saveOrUpdate(user);
          return null;
        });
    };

    /**
     * Not implemented here
     */
    @Override
    public void close() {
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

package com.llisovichok.storages;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.User;
import com.llisovichok.service.Settings;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.Collection;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ALEKSANDR KUDIN on 25.04.2017.
 */
public class JdbcStorageTest extends Mockito {

    final static JdbcStorage JDBC_STORAGE = JdbcStorage.getINSTANCE();

    Connection con = null;

    private Connection setConnection() {
        Settings settings = Settings.getInstance();
        try {

            Class.forName(settings.getValue("jdbc.driver_class"));
            con = DriverManager.getConnection(settings.getValue("jdbc.url"), settings.getValue("jdbc.username"), settings.getValue("jdbc.password"));
            return con;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
         throw new IllegalStateException("Couldn't get requested connection");
    }
    @Ignore
    @Test
    public void values() throws Exception {

        con = setConnection();

        int amountOfUsers = -1;

        try(Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM public.clients")){

            while(rs.next()){
                 amountOfUsers = rs.getInt("total");
             }

        } catch (SQLException ex){
                throw new IllegalStateException(ex);
        }

        Collection<User> users = JDBC_STORAGE.values();

        int listSize = users.size();

        assertEquals(listSize, amountOfUsers);

        con.close();

        User test1 = new User("Test", "Testing", "Test", 647834783, new Pet("Test", "test", 2));
        User test2 = new User("Opra", "Jenkins", "Greedy st., 458", 58235419, new Pet("Dodik", "snake", 4));

        int id1, id2;
        id1 = JDBC_STORAGE.add(test1);
        id2 = JDBC_STORAGE.add(test2);

        assertTrue(id1 > 0 && id2 > 0);

        Collection<User> users1 = JDBC_STORAGE.values();

        ArrayList<User> usersList = new ArrayList<>(users1);
        User example1 = null;
        User example2 = null;

        for(User u : usersList){
            if(u.getId() == id1) example1 = u;
            if(u.getId() == id2) example2 = u;
        }

        assertEquals("Test", example1.getFirstName() );
        assertEquals("Testing", example1.getLastName() );
        assertEquals("Test", example1.getAddress() );
        assertEquals(647834783, example1.getPhoneNumber() );
        assertEquals("Test", example1.getPet().getName() );
        assertEquals("test", example1.getPet().getKind() );
        assertEquals(2, example1.getPet().getAge() );

        assertEquals("Opra", example2.getFirstName() );
        assertEquals("Jenkins", example2.getLastName() );
        assertEquals("Greedy st., 458", example2.getAddress() );
        assertEquals(58235419, example2.getPhoneNumber() );
        assertEquals("Dodik", example2.getPet().getName() );
        assertEquals("snake", example2.getPet().getKind() );
        assertEquals(4, example2.getPet().getAge() );

        JDBC_STORAGE.removeUser(id1);
        JDBC_STORAGE.removeUser(id2);
    }

    @Test
    public void add() throws Exception {
        User user = new User("Test", "Testing", "Test", 647834783, new Pet("Test", "test", 2));
        int id;
        id = JDBC_STORAGE.add(user);
        assertTrue(id > 0);

        User addedUser = JDBC_STORAGE.getUser(id);
        assertEquals("Test", addedUser.getFirstName() );
        assertEquals("Testing", addedUser.getLastName() );
        assertEquals("Test", addedUser.getAddress() );
        assertEquals(647834783, addedUser.getPhoneNumber() );
        assertEquals("Test", addedUser.getPet().getName() );
        assertEquals("test", addedUser.getPet().getKind() );
        assertEquals(2, addedUser.getPet().getAge() );

        JDBC_STORAGE.removeUser(id);
    }

    @Test
    public void edit() throws Exception {
        User user = new User("Gal", "Hulio", "Hepa st., 47", 647834783, new Pet("Joric", "dog", 2));
        User alteredUser = new User("Opra", "Jenkins", "Greedy st., 458", 58235419, new Pet("Dodik", "snake", 4));
        int id;
        id = JDBC_STORAGE.add(user);
        assertTrue(id >0);
        JDBC_STORAGE.edit(id, alteredUser);
        User alterEgo = JDBC_STORAGE.getUser(id);
        assertEquals("Opra", alterEgo.getFirstName() );
        assertEquals("Jenkins", alterEgo.getLastName() );
        assertEquals("Greedy st., 458", alterEgo.getAddress() );
        assertEquals(58235419, alterEgo.getPhoneNumber() );
        assertEquals("Dodik", alterEgo.getPet().getName() );
        assertEquals("snake", alterEgo.getPet().getKind() );
        assertEquals(4, alterEgo.getPet().getAge() );
        JDBC_STORAGE.removeUser(id);
    }

    @Test
    public void getUser() throws Exception {
        User user = new User("Test", "Test", "Test", 103, new Pet("Test", "test", 100));
        int id = JDBC_STORAGE.add(user);
        assertTrue(id > 0);
        User obtainedUser = JDBC_STORAGE.getUser(id);
        assertEquals("Test", obtainedUser.getFirstName());
        assertEquals("Test", obtainedUser.getLastName());
        assertEquals("Test", obtainedUser.getAddress());
        assertEquals(103, obtainedUser.getPhoneNumber());
        assertEquals("Test", obtainedUser.getPet().getName());
        assertEquals("test", obtainedUser.getPet().getKind());
        assertEquals(100, obtainedUser.getPet().getAge());
        JDBC_STORAGE.removeUser(id);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeUser() throws Exception {
        User user = new User("Test", "Test", "Test", 103, new Pet("Test", "test", 100));
        int id = JDBC_STORAGE.add(user);
        assertTrue(id > 0);
        JDBC_STORAGE.removeUser(id);
        JDBC_STORAGE.getUser(id);
    }
    @Ignore
    @Test
    public void findUsers() throws Exception {

        User user = new User("Test", "Test", "Test", 103, new Pet("TEST", "test", 100));
        User user2 = new User("Pest", "Test", "Test", 103, new Pet("TEST", "test", 100));
        User user3 = new User("Dest", "Test", "Test", 103, new Pet("TEST", "test", 100));
        int id = JDBC_STORAGE.add(user);
        int id2 = JDBC_STORAGE.add(user2);
        int id3 = JDBC_STORAGE.add(user3);

        try {
            assertTrue(id > 0);
            assertTrue(id2 > 0);
            assertTrue(id3 > 0);

            Collection<User> collection = JDBC_STORAGE.findUsers("est", true, false, false);
            ArrayList<User> result = new ArrayList<>(collection);

            assertTrue(result.size() == 3);

            User user_obtained1 = result.get(0);
            User user_obtained2 = result.get(1);
            User user_obtained3 = result.get(2);

            assertEquals("Test", user_obtained1.getFirstName());
            assertEquals("Pest", user_obtained2.getFirstName());
            assertEquals("Dest", user_obtained3.getFirstName());


            Collection<User> collection2 = JDBC_STORAGE.findUsers("Pe", true, false, false);
            ArrayList<User> result2 = new ArrayList<>(collection2);

            assertTrue(result2.size() == 1);

            User user_obtained4 = result2.get(0);

            assertEquals("Pest", user_obtained4.getFirstName());


            Collection<User> collection3 = JDBC_STORAGE.findUsers("Test", true, true, false);
            ArrayList<User> result3 = new ArrayList<>(collection3);

            assertTrue(result3.size() == 3);

            User user_obtained5 = result3.get(0);
            User user_obtained6 = result3.get(1);
            User user_obtained7 = result3.get(2);

            assertEquals("Test", user_obtained5.getLastName());
            assertEquals("Test", user_obtained6.getLastName());
            assertEquals("Test", user_obtained7.getLastName());


            Collection<User> collection4 = JDBC_STORAGE.findUsers("TEST", false, false, true);
            ArrayList<User> result4 = new ArrayList<>(collection4);

            assertTrue(result4.size() == 3);

            User user_obtained8 = result4.get(0);
            User user_obtained9 = result4.get(1);
            User user_obtained10 = result4.get(2);

            assertEquals("TEST", user_obtained8.getPet().getName());
            assertEquals("TEST", user_obtained9.getPet().getName());
            assertEquals("TEST", user_obtained10.getPet().getName());


            Collection<User> collection5 = JDBC_STORAGE.findUsers("T", false, true, false);
            ArrayList<User> result5 = new ArrayList<>(collection5);

            assertTrue(result5.size() == 3);

            User user_obtained11 = result5.get(0);
            User user_obtained12 = result5.get(1);
            User user_obtained13 = result5.get(2);

            assertEquals("Test", user_obtained11.getLastName());
            assertEquals("Test", user_obtained12.getLastName());
            assertEquals("Test", user_obtained13.getLastName());
        }

        finally{
            JDBC_STORAGE.removeUser(id);
            JDBC_STORAGE.removeUser(id2);
            JDBC_STORAGE.removeUser(id3);
        }
    }
}
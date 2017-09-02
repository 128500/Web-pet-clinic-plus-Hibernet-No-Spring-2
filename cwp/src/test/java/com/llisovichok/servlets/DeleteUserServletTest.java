package com.llisovichok.servlets;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.Role;
import com.llisovichok.storages.HibernateStorage;
import com.llisovichok.storages.JdbcStorage;
import com.llisovichok.models.User;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by ALEKSANDER KUDIN on 01.04.2017.
 */

public class DeleteUserServletTest extends Mockito {

    //final static UserData USER_DATA = UserData.getInstance();
    //final static JdbcStorage JDBC_STORAGE = JdbcStorage.getINSTANCE();
    private final static HibernateStorage H_STORAGE = HibernateStorage.getInstance();

    @Test
    public void testDoGet() throws Exception {

        System.out.println("\n=========================================================================================");
        System.out.println("Testing  || " + this.getClass().getName() + " || testDoGet()");
        System.out.println("\n=========================================================================================");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        User userOne = new User("t", "t", "t", 12L, new Pet("t", "t", 1));
        userOne.setRole(new Role("user"));
        User userTwo = new User("t", "t", "t", 12L, new Pet("t", "t", 2));
        userTwo.setRole(new Role("user"));

        Integer  idOne = null, idTwo = null;

        idOne = H_STORAGE.addUser(userOne);
        idTwo = H_STORAGE.addUser(userTwo);

        ArrayList<User> before = new ArrayList<>(H_STORAGE.values());
        ArrayList<Integer> ids = new ArrayList<>();

        for (User u : before) {
            ids.add(u.getId());
        }
        assertTrue(ids.contains(idOne));
        assertTrue(ids.contains(idTwo));
        int beforeSize = before.size();

        when(request.getParameter("id")).thenReturn(String.valueOf(idOne));
        new DeleteUserServlet().doGet(request, response);
        verify(request, atLeast(1)).getParameter("id");
        assertEquals((beforeSize - 1), H_STORAGE.values().size());

        when(request.getParameter("id")).thenReturn(String.valueOf(idTwo));
        new DeleteUserServlet().doGet(request, response);
        verify(request, atLeast(1)).getParameter("id");
        assertEquals((beforeSize - 2), H_STORAGE.values().size());

        ArrayList<User> after = new ArrayList<>(H_STORAGE.values());
        ArrayList<Integer> afterIds = new ArrayList<>();

        for (User u : after) {
            afterIds.add(u.getId());
        }
        assertFalse(afterIds.contains(idOne));
        assertFalse(afterIds.contains(idTwo));

        System.out.println("\n=========================================================================================");
    }
}
package com.llisovichok.servlets;

import com.llisovichok.lessons.clinic.Pet;
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
@Ignore
public class DeleteUserServletTest extends Mockito {

    //final static UserData USER_DATA = UserData.getInstance();
    final static JdbcStorage JDBC_STORAGE = JdbcStorage.getINSTANCE();

    @Test
    public void testDoGet() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        User userOne = new User("t", "t", "t", 12, new Pet("t", "t", 1));
        User userTwo = new User("t", "t", "t", 12, new Pet("t", "t", 2));


        int idOne = JDBC_STORAGE.add(userOne);
        int idTwo = JDBC_STORAGE.add(userTwo);

        ArrayList<User> before = new ArrayList<>(JDBC_STORAGE.values());
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
        assertEquals((beforeSize - 1), JDBC_STORAGE.values().size());

        when(request.getParameter("id")).thenReturn(String.valueOf(idTwo));
        new DeleteUserServlet().doGet(request, response);
        verify(request, atLeast(1)).getParameter("id");
        assertEquals((beforeSize - 2), JDBC_STORAGE.values().size());

        ArrayList<User> after = new ArrayList<>(JDBC_STORAGE.values());
        ArrayList<Integer> afterIds = new ArrayList<>();

        for (User u : after) {
            afterIds.add(u.getId());
        }
        assertFalse(afterIds.contains(idOne));
        assertFalse(afterIds.contains(idTwo));



        //USER_DATA.getUsers().clear();

    }
}
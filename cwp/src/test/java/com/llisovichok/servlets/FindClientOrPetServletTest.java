package com.llisovichok.servlets;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.User;
import com.llisovichok.storages.HibernateStorage;
import com.llisovichok.storages.JdbcStorage;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FindClientOrPetServletTest extends Mockito {

    //private static JdbcStorage JDBC_STORAGE = JdbcStorage.getINSTANCE();
    private final static HibernateStorage H_STORAGE = HibernateStorage.getInstance();

    @Test
    public void doPost() throws Exception {

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        RequestDispatcher reqDispatcher = mock(RequestDispatcher.class);


        when(req.getParameter("input")).thenReturn("1232123");
        when(req.getParameter("first_name")).thenReturn("first_name");
        when(req.getParameter("last_name")).thenReturn("last_name");
        when(req.getParameter("pet_name")).thenReturn(null);

        when(req.getRequestDispatcher("/views/user/FailedResultOfSearch.jsp")).thenReturn(reqDispatcher);
        doNothing().when(reqDispatcher).forward(req, resp);

        new FindClientOrPetServlet().doPost(req, resp);

        verify(req, atLeastOnce()).getRequestDispatcher("/views/user/FailedResultOfSearch.jsp");

    }
@Ignore@Test
    public void doPostTest2() throws Exception {

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        RequestDispatcher reqDispatcher = mock(RequestDispatcher.class);


        int id = H_STORAGE.addUser(new User("Test","test", "test", 1025, new Pet("test", "test", 2)));
        try {
            when(req.getParameter("input")).thenReturn("Test");
            when(req.getParameter("first_name")).thenReturn("first_name");
            when(req.getParameter("last_name")).thenReturn("last_name");
            when(req.getParameter("pet_name")).thenReturn("pet_name");

            when(req.getRequestDispatcher("/views/user/ResultOfSearching.jsp")).thenReturn(reqDispatcher);
            doNothing().when(reqDispatcher).forward(req, resp);

            new FindClientOrPetServlet().doPost(req, resp);

            verify(req, atLeastOnce()).getRequestDispatcher("/views/user/ResultOfSearching.jsp");

        } finally{
            H_STORAGE.removeUser(id);
        }
    }
}
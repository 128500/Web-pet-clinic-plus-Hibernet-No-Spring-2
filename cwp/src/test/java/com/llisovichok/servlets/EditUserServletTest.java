package com.llisovichok.servlets;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.Role;
import com.llisovichok.storages.HibernateStorage;
import com.llisovichok.storages.JdbcStorage;
import com.llisovichok.models.User;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;

/**
 * Created by ALEKSANDER KUDIN on 02.04.2017.
 */

public class EditUserServletTest extends Mockito {

    //final static UserData USER_DATA = UserData.getInstance();
    //final static JdbcStorage JDBC_STORAGE = JdbcStorage.getINSTANCE();
    private final  static HibernateStorage H_STORAGE = HibernateStorage.getInstance();


    @Test
    public void testDoPost() throws Exception {
        System.out.println("\n=========================================================================================");
        System.out.println("Testing  || " + this.getClass().getName() + " || testDoPost()");
        System.out.println("\n=========================================================================================");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        User user = new User("Check", "Check", "Check", 1245L, new Pet("Check", "check", 1245));
        user.setRole(new Role("user"));

        Integer id = null;
        try {
            id = H_STORAGE.addUser(user);

            when(request.getParameter("id")).thenReturn(String.valueOf(id));
            when(request.getParameter("client first name")).thenReturn("Altered");
            when(request.getParameter("client last name")).thenReturn("Altered");
            when(request.getParameter("address")).thenReturn("Altered");
            when(request.getParameter("phone number")).thenReturn("1111");
            when(request.getParameter("pet kind")).thenReturn("altered");
            when(request.getParameter("pet name")).thenReturn("Altered");
            when(request.getParameter("pet age")).thenReturn("1111");

            new EditUserServlet().doPost(request, response);

            verify(request, atLeastOnce()).getParameter("id");
            verify(request, atLeastOnce()).getParameter("client first name");
            verify(request, atLeastOnce()).getParameter("client last name");
            verify(request, atLeastOnce()).getParameter("address");
            verify(request, atLeastOnce()).getParameter("phone number");
            verify(request, atLeastOnce()).getParameter("pet age");
            verify(request, atLeastOnce()).getParameter("pet kind");
            verify(request, atLeastOnce()).getParameter("pet name");

            User altered = H_STORAGE.getUser(id);

            assertEquals("Altered", altered.getFirstName());
            assertEquals("Altered", altered.getLastName());
            assertEquals("Altered", altered.getAddress());
            assertEquals(1111L, altered.getPhoneNumber());
            assertEquals(1111, altered.getPet().getAge());
            assertEquals("altered", altered.getPet().getKind());
            assertEquals("Altered", altered.getPet().getName());
        } finally{
            if(id != null) H_STORAGE.removeUser(id);
        }

        System.out.println("\n=========================================================================================");
    }

    @Test
    public void testDoGet() throws Exception {

        System.out.println("\n=========================================================================================");
        System.out.println("Testing  || " + this.getClass().getName() + " || testDoGet()");
        System.out.println("\n=========================================================================================");

        User user = new User("Check", "Check", "Check", 1245L, new Pet("Check", "check", 1245));
        user.setRole(new Role("user"));
        Integer id = null;
        try {
            id = H_STORAGE.addUser(user);
            HttpServletRequest request = mock(HttpServletRequest.class);
            HttpServletResponse response = mock(HttpServletResponse.class);
            RequestDispatcher dispatcher = mock(RequestDispatcher.class);

            when(request.getParameter("id")).thenReturn(String.valueOf(id));
            when(request.getRequestDispatcher("/views/user/EditUser.jsp")).thenReturn(dispatcher);
            doNothing().when(dispatcher).forward(request, response);

            new EditUserServlet().doGet(request, response);

            verify(request, times(1)).getRequestDispatcher("/views/user/EditUser.jsp");
        } finally{
            if(id != null) H_STORAGE.removeUser(id);
        }
        System.out.println("\n=========================================================================================");
    }

}
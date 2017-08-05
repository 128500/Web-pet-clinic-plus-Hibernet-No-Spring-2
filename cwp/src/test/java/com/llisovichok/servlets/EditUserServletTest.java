package com.llisovichok.servlets;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.storages.JdbcStorage;
import com.llisovichok.models.User;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;

/**
 * Created by ALEKSANDER KUDIN on 02.04.2017.
 */
@Ignore
public class EditUserServletTest extends Mockito {

    //final static UserData USER_DATA = UserData.getInstance();
    final static JdbcStorage JDBC_STORAGE = JdbcStorage.getINSTANCE();


    @Test
    public void testDoPost() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        User user = new User("Test", "Test", "Test", 1245, new Pet("Test", "test", 1245));
        //USER_DATA.addUser(123456, user);
        //System.out.println(USER_DATA.getUser(123456).toString());
        int id = JDBC_STORAGE.addUser(user);

        when(request.getParameter("id")).thenReturn(String.valueOf(id));
        when(request.getParameter("client first name")).thenReturn("Altered");
        when(request.getParameter("client last name")).thenReturn("Altered");
        when(request.getParameter("address")).thenReturn("Altered");
        when(request.getParameter("phone number")).thenReturn("1111");
        when(request.getParameter("pet kind")).thenReturn("altered");
        when(request.getParameter("pet name")).thenReturn("Altered");
        when(request.getParameter("pet age")).thenReturn("1111");

        new EditUserServlet().doPost(request,response);

        verify(request, atLeastOnce()).getParameter("id");
        verify(request, atLeastOnce()).getParameter("client first name");
        verify(request, atLeastOnce()).getParameter("client last name");
        verify(request, atLeastOnce()).getParameter("address");
        verify(request, atLeastOnce()).getParameter("phone number");
        verify(request, atLeastOnce()).getParameter("pet age");
        verify(request, atLeastOnce()).getParameter("pet kind");
        verify(request, atLeastOnce()).getParameter("pet name");

        User altered  = JDBC_STORAGE.getUser(id);

        assertEquals("Altered", altered.getFirstName());
        assertEquals("Altered", altered.getLastName());
        assertEquals("Altered", altered.getAddress());
        assertEquals(1111, altered.getPhoneNumber());
        assertEquals(1111, altered.getPet().getAge());
        assertEquals("altered", altered.getPet().getKind());
        assertEquals("Altered", altered.getPet().getName());

        //System.out.println(USER_DATA.getUser(123456).toString());

        //USER_DATA.getUsers().clear();

        JDBC_STORAGE.removeUser(id);
    }

    @Ignore
    public void testDoGet() throws Exception {

    }

}
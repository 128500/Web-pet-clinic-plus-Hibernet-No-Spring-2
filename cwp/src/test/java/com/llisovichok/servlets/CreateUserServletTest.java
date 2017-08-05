package com.llisovichok.servlets;

import com.llisovichok.models.User;
import com.llisovichok.storages.HibernateStorage;
import com.llisovichok.storages.JdbcStorage;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ALEKSANDR KUDIN on 31.03.2017.
 */

public class CreateUserServletTest extends Mockito {

    //private JdbcStorage JDBC_STORAGE = JdbcStorage.getINSTANCE();
    private final static HibernateStorage H_STORAGE  = HibernateStorage.getInstance();

    @Test
    public void doPost() throws Exception {

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);


        //int previousSize = JDBC_STORAGE.values().size();
        int previousSize = H_STORAGE.values().size();

        when(req.getParameter("add")).thenReturn("add");
        when(req.getParameter("client first name")).thenReturn("fName");
        when(req.getParameter("client last name")).thenReturn("lName");
        when(req.getParameter("address")).thenReturn("address");
        when(req.getParameter("phone number")).thenReturn("102");
        when(req.getParameter("pet name")).thenReturn("pName");
        when(req.getParameter("pet kind")).thenReturn("kind");
        when(req.getParameter("pet age")).thenReturn("2");


        new CreateUserServlet().doPost(req, resp);

        ArrayList<User> users = new ArrayList<>(H_STORAGE.values());
        User user = users.get(users.size() - 1);
        int id = user.getId();

        try {
            verify(req, atLeast(1)).getParameter("add");
            verify(req, atLeast(1)).getParameter("client first name");
            verify(req, atLeast(1)).getParameter("client last name");
            verify(req, atLeast(1)).getParameter("address");
            verify(req, atLeast(1)).getParameter("phone number");
            verify(req, atLeast(1)).getParameter("pet name");
            verify(req, atLeast(1)).getParameter("pet kind");
            verify(req, atLeast(1)).getParameter("pet age");

            int currentSize = users.size();
            assertTrue(currentSize > previousSize);

            assertEquals("fName", user.getFirstName());
            assertEquals("lName", user.getLastName());
            assertEquals("address", user.getAddress());
            assertEquals(102, user.getPhoneNumber());
            assertEquals("pName", user.getPet().getName());
            assertEquals("kind", user.getPet().getKind());
            assertEquals(2, user.getPet().getAge());

        } finally{
            H_STORAGE.removeUser(id);
        }


        int previousSize2 = H_STORAGE.values().size();

        Map<String, String[]> requestMap = new LinkedHashMap<>();

        String[] fName = {"    "};  //will be trimmed
        requestMap.put("client first name", fName);

        String[] lName = {"test"};
        requestMap.put("client last name", lName);

        String[] address = {"test"};
        requestMap.put("address", address);

        String[] phoneNumber = {"000000"};
        requestMap.put("phone number", phoneNumber);

        String[] petName = {"test"};
        requestMap.put("pet name", petName);

        String[] petKind = {"test"};
        requestMap.put("pet kind", petKind);

        String[] petAge = {"00"};
        requestMap.put("pet age", petAge);

        String[] add = {"addUser"};
        requestMap.put("addUser", add);

        when(req.getParameterMap()).thenReturn(requestMap);

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        CreateUserServlet cus = new CreateUserServlet();

        when(req.getRequestDispatcher("/views/user/WrongParameter.jsp")).thenReturn(dispatcher);

        doNothing().when(dispatcher).forward(req, resp);

        assertTrue(!cus.checkRequestParameters(req).isEmpty());

        cus.doPost(req, resp);

        verify(req, atLeastOnce()).getParameterMap();


        int currentSize2 = H_STORAGE.values().size();

        assertTrue(currentSize2 == previousSize2);
    }


    @Test
    public void checkRequestParameters() throws Exception{

        HttpServletRequest req = mock(HttpServletRequest.class);

        Map<String, String[]> requestMap = new LinkedHashMap<>();

        String[] fName = {"    "};  //will be trimmed
        requestMap.put("client first name", fName);

        String[] lName = {""};
        requestMap.put("client last name", lName);

        String[] address = {""};
        requestMap.put("address", address);

        String[] phoneNumber = {""};
        requestMap.put("phone number", phoneNumber);

        String[] petName = {""};
        requestMap.put("pet name", petName);

        String[] petKind = {""};
        requestMap.put("pet kind", petKind);

        String[] petAge = {""};
        requestMap.put("pet age", petAge);

        String[] add = {"addUser"};
        requestMap.put("addUser", add);

        when(req.getParameterMap()).thenReturn(requestMap);

        ArrayList<String> result = new CreateUserServlet().checkRequestParameters(req);

        verify(req, atLeastOnce()).getParameterMap();

        assertTrue(result.size() == 7);

        assertEquals("CLIENT FIRST NAME", result.get(0));
        assertEquals("CLIENT LAST NAME", result.get(1));
        assertEquals("ADDRESS", result.get(2));
        assertEquals("PHONE NUMBER", result.get(3));
        assertEquals("PET NAME", result.get(4));
        assertEquals("PET KIND", result.get(5));
        assertEquals("PET AGE", result.get(6));
    }

    @Test
    public void createUser() throws Exception {

        HttpServletRequest req = mock(HttpServletRequest.class);

        when(req.getParameter("client first name")).thenReturn("fName");
        when(req.getParameter("client last name")).thenReturn("lName");
        when(req.getParameter("address")).thenReturn("address");
        when(req.getParameter("phone number")).thenReturn("102");
        when(req.getParameter("pet name")).thenReturn("pName");
        when(req.getParameter("pet kind")).thenReturn("kind");
        when(req.getParameter("pet age")).thenReturn("2");

        User user = new CreateUserServlet().createUser(req);

        verify(req, atLeast(1)).getParameter("client first name");
        verify(req, atLeast(1)).getParameter("client last name");
        verify(req, atLeast(1)).getParameter("address");
        verify(req, atLeast(1)).getParameter("phone number");
        verify(req, atLeast(1)).getParameter("pet name");
        verify(req, atLeast(1)).getParameter("pet kind");
        verify(req, atLeast(1)).getParameter("pet age");

        assertEquals("fName", user.getFirstName());
        assertEquals("lName", user.getLastName());
        assertEquals("address", user.getAddress());
        assertEquals(102, user.getPhoneNumber());
        assertEquals("pName", user.getPet().getName());
        assertEquals("kind", user.getPet().getKind());
        assertEquals(2, user.getPet().getAge());
    }
    @Ignore
    @Test
    public void createUserWithId() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);

        when(req.getParameter("client first name")).thenReturn("fName");
        when(req.getParameter("client last name")).thenReturn("lName");
        when(req.getParameter("address")).thenReturn("address");
        when(req.getParameter("phone number")).thenReturn("102");
        when(req.getParameter("pet name")).thenReturn("pName");
        when(req.getParameter("pet kind")).thenReturn("kind");
        when(req.getParameter("pet age")).thenReturn("2");

       // User user = new CreateUserServlet().createUserWithId(req, 25);

        verify(req, atLeast(1)).getParameter("client first name");
        verify(req, atLeast(1)).getParameter("client last name");
        verify(req, atLeast(1)).getParameter("address");
        verify(req, atLeast(1)).getParameter("phone number");
        verify(req, atLeast(1)).getParameter("pet name");
        verify(req, atLeast(1)).getParameter("pet kind");
        verify(req, atLeast(1)).getParameter("pet age");

        //assertEquals("fName", user.getFirstName());
        //assertEquals("lName", user.getLastName());
        //assertEquals("address", user.getAddress());
        //assertEquals(102, user.getPhoneNumber());
        //assertEquals("pName", user.getPet().getName());
        //assertEquals("kind", user.getPet().getKind());
        //assertEquals(2, user.getPet().getAge());
        //assertEquals(25, user.getId());
    }
    @Ignore
    @Test
    public void doGet() throws Exception {

    }
}
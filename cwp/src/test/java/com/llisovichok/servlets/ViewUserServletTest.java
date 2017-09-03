package com.llisovichok.servlets;

import com.llisovichok.models.User;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by KUDIN ALEKSANDR on 03.09.2017.
 */
public class ViewUserServletTest extends Mockito {

    @Test
    public void doGet() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getRequestDispatcher("/views/user/ViewUser.jsp")).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(req, resp);

        new ViewUserServlet().doGet(req, resp);

        verify(req, atLeastOnce()).setAttribute(eq("users"), anyCollectionOf(User.class));
        verify(req, atLeastOnce()).getRequestDispatcher("/views/user/ViewUser.jsp");
        verify(dispatcher, atLeastOnce()).forward(req, resp);
    }
}
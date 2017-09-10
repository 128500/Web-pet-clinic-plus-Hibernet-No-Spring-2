package com.llisovichok.servlets;

import com.llisovichok.helper.UserCreationHelper;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by KUDIN ALEKSANDR on 09.09.2017.
 */


public class ViewMessagesServletTest extends Mockito {

    private final static UserCreationHelper CREATION_HELPER = UserCreationHelper.getInstance();

    @Test
    public void doGet() throws Exception {
        System.out.println("\n=========================================================================================");
        System.out.println("Testing  || " + this.getClass().getName() + " || doGet()");
        System.out.println("\n=========================================================================================");

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        Integer userId = CREATION_HELPER.getUserId();
        when(req.getParameter("id")).thenReturn(String.valueOf(userId));
        when(req.getRequestDispatcher("/views/user/ViewMessages.jsp")).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(req, resp);

        new ViewMessagesServlet().doGet(req, resp);

        verify(req, atLeastOnce()).getRequestDispatcher("/views/user/ViewMessages.jsp");
    }


    @Test
    public void destroy() throws Exception {
        System.out.println("\n=========================================================================================");
        System.out.println("Testing  || " + this.getClass().getName() + " || destroy()");
        System.out.println("\n=========================================================================================");

        new ViewMessagesServlet().destroy();
    }
}
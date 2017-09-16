package com.llisovichok.servlets;

import com.llisovichok.test_helper.UserCreationHelper;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by KUDIN ALEKSANDR on 09.09.2017.
 */
public class ShowImageServletTest extends Mockito {

    private final static UserCreationHelper HELPER = UserCreationHelper.getInstance();
    @Test
    public void doGet() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        Integer petId = HELPER.getPetId();
        when(req.getPathInfo()).thenReturn("/" + petId + ".jpg");

        ServletContext sc = mock(ServletContext.class);
        ShowImageServlet shis = new ShowImageServlet(){
            public ServletContext getServletContext(){
                return sc;
            }
        };

        when(sc.getMimeType(anyString())).thenReturn(petId + ".jpg");
        doNothing().when(resp).setContentType(anyString());

        ServletOutputStream os = mock(ServletOutputStream.class);
        when(resp.getOutputStream()).thenReturn(os);
        doNothing().when(os).write(any());

        shis.doGet(req, resp);
        verify(resp, atLeastOnce()).setContentType(any());
        verify(resp, atLeastOnce()).setContentLength(anyInt());
        verify(resp, atLeastOnce()).getOutputStream();
        verify(os, atLeastOnce()).write(any());
    }
}
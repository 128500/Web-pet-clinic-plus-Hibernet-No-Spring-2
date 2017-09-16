package com.llisovichok.servlets;

import com.llisovichok.storages.HibernateStorage;
import com.llisovichok.test_helper.UserCreationHelper;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by KUDIN ALEKSANDR on 27.08.2017.
 */

public class AddInfoServletTest extends Mockito {

    private final static HibernateStorage H_STORAGE = HibernateStorage.getInstance();
    private final static UserCreationHelper HELPER = UserCreationHelper.getInstance();
    private Integer userId = HELPER.getUserId();
    private Integer petId = HELPER.getPetId();

    /**
     * Creates an InputStream from an external source predetermined with the URL
     * The external source is needed for passing tests running by Travis CI (https://travis-ci.org)
     * @param urlPath the URL path
     * @return InputStream of image bytes
     */
    private static InputStream getInputStream(final String urlPath) {
        InputStream is = null;
        try{
            URL url = new URL(urlPath);
            is = url.openStream();
            return is;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Couldn't  get image bytes");
    }


    @Test
    public void doGet() throws Exception {
        System.out.println("\n=========================================================================================");
        System.out.println("Testing  || " + this.getClass().getName() + " || testDoGet()");
        System.out.println("\n=========================================================================================");

        HttpServletRequest reqMock = mock(HttpServletRequest.class);
        HttpServletResponse respMock = mock(HttpServletResponse.class);

        /*first redirecting to NoClientWithSuchId page when user with the given id is not found*/
        when(reqMock.getParameter("id")).thenReturn("1258012");
        when(reqMock.getContextPath()).thenReturn("");
        new AddInfoServlet().doGet(reqMock, respMock);
        verify(respMock, atLeastOnce()).sendRedirect(String.format("%s%s", reqMock.getContextPath(), "/views/user/NoClientWithSuchId.jsp"));

        /*second if everything is OK*/
        when(reqMock.getParameter("id")).thenReturn(String.valueOf(userId));
        RequestDispatcher dispMock = mock(RequestDispatcher.class);
        when(reqMock.getRequestDispatcher("/views/user/AddInfo.jsp")).thenReturn(dispMock);
        doNothing().when(dispMock).forward(reqMock, respMock);
        new AddInfoServlet().doGet(reqMock, respMock);
        verify(reqMock, times(1)).getRequestDispatcher("/views/user/AddInfo.jsp");

        System.out.println("\n###########  DONE ############\n");
    }

    @Test
    public void doPost() throws Exception {
        System.out.println("\n=========================================================================================");
        System.out.println("Testing  || " + this.getClass().getName() + " || testDoPost()");
        System.out.println("\n=========================================================================================");

        HttpServletRequest reqMock = mock(HttpServletRequest.class);
        HttpServletResponse respMock = mock(HttpServletResponse.class);
        Part partMock = mock(Part.class);

        when(reqMock.getParameter("pet_id")).thenReturn(String.valueOf(petId));
        when(reqMock.getPart("photo")).thenReturn(partMock);
        RequestDispatcher dispMock = mock(RequestDispatcher.class);
        when(reqMock.getRequestDispatcher("/views/user/SuccessInAddingPhoto.jsp")).thenReturn(dispMock);
        doNothing().when(dispMock).forward(reqMock, respMock);

        try(InputStream is = getInputStream("http://www.avajava.com/images/avajavalogo.jpg")){
            when(partMock.getInputStream()).thenReturn(is);
            new AddInfoServlet().doPost(reqMock, respMock);
        }
        assertTrue(H_STORAGE.getPetById(petId).getPhoto().getImage().length > 0);

        System.out.println("\n###########  DONE ############\n");
    }
}
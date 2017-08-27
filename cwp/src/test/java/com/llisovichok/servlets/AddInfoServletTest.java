package com.llisovichok.servlets;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.Role;
import com.llisovichok.models.User;
import com.llisovichok.storages.HibernateStorage;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by KUDIN ALEKSANDR on 27.08.2017.
 */
public class AddInfoServletTest extends Mockito {

    private final static HibernateStorage H_STORAGE = HibernateStorage.getInstance();
    private Integer userId;
    private Integer petId;

    public AddInfoServletTest(){
        User user = new User("test", "test", "test", 125L,
                new Pet("test", "test", 1));
        user.setRole(new Role("user"));
        this.userId = H_STORAGE.addUser(user);
        petId = H_STORAGE.getUser(userId).getPet().getId();
    }

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
        verify(reqMock.getRequestDispatcher("/views/user/AddInfo.jsp"));
    }

    @Test
    public void doPost() throws Exception {
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
    }


}
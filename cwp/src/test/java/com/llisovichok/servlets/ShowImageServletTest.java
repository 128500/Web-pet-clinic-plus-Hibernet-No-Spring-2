package com.llisovichok.servlets;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.lessons.clinic.PetPhoto;
import com.llisovichok.storages.HibernateStorage;
import com.llisovichok.storages.UserCreationHelper;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

/**
 * Created by KUDIN ALEKSANDR on 09.09.2017.
 */
public class ShowImageServletTest extends Mockito {

    //private final static UserCreationHelper HELPER = UserCreationHelper.getInstance();

    @Test
    public void doGet() throws Exception {
        HibernateStorage storage = mock(HibernateStorage.class);
        HibernateStorage.setInstance(storage);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getPathInfo()).thenReturn("/1.jpg");

        Pet pet = mock(Pet.class);
        when(storage.getPetById(anyInt())).thenReturn(pet);

        byte[] photoBytes = new byte[] { (byte)0xe0, 0x4f, (byte)0xd0, 0x20,  (byte)0x9d };

        PetPhoto photo = mock(PetPhoto.class);
        when(pet.getPhoto()).thenReturn(photo);
        when(photo.getImage()).thenReturn(photoBytes);

        ShowImageServlet shis = spy(ShowImageServlet.class);
        ServletContext sc = mock(ServletContext.class);
        when(shis.getServletContext()).thenReturn(sc);
        when(sc.getMimeType(anyString())).thenReturn("1.jpg");
        doNothing().when(resp).setContentType(anyString());
        shis.doGet(req, resp);

        //verify(resp, atLeastOnce()).setContentType(any());
        verify(resp, atLeastOnce()).setContentLength(anyInt());
        verify(resp, atLeastOnce()).getOutputStream().write(photoBytes);
    }
}
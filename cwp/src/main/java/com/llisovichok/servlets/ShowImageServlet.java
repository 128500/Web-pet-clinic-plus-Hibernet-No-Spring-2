package com.llisovichok.servlets;


import com.llisovichok.storages.HibernateStorage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by KUDIN ALEKSANDR on 25.08.2017.
 */
public class ShowImageServlet extends HttpServlet {

    private final static HibernateStorage HIBERNATE_STORAGE  = HibernateStorage.getInstance();

    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        String imageName = req.getPathInfo().substring(1);
        String[] tokens = imageName.split("\\.");
        byte[] imageBytes = HIBERNATE_STORAGE.getPetById(Integer.parseInt(tokens[0])).getPhoto().getImage();

        try {
            if(imageBytes != null && imageBytes.length > 0) {
                resp.setContentType(getServletContext().getMimeType(imageName));
                resp.setContentLength(imageBytes.length);
                resp.getOutputStream().write(imageBytes);
            }
            else resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch  (IOException e){
            e.printStackTrace();
        }
    }
}

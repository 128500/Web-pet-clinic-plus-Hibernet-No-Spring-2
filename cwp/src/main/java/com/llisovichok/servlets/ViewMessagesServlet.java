package com.llisovichok.servlets;

import com.llisovichok.models.User;
import com.llisovichok.storages.HibernateStorage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * Created by ALEKSANDR KUDIN on 26.08.2017.
 */

public class ViewMessagesServlet extends HttpServlet{

    private final static HibernateStorage HIBERNATE_STORAGE = HibernateStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = HIBERNATE_STORAGE.getUser(Integer.valueOf(req.getParameter("id")));
        req.setAttribute("user", user.getMessages());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/ViewMessages.jsp");
        dispatcher.forward(req, resp);
    }


    @Override
    public void destroy(){
        super.destroy();
        HIBERNATE_STORAGE.close();
    }
}

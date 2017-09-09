package com.llisovichok.servlets;

import com.llisovichok.storages.HibernateStorage;
import com.llisovichok.storages.JdbcStorage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ALEKSANDER KUDIN on 23.03.2017.
 * Version 1.0
 */
public class ViewUserServlet extends HttpServlet {


    private final static HibernateStorage HIBERNATE_STORAGE = HibernateStorage.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", HIBERNATE_STORAGE.values());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/ViewUser.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    public void destroy(){
        super.destroy();
    }

}

package com.llisovichok.servlets;

import com.llisovichok.models.User;
import com.llisovichok.storages.HibernateStorage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by KUDIN ALEKSANDR on 11.09.2017.
 */
public class SendMessageServlet extends HttpServlet {

    private static final HibernateStorage HIBERNATE_STORAGE = new HibernateStorage();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = HIBERNATE_STORAGE.getUser(Integer.valueOf(req.getParameter("id")));
        req.setAttribute("user", user);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/SendMessage.jsp");
        dispatcher.forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Integer id = Integer.valueOf(req.getParameter("id"));
        String message = req.getParameter("message");
        HIBERNATE_STORAGE.addMessage(id, message);
        resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/user/view/"));
    }

    @Override
    public void destroy(){
        super.destroy();
    }
}

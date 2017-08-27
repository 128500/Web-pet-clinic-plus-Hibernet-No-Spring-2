package com.llisovichok.servlets;

import com.llisovichok.models.User;
import com.llisovichok.storages.HibernateStorage;
import com.llisovichok.storages.JdbcStorage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * TODO addUser method to check the input letters
 * Created by ALEKSANDR KUDIN on 25.04.2017.
 */
public class FindClientOrPetServlet extends HttpServlet {

   // JdbcStorage JDBC_STORAGE = JdbcStorage.getINSTANCE();
    private final static HibernateStorage HIBERNATE_STORAGE = HibernateStorage.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //input must be more than three letters!!!?
        req.setCharacterEncoding("UTF-8");

        String input = req.getParameter("input");
        boolean lookInFirstName = isNotNull(req.getParameter("first_name"));
        boolean lookInLastName = isNotNull(req.getParameter("last_name"));
        boolean lookInPetName = isNotNull(req.getParameter("pet_name"));
        //Collection<User> result = JDBC_STORAGE.findUsers(input, lookInFirstName, lookInLastName, lookInPetName);
        Collection<User> result = HIBERNATE_STORAGE.findUsers(input, lookInFirstName, lookInLastName, lookInPetName);
        if(result.isEmpty()){
            RequestDispatcher rd = req.getRequestDispatcher("/views/user/FailedResultOfSearch.jsp");
            rd.forward(req, resp);
        }
        else {
            req.setAttribute("users", result);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/ResultOfSearching.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    public void destroy(){
        super.destroy();
    }

    private boolean isNotNull(Object o){
        return o != null;
    }
}

package com.llisovichok.servlets;

import com.llisovichok.lessons.clinic.Pet;
import com.llisovichok.models.User;
import com.llisovichok.storages.HibernateStorage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ALEKSANDR KUDIN on 23.03.2017.
 */
public class CreateUserServlet extends HttpServlet {

    //final static UserData USER_DATA = UserData.getInstance();

    //final static JdbcStorage JDBC_STORAGE = JdbcStorage.getINSTANCE();

    private final static HibernateStorage HIBERNATE_STORAGE = HibernateStorage.getInstance();

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (checkRequestParameters(req).isEmpty()){
            doAdd(req);
            resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/user/view/"));
        }
        else {
            req.setAttribute("wrongParams", checkRequestParameters(req));
            RequestDispatcher dispatcher = req.getRequestDispatcher( "/views/user/WrongParameter.jsp");
            dispatcher.forward(req, resp);
        }
    }

    ArrayList<String> checkRequestParameters(HttpServletRequest req){
        Map<String,String[]> parameters = req.getParameterMap();
        ArrayList<String> wrongParameters =  new ArrayList<>();

        for(Map.Entry<String,String[]> entry : parameters.entrySet()){

            int length = entry.getValue().length;

            for(int i = 0; i < length; i++){
                if(!isNotNull(entry.getValue()[i]) || !isNotEmpty(entry.getValue()[i].trim())) wrongParameters.add(entry.getKey().toUpperCase());
            }
        }
        return wrongParameters;
    }

    private void doAdd(HttpServletRequest req)throws IOException{
        if(req.getParameter("add") != null) {
            //User user = createUser(req);
            //System.out.println(user.toString());
            //USER_DATA.add(Integer.valueOf(user.getId()), user);
            User user = createUser(req);
            //JDBC_STORAGE.add(user);
            HIBERNATE_STORAGE.add(user);
        }
    }

    User createUser(HttpServletRequest req)throws IOException{

        String clientFirstName = req.getParameter("client first name");
        String clientLastName = req.getParameter("client last name");
        String clientAddress = req.getParameter("address");
        long  clientPhoneNumber = Long.parseLong(req.getParameter("phone number"));

        //Pet pet = createChosenPet(req);
        //return new User(clientFirstName, clientLastName, clientAddress, clientPhoneNumber, pet);
        Pet hiberPet= createChosenPet(req);
        return new User(clientFirstName, clientLastName, clientAddress,
                clientPhoneNumber, hiberPet);
    }

    /**User createUserWithId(HttpServletRequest req, Integer id)throws IOException{
        User user = createUser(req);
        user.setId(id);
        return user;
    }*/

    private Pet createChosenPet(HttpServletRequest req){

        String petKind = req.getParameter("pet kind");
        String petName = req.getParameter("pet name");
        int petAge = Integer.parseInt(req.getParameter("pet age"));

        //return new Pet(petName, petKind, petAge);
        return new Pet(petName, petKind, petAge);
    }

    private boolean isNotNull(Object o){
        return o != null;
    }

    private boolean isNotEmpty(String s){
        return !s.equals("");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.setAttribute("users", this.USER_DATA.values());
        //req.setAttribute("users", JDBC_STORAGE.values());
        req.setAttribute("users", HIBERNATE_STORAGE.values());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/ViewUser.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    public void destroy(){
        super.destroy();
        //JDBC_STORAGE.close();
    }
}

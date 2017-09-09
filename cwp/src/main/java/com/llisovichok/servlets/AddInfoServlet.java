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
import java.io.*;

/**
 * TODO
 * Created by ALEKSANDR KUDIN on 22.04.2017.
 */

@WebServlet("/upload")
@MultipartConfig

public class AddInfoServlet extends HttpServlet{

    //final static JdbcStorage JDBC_STORAGE = JdbcStorage.getINSTANCE();
    private final static HibernateStorage HIBERNATE_STORAGE = HibernateStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.setAttribute("user", JDBC_STORAGE.getUser(Integer.valueOf(req.getParameter("id"))));
        User user = HIBERNATE_STORAGE.getUser(Integer.valueOf(req.getParameter("id")));
        if (user.getId() == null){
            resp.sendRedirect(String.format("%s%s", req.getContextPath(), "/views/user/NoClientWithSuchId.jsp"));
        }
        else {
            req.setAttribute("user", user);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/AddInfo.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String idParam = req.getParameter("pet_id");
        Integer id = Integer.valueOf(idParam);
        try{
            Part filePart = req.getPart("photo");
            byte[] data = new byte[1024];
            int nRead;

            try(InputStream fileContent = filePart.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                while ((nRead = fileContent.read(data, 0, data.length)) != -1) {
                    baos.write(data, 0, nRead);
                }
                baos.flush();
                //int byteArraySize = baos.size();

                byte[] buffer = baos.toByteArray();
                //ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                //JDBC_STORAGE.addPhoto(id, bais, byteArraySize);
                //HIBERNATE_STORAGE.addPhoto(id, bais, byteArraySize);
                HIBERNATE_STORAGE.addPhotoWithHibernate(id, buffer);

                RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/SuccessInAddingPhoto.jsp");
                dispatcher.forward(req, resp);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void destroy(){
        super.destroy();
    }
}

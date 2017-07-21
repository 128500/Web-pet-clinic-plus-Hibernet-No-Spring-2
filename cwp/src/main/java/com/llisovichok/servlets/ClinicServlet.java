package com.llisovichok.servlets;

import com.llisovichok.lessons.clinic.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ClinicServlet extends HttpServlet {

    private final List<Client> clients = new CopyOnWriteArrayList<Client>();
    private final List<Client> foundClients = new CopyOnWriteArrayList<Client>();
    private boolean flag = true;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String content;
        if(flag) {
            content = this.viewPets();
        }
        else{
            content = this.viewClient();
            flag = true;
            foundClients.clear();
        }
        writer.append(
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<title>Clinic Pet</title>" +
                        "<meta charset='UTF-8'"+
                        "</head>" +
                        "<body>" +
                        getAddForm(req)+
                        "<br><br>"+
                        getSearchForm(req)+
                        "<br><br>"+
                        getSearchByPertNameForm(req)+
                        "<br><br>"+
                        getShowClientsForm(req)+
                        "<br><br>"+
                        content+
                        "</body>" +
                        "</html>"
        );
        writer.flush();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doAdd(req);
        doSearch(req);
        doSearchByPartName(req);
        doShowAllClients(req);
        doGet(req, resp);
    }

    private void doAdd(HttpServletRequest req)throws IOException{
        if(req.getParameter("add") != null) {
        Client client = createClient(req);
        this.clients.add(client);
        }
    }

    private Client createClient(HttpServletRequest req)throws IOException{
        String petKind = req.getParameter("pet kind");
        String petName = req.getParameter("pet name");
        String clientName = req.getParameter("client name");

        Client client;
        if(isNotEmpty(clientName) && isNotNull(clientName) && isNotNull(petKind) && isNotEmpty(petKind) && isNotNull(petName) && isNotEmpty(petName) ) {
            client = new Client(req.getParameter("client name"), createChosenPet(req));
        }
        else client = new Client("unknown", new Pet("unknown"));

        return client;
    }

    private Pet createChosenPet(HttpServletRequest req){

        String petKind = req.getParameter("pet kind");
        String petName = req.getParameter("pet name");

        Pet pet;

        if(petKind.equals("dog")) pet = new Dog(petName);
        else if(petKind.equals("cat")) pet = new Cat(petName);
        else if(petKind.equals("hamster")) pet = new Hamster(petName);
        else pet = new Pet(petName);

        return pet;
    }

    private boolean isNotNull(Object o){
        return o != null;
    }

    private boolean isNotEmpty(String s){
        return !s.equals("");
    }

    private void doSearch(HttpServletRequest req){
        String findClient = req.getParameter("find client");
        if(req.getParameter("search") != null){
            flag = false;
            if (!clients.isEmpty()) {
                for (Client c : clients) {
                    if (findClient.equals(c.getFirstName())) foundClients.add(c);
                }
            }
        }
    }

    private void doSearchByPartName(HttpServletRequest req){
        String findCoincidence = req.getParameter("find coincidence");
        if(req.getParameter("partly search") != null){
            flag = false;
            if (!clients.isEmpty()) {
                for (Client c : clients) {
                    if (c.getFirstName().toLowerCase().contains(findCoincidence.toLowerCase())) foundClients.add(c);
                }
            }
        }
    }

    private void doShowAllClients(HttpServletRequest req){
        if(req.getParameter("show all") != null){
            flag = true;
        }
    }

    private String getAddForm(HttpServletRequest req){
        StringBuilder sbAdd = new StringBuilder();
        sbAdd.append(
                    "   <form action = '" + req.getContextPath() + "/' method  = 'post'>"+
                            "<select required size='1' name = 'pet kind'>"+
                        "   <option disabled>Choose pet</option>"+
                        "   <option value='pet'>Pet</option>"+
                        "   <option value='dog'>Dog</option>"+
                        "   <option value='cat'>Cat</option>"+
                        "   <option value='hamster'>Hamster</option>"+
                        "   </select>"+
                        "   Pet name : <input required type = 'text' name = 'pet name'>" +
                        "   <br><br>"+
                        "   Client name : <input required type = 'text' name = 'client name'>" +
                        "   <input type = 'reset' value = 'Reset all'>" +
                        "   <input type = 'submit' name = 'add' value = 'Add'>" +
                        "   </form>"
        );
        return sbAdd.toString();
    }

    private String getSearchForm(HttpServletRequest req){
        StringBuilder sbSearch = new StringBuilder();
        sbSearch.append(
                "   <form action = '" + req.getContextPath() + "/' method  = 'post'>" +
                        "   Find client : <input required type = 'text' name = 'find client'>" +
                        "   <input type = 'submit' name = 'search' value = 'Search'>" +
                        "   </form>"
        );
        return sbSearch.toString();
    }

    private String getSearchByPertNameForm(HttpServletRequest req){
        StringBuilder sbPartlySearch = new StringBuilder();
        sbPartlySearch.append(
                "   <form action = '" + req.getContextPath() + "/' method  = 'post'>" +
                        "   Find client by part of the name : <input required type = 'text' name = 'find coincidence'>" +
                        "   <input type = 'submit' name = 'partly search' value = 'Search by part name'>" +
                        "   </form>"
        );
        return sbPartlySearch.toString();
    }

    private String getShowClientsForm(HttpServletRequest req){
        StringBuilder sbShowAllClients = new StringBuilder();
        sbShowAllClients.append(
                "   <form action = '" + req.getContextPath() + "/' method  = 'post'>" +
                        "   Show all clients <input type = 'submit' name = 'show all' value = 'Show all clients'>" +
                        "   </form>"
        );
        return sbShowAllClients.toString();
    }

    private String viewPets() {
        StringBuilder sb = new StringBuilder();
        sb.append("<p>Clients list</p>");
        sb.append("<table width='50%' border='1'>");
        for (Client c : clients) {
            sb.append("<tr><td style='border : 1 px solid black'>").append(c.getFirstName()).append("</td>");
            sb.append("<td style='border : 1 px solid black'>").append(c.getPet().getKind()).append("</td>");
            sb.append("<td style='border : 1 px solid black'>").append(c.getPet().getName()).append("</td></tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

    private String viewClient(){

        StringBuilder sb = new StringBuilder();
        sb.append("<p>Client info</p>");
        sb.append("<table width='50%' border='1'>");
        for (Client c : foundClients) {
            sb.append("<tr><td style='border : 1 px solid black'>").append(c.getFirstName()).append("</td>");
            sb.append("<td style='border : 1 px solid black'>").append(c.getPet().getKind()).append("</td>");
            sb.append("<td style='border : 1 px solid black'>").append(c.getPet().getName()).append("</td></tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }
}

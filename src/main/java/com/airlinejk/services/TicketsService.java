package com.airlinejk.services;

import com.airlinejk.business_logic.Tickets;
import com.airlinejk.daos.TicketsDao;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kevin Flores
 */
public class TicketsService extends HttpServlet {

    private final TicketsDao dao = new TicketsDao();
    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        switch(request.getServletPath()){
            case "/tickets/get" : get(request, response); break;
            case "/tickets/get/flight" : getByFlight(request, response); break;
            case "/tickets/add" : insert(request, response); break;
            case "/tickets/update" : update(request, response); break;
            case "/tickets/all" : getAll(response); break;
            case "/tickets/delete" : delete(request, response); break;
        }   
    }
    
    private void insert(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Tickets ticket = gson.fromJson(reader, Tickets.class);
        dao.insert(ticket);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Tickets ticket = gson.fromJson(reader, Tickets.class);
        dao.update(ticket);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Integer id = Integer.parseInt(request.getParameter("id"));
        Tickets ticket = dao.get(id);
        String allJson = gson.toJson(ticket);
        PrintWriter out = response.getWriter();
        out.print(allJson);
        out.flush();
    }
    
    private void getByFlight(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Integer id = Integer.parseInt(request.getParameter("id"));
        List<Tickets> ticket = dao.allFlightTickets(id);
        String allJson = gson.toJson(ticket);
        PrintWriter out = response.getWriter();
        out.print(allJson);
        out.flush();
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Integer id = Integer.parseInt(request.getParameter("id"));
        dao.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void getAll(HttpServletResponse response) throws IOException{
        List<Tickets> all = dao.all();
        String allJson = gson.toJson(all);
        PrintWriter out = response.getWriter();
        out.print(allJson);
        out.flush();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
}

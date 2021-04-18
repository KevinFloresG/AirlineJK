package com.airlinejk.services;

import com.airlinejk.business_logic.Reservations;
import com.airlinejk.daos.ReservationsDao;
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
public class ReservationsService extends HttpServlet {

   private final ReservationsDao dao = new ReservationsDao();
    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        switch(request.getServletPath()){
            case "/reservations/get" : get(request, response); break;
            case "/reservations/get/user" : getByUser(request, response); break;
            case "/reservations/get/flight" : getByFlight(request, response); break;
            case "/reservations/add" : insert(request, response); break;
            case "/reservations/update" : update(request, response); break;
            case "/reservations/all" : getAll(response); break; 
            case "/reservations/delete" : delete(request, response); break;
        }   
    }
    
    private void insert(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Reservations rsvt = gson.fromJson(reader, Reservations.class);
        dao.insert(rsvt);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Reservations rsvt = gson.fromJson(reader, Reservations.class);
        dao.update(rsvt);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Integer id = Integer.parseInt(request.getParameter("id"));
        Reservations rsvt = dao.get(id);
        String allJson = gson.toJson(rsvt);
        PrintWriter out = response.getWriter();
        out.print(allJson);
        out.flush();
    }
    
    private void getByUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String id = request.getParameter("username");
        List<Reservations> rsvt = dao.allUserRes(id);
        String allJson = gson.toJson(rsvt);
        PrintWriter out = response.getWriter();
        out.print(allJson);
        out.flush();
    }
    
    private void getByFlight(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String id = request.getParameter("id");
        List<Reservations> rsvt = dao.allFlightRes(id);
        String allJson = gson.toJson(rsvt);
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
        List<Reservations> all = dao.all();
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

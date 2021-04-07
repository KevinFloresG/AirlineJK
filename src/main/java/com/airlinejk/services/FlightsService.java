package com.airlinejk.services;

import com.airlinejk.business_logic.Flights;
import com.airlinejk.daos.FlightsDao;
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
public class FlightsService extends HttpServlet {

    private final FlightsDao dao = new FlightsDao();
    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        switch(request.getServletPath()){     
            case "/flights/get" : get(request, response); break;
            case "/flights/get/route" : getByRoute(request, response); break;
            case "/flights/get/routediscount" : getByRouteWithDiscount(request, response); break;
            case "/flights/add" : insert(request, response); break;
            case "/flights/update/info" : updateInfo(request, response); break;
            case "/flights/update/seats" : updateSeats(request, response); break;
            case "/flights/all" : getAll(response); break;
            case "/flights/all/discount" : getAllWithDiscount(response); break;
            case "/flights/delete" : delete(request, response); break;
        }   
    }
    
    private void insert(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Flights flight = gson.fromJson(reader, Flights.class);
        dao.insert(flight);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void updateInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Flights flight = gson.fromJson(reader, Flights.class);
        dao.updateFlightInfo(flight);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void updateSeats(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Flights flight = gson.fromJson(reader, Flights.class);
        dao.updateFlightSeats(flight);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Integer id = Integer.parseInt(request.getParameter("id"));
        Flights flight = dao.get(id);
        String allJson = gson.toJson(flight);
        PrintWriter out = response.getWriter();
        out.print(allJson);
        out.flush();
    }
    
    private void getByRoute(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String route = request.getParameter("id");
        List<Flights> flights = dao.searchByRoute(route);
        String json = gson.toJson(flights);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
    
    private void getByRouteWithDiscount(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
        String route = request.getParameter("id");
        List<Flights> flights = dao.searchByRouteWithDiscount(route);
        String json = gson.toJson(flights);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Integer id = Integer.parseInt(request.getParameter("id"));
        dao.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void getAll(HttpServletResponse response) throws IOException{
        List<Flights> all = dao.all();
        String allJson = gson.toJson(all);
        PrintWriter out = response.getWriter();
        out.print(allJson);
        out.flush();
    }
    
    private void getAllWithDiscount(HttpServletResponse response) throws IOException{
        List<Flights> all = dao.allDiscount();
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

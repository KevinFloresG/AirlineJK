package com.airlinejk.services;

import com.airlinejk.business_logic.Airplanes;
import com.airlinejk.daos.AirplanesDao;
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
public class AirplanesService extends HttpServlet {

    private final AirplanesDao dao = new AirplanesDao();
    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        switch(request.getServletPath()){
            case "/airplanes/get" : get(request, response); break;
            case "/airplanes/add" : insert(request, response); break;
            case "/airplanes/update" : update(request, response); break;
            case "/airplanes/all" : getAll(response); break;
            case "/airplanes/delete" : delete(request, response); break;
        }   
    }
    
    private void insert(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Airplanes airplane = gson.fromJson(reader, Airplanes.class);
        dao.insert(airplane);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Airplanes airplane = gson.fromJson(reader, Airplanes.class);
        dao.update(airplane);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String id = request.getParameter("id");
        Airplanes airplane = dao.get(id);
        String allJson = gson.toJson(airplane);
        PrintWriter out = response.getWriter();
        out.print(allJson);
        out.flush();
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String id = request.getParameter("id");
        dao.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void getAll(HttpServletResponse response) throws IOException{
        List<Airplanes> all = dao.all();
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

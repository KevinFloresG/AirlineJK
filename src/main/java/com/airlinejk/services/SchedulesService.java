package com.airlinejk.services;

import com.airlinejk.business_logic.Schedules;
import com.airlinejk.daos.SchedulesDao;
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
public class SchedulesService extends HttpServlet {

    private final SchedulesDao dao = new SchedulesDao();
    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        switch(request.getServletPath()){
            case "/schedules/get" : get(request, response); break;
            case "/schedules/add" : insert(request, response); break;
            case "/schedules/update" : update(request, response); break;
            case "/schedules/all" : getAll(response); break;
            case "/schedules/delete" : delete(request, response); break;
        }   
    }
    
    private void insert(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Schedules schedule = gson.fromJson(reader, Schedules.class);
        dao.insert(schedule);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Schedules schedule = gson.fromJson(reader, Schedules.class);
        dao.update(schedule);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Integer id = Integer.parseInt(request.getParameter("id"));
        Schedules schedule = dao.get(id);
        String allJson = gson.toJson(schedule);
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
        List<Schedules> all = dao.all();
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

package com.airlinejk.services;

import com.airlinejk.business_logic.Routes;
import com.airlinejk.daos.RoutesDao;
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
public class RoutesService extends HttpServlet {

   private final RoutesDao dao = new RoutesDao();
    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        switch(request.getServletPath()){
            case "/routes/get" : get(request, response); break;
            case "/routes/get/origin" : getO(request, response); break;
            case "/routes/get/destination" : getD(request, response); break;
            case "/routes/get/origin_destination" : getOD(request, response); break;
            case "/routes/get/origin_destination_like" : getODL(request, response); break;
            case "/routes/add" : insert(request, response); break;
            case "/routes/update" : update(request, response); break;
            case "/routes/all" : getAll(response); break;
            case "/routes/delete" : delete(request, response); break;
        }   
    }
    
    private void insert(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Routes route = gson.fromJson(reader, Routes.class);
        dao.insert(route);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Routes route = gson.fromJson(reader, Routes.class);
        dao.update(route);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String id = request.getParameter("id");
        Routes route = dao.get(id);
        String allJson = gson.toJson(route);
        PrintWriter out = response.getWriter();
        out.print(allJson);
        out.flush();
    }
    
    private void getO(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String id = request.getParameter("id");
        List<Routes> routes = dao.searchByOrigin(id);
        String json = gson.toJson(routes);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
    
    private void getD(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String id = request.getParameter("id");
        List<Routes> routes = dao.searchByDestination(id);
        String json = gson.toJson(routes);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
    
    private void getOD(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String origin = request.getParameter("ori");
        String destination = request.getParameter("des");
        List<Routes> routes = dao.searchByOriDes(origin,destination);
        String json = gson.toJson(routes);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
    
    private void getODL(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String origin = request.getParameter("ori");
        String destination = request.getParameter("des");
        List<Routes> routes = dao.searchByOriDesWithLike(origin,destination);
        String json = gson.toJson(routes);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String id = request.getParameter("id");
        dao.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void getAll(HttpServletResponse response) throws IOException{
        List<Routes> all = dao.all();
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

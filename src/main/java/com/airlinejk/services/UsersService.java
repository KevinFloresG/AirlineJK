package com.airlinejk.services;

import com.airlinejk.business_logic.Userss;
import com.airlinejk.daos.UsersDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class UsersService extends HttpServlet {

    private final UsersDao dao = new UsersDao();
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        switch(request.getServletPath()){
            case "/users/get" : get(request, response); break;
            case "/users/flight" : getFromFlight(request, response); break;
            case "/users/login" : login(request, response); break;
            case "/users/add" : insert(request, response); break;
            case "/users/update/info" : updateInfo(request, response); break;
            case "/users/update/pass" : updatePass(request, response); break;
            case "/users/all" : getAll(response); break;
            case "/users/delete" : delete(request, response); break;
        }   
    }
    
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Userss user = gson.fromJson(reader, Userss.class);
        Userss userReal = dao.get(user.getUsername());
        PrintWriter out = response.getWriter();
        if(user.getPassword().equals(userReal.getPassword())){
            out.print(true);
        }else{ out.print(false); }
        out.flush();
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void insert(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Userss user = gson.fromJson(reader, Userss.class);
        dao.insert(user);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void updateInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Userss user = gson.fromJson(reader, Userss.class);
        dao.updateUserInfo(user);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void updatePass(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Userss user = gson.fromJson(reader, Userss.class);
        dao.updateUserPass(user);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String id = request.getParameter("id");
        Userss user = dao.get(id);
        String allJson = gson.toJson(user);
        PrintWriter out = response.getWriter();
        out.print(allJson);
        out.flush();
    }
    
    private void getFromFlight(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String id = request.getParameter("id");
        int fId = Integer.parseInt(id);
        List<Userss> user = dao.searchByFlight(fId);
        String allJson = gson.toJson(user);
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
        List<Userss> all = dao.all();
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

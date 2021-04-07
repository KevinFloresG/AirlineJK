package com.airlinejk.services;

import com.airlinejk.business_logic.Paymenttypes;
import com.airlinejk.daos.PaymentTypesDao;
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
public class PayTypesService extends HttpServlet {

    private final PaymentTypesDao dao = new PaymentTypesDao();
    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        switch(request.getServletPath()){
            case "/paytypes/get" : get(request, response); break;
            case "/paytypes/add" : insert(request, response); break;
            case "/paytypes/update" : update(request, response); break;
            case "/paytypes/all" : getAll(response); break;
            case "/paytypes/delete" : delete(request, response); break;
        }   
    }
    
    private void insert(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Paymenttypes paytype = gson.fromJson(reader, Paymenttypes.class);
        dao.insert(paytype);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = request.getReader();
        Paymenttypes paytype = gson.fromJson(reader, Paymenttypes.class);
        dao.update(paytype);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String id = request.getParameter("id");
        Paymenttypes paytype = dao.get(id);
        String allJson = gson.toJson(paytype);
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
        List<Paymenttypes> all = dao.all();
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

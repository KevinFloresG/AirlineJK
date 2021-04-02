package com.airlinejk.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Kevin Flores
 */
public class ConnDB {
    
    private static ConnDB instance;
    private Connection conn;
    
    private ConnDB(){
        conn = this.connect();
    }
    
    public static ConnDB getInstance(){
        if(instance == null){
            instance = new ConnDB();
        }
        return instance;
    }
    
    private Connection connect(){
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "system";
        String pass = "root";
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            System.out.println("Unable to connect database");
        }
        return null;
    }
    
    public Connection getConn(){
        return conn;
    }
    
}

package com.airlinejk.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Unable to connect database");
        }
        return null;
    }
    
    public Connection getConn(){
        return conn;
    }
    
}

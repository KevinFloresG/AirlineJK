package com.airlinejk.daos;

import com.airlinejk.business_logic.Airplanetypes;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin Flores, Javier Amador
 */
public class AirplanetypesDao {
    
    private ConnDB conn;
    
    private static final String INSERT = "{call ins_arplType(?,?,?,?,?,?)}";
    private static final String UPDATE = "{call upd_arplType(?,?,?,?,?,?)}";
    private static final String DELETE = "{call del_arplType(?)}";
    private static final String ALL = "select * from airplaneTypes";
    private static final String GET = "select * from airplaneTypes where id = ?";
    
    public AirplanetypesDao(){
        conn = ConnDB.getInstance();
    }
    
    public void insert(Airplanetypes apt){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(INSERT);
            cs.setString(1, apt.getId());
            cs.setString(2, apt.getModel());
            cs.setString(3, apt.getBrand());
            cs.setInt(4, apt.getPassengersQ());
            cs.setInt(5, apt.getRowQ());
            cs.setInt(6, apt.getColumnQ());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to insert airplane type.");
        }
    }
    
    public void update(Airplanetypes apt){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(UPDATE);
            cs.setString(1, apt.getId());
            cs.setString(2, apt.getModel());
            cs.setString(3, apt.getBrand());
            cs.setInt(4, apt.getPassengersQ());
            cs.setInt(5, apt.getRowQ());
            cs.setInt(6, apt.getColumnQ());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to update airplane type info.");
        }
    }
    
    public void delete(String id){
        try {
            CallableStatement ps;
            ps = conn.getConn().prepareCall(DELETE);
            ps.setString(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to delete airplane type.");
        }
    }
    
    public List<Airplanetypes> all(){
        List<Airplanetypes> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructAirplanetypes(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all airplane types.");
        }
        return result;
    }
    
    public Airplanetypes get(String id){
        Airplanetypes result = new Airplanetypes();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(GET);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = constructAirplanetypes(rs);
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to get airplane type.");
        }
        return result;
    }
    
    private Airplanetypes constructAirplanetypes(ResultSet rs) throws SQLException{
        Airplanetypes apt = new Airplanetypes();
        apt.setId(rs.getString("id"));
        apt.setModel(rs.getString("model"));
        apt.setBrand(rs.getString("brand"));
        apt.setPassengersQ(rs.getInt("passengersQ"));
        apt.setRowQ(rs.getInt("rowQ"));
        apt.setColumnQ(rs.getInt("columnQ"));
        return apt;
    }
}

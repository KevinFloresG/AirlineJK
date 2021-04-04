package com.airlinejk.daos;

import com.airlinejk.business_logic.Airplanes;
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
public class AirplanesDao {
    
    private ConnDB conn;
    private AirplanetypesDao aptDao;
    
    private static final String INSERT = "{call ins_airplane(?,?,?)}";
    private static final String UPDATE = "{call upd_airplane(?,?,?)}";
    private static final String DELETE = "{call del_airplane(?)}";
    private static final String ALL = "select * from airplanes";
    private static final String ALL_TYPE_APL = "select * from airplanes where airplaneType_id = ?";
    private static final String GET = "select * from airplanes where id = ?";
    
    public AirplanesDao(){
        conn = ConnDB.getInstance();
        aptDao = new AirplanetypesDao();
    }
    
    public void insert(Airplanes airplane){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(INSERT);
            cs.setString(1, airplane.getId());
            cs.setInt(2, airplane.getAnno());
            cs.setString(3, airplane.getAirplaneType().getId());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to insert airplane.");
        }
    }
    
    public void update(Airplanes airplane){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(UPDATE);
            cs.setString(1, airplane.getId());
            cs.setInt(2, airplane.getAnno());
            cs.setString(3, airplane.getAirplaneType().getId());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to update airplane info.");
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
            System.out.println("Error: It was imposible to delete airplane.");
        }
    }
    
    public List<Airplanes> all(){
        List<Airplanes> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructAirplanes(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all airplanes.");
        }
        return result;
    }
    
    public List<Airplanes> allTypeAirplanes(String typeId){
        List<Airplanes> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL_TYPE_APL);
            ps.setString(1, typeId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructAirplanes(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all airplanes.");
        }
        return result;
    }
    
    public Airplanes get(String id){
        Airplanes result = new Airplanes();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(GET);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = constructAirplanes(rs);
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to get airplane.");
        }
        return result;
    }
    
    private Airplanes constructAirplanes(ResultSet rs) throws SQLException{
        Airplanes airplane = new Airplanes();
        airplane.setId(rs.getString("id"));
        airplane.setAnno(rs.getInt("anno"));
        airplane.setAirplaneType(aptDao.get(rs.getString("airplaneType_id")));
        return airplane;
    }
    
}

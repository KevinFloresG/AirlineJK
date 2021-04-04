package com.airlinejk.daos;

import com.airlinejk.business_logic.Countries;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin Flores
 */
public class CountriesDao {
    
    private ConnDB conn;
    
    private static final String INSERT = "{call ins_country(?,?)}";
    private static final String UPDATE = "{call upd_country(?,?)}";
    private static final String DELETE = "{call del_country(?)}";
    private static final String ALL = "select * from countries";
    private static final String GET = "select * from countries where id = ?";
    
    public CountriesDao(){
        conn = ConnDB.getInstance();
    }
    
    public void insert(Countries country){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(INSERT);
            cs.setString(1, country.getId());
            cs.setString(2, country.getName());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to insert country.");
        }
    }
    
    public void update(Countries country){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(UPDATE);
            cs.setString(1, country.getId());
            cs.setString(2, country.getName());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to update country info.");
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
            System.out.println("Error: It was imposible to delete country.");
        }
    }
    
    public List<Countries> all(){
        List<Countries> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructCountries(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all countries.");
        }
        return result;
    }
    
    public Countries get(String id){
        Countries result = new Countries();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(GET);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = constructCountries(rs);
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to get country.");
        }
        return result;
    }
    
    private Countries constructCountries(ResultSet rs) throws SQLException{
        Countries country = new Countries();
        country.setId(rs.getString("id"));
        country.setName(rs.getString("name"));
        return country;
    }
}

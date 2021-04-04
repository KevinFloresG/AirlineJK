package com.airlinejk.daos;

import com.airlinejk.business_logic.Cities;
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
public class CitiesDao {
    
    private ConnDB conn;
    private CountriesDao countryDao;
    
    private static final String INSERT = "{call ins_city(?,?,?)}";
    private static final String UPDATE = "{call upd_city(?,?,?)}";
    private static final String DELETE = "{call del_city(?)}";
    private static final String ALL = "select * from cities";
    private static final String GET = "select * from cities where id = ?";
    
    public CitiesDao(){
        conn = ConnDB.getInstance();
        countryDao = new CountriesDao();
    }
    
    public void insert(Cities city){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(INSERT);
            cs.setString(1, city.getId());
            cs.setString(2, city.getName());
            cs.setString(3, city.getCountry().getId());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Was imposible to insert city.");
        }
    }
    
    public void update(Cities city){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(UPDATE);
            cs.setString(1, city.getId());
            cs.setString(2, city.getName());
            cs.setString(3, city.getCountry().getId());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Was imposible to update city info.");
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
            System.out.println("Error: It was imposible to delete city.");
        }
    }
    
    public List<Cities> all(){
        List<Cities> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructCities(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all cities.");
        }
        return result;
    }
    
    public Cities get(String id){
        Cities result = new Cities();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(GET);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = constructCities(rs);
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to get city.");
        }
        return result;
    }
    
    private Cities constructCities(ResultSet rs) throws SQLException{
        Cities city = new Cities();
        city.setId(rs.getString("id"));
        city.setName(rs.getString("name"));
        city.setCountry(countryDao.get(rs.getString("country_id")));
        return city;
    }
    
}

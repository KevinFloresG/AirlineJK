package com.airlinejk.daos;

import com.airlinejk.business_logic.Routes;
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
public class RoutesDao {
    
    private ConnDB conn;
    private CitiesDao cityDao;
    private SchedulesDao scheduleDao;
    private AirplanesDao airplaneDao;
    
    private static final String INSERT = "{call ins_route(?,?,?,?,?,?,?)}";
    private static final String UPDATE = "{call upd_route(?,?,?,?,?,?,?)}";
    private static final String DELETE = "{call del_route(?)}";
    private static final String ALL = "select * from routes";
    private static final String SEARCH_BY_ORIGIN = "select * from routes where origin = ?";
    private static final String SEARCH_BY_DESTINATION = "select * from routes where destination = ?";
    private static final String SEARCH_BY_ORIGIN_DESTINATION = "select * from routes where origin = ? and destination = ?";
    private static final String SEARCH_BY_ORIGIN_DESTINATION_LIKE = "select * from (routes r inner join cities o on r.origin = o.id) inner join cities d on r.destination = d.id where o.name like ? and d.name like ?";
    private static final String GET = "select * from routes where id = ?";
    
    public RoutesDao(){
        conn = ConnDB.getInstance();
        cityDao = new CitiesDao();
        scheduleDao = new SchedulesDao();
        airplaneDao = new AirplanesDao();
    }
    
    public void insert(Routes route){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(INSERT);
            cs.setString(1, route.getId());
            cs.setInt(2, route.getDurationhours());
            cs.setInt(3, route.getDurationminutes());
            cs.setString(4, route.getOrigin().getId());
            cs.setString(5, route.getDestination().getId());
            cs.setString(6, route.getAirplaneId().getId());
            cs.setInt(7, route.getSchedule().getId());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to insert route.");
        }
    }
    
    public void update(Routes route){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(UPDATE);
            cs.setString(1, route.getId());
            cs.setInt(2, route.getDurationhours());
            cs.setInt(3, route.getDurationminutes());
            cs.setString(4, route.getOrigin().getId());
            cs.setString(5, route.getDestination().getId());
            cs.setString(6, route.getAirplaneId().getId());
            cs.setInt(7, route.getSchedule().getId());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to update route info.");
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
            System.out.println("Error: It was imposible to delete route.");
        }
    }
    
    public List<Routes> all(){
        List<Routes> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructRoutes(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all Routes.");
        }
        return result;
    }
    
    public List<Routes> searchByOrigin(String origin){
        List<Routes> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(SEARCH_BY_ORIGIN);
            ps.setString(1, origin);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructRoutes(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list routes with specified origin.");
        }
        return result;
    }
    
    public List<Routes> searchByDestination(String destination){
        List<Routes> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(SEARCH_BY_DESTINATION);
            ps.setString(1, destination);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructRoutes(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list routes with specified destination.");
        }
        return result;
    }
    
    public List<Routes> searchByOriDes(String origin, String destination){
        List<Routes> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(SEARCH_BY_ORIGIN_DESTINATION);
            ps.setString(1, origin);
            ps.setString(2, destination);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructRoutes(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list routes with specified origin and destination.");
        }
        return result;
    }
    
    public List<Routes> searchByOriDesWithLike(String origin, String destination){
        List<Routes> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(SEARCH_BY_ORIGIN_DESTINATION_LIKE);
            ps.setString(1, "%" + origin + "%");
            ps.setString(2, "%" + destination + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructRoutesForLikeSearch(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list routes with specified origin and destination.");
        }
        return result;
    }
    
    public Routes get(String id){
        Routes result = new Routes();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(GET);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = constructRoutes(rs);
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to get route.");
        }
        return result;
    }
    
    private Routes constructRoutes(ResultSet rs) throws SQLException{
        Routes route = new Routes();
        route.setId(rs.getString("id"));
        route.setDurationhours(rs.getInt("durationHours"));
        route.setDurationminutes(rs.getInt("durationMinutes"));
        route.setOrigin(cityDao.get(rs.getString("origin")));
        route.setDestination(cityDao.get(rs.getString("destination")));
        route.setAirplaneId(airplaneDao.get(rs.getString("airplane_id")));
        route.setSchedule(scheduleDao.get(rs.getInt("schedule")));
        return route;
    }
    
    private Routes constructRoutesForLikeSearch(ResultSet rs) throws SQLException{
        Routes route = new Routes();
        route.setId(rs.getString("r.id"));
        route.setDurationhours(rs.getInt("r.durationHours"));
        route.setDurationminutes(rs.getInt("r.durationMinutes"));
        route.setOrigin(cityDao.get(rs.getString("r.origin")));
        route.setDestination(cityDao.get(rs.getString("r.destination")));
        route.setAirplaneId(airplaneDao.get(rs.getString("r.airplane_id")));
        route.setSchedule(scheduleDao.get(rs.getInt("r.schedule")));
        return route;
    }
    
}

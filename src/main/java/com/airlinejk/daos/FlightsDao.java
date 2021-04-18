package com.airlinejk.daos;

import com.airlinejk.business_logic.Flights;
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
public class FlightsDao {
    
    private ConnDB conn;
    private RoutesDao routeDao;
    
    private static final String INSERT = "{call ins_flight(?,?,?,?,?,?)}";
    private static final String UPDATE_FLIGHTSEATS = "{call upd_flightSeats(?,?)}";
    private static final String UPDATE_FLIGHTINFO = "{call upd_flightInfo(?,?,?,?,?,?,?)}";
    private static final String DELETE = "{call del_flight(?)}";
    private static final String ALL = "select * from flights";
    private static final String ALL_DISCOUNT_FLIGHTS = "select * from flights where discount != 0";
    private static final String SEARCH_BY_ROUTE = "select * from flights where route = ?";
    private static final String SEARCH_BY_ROUTE_DISCOUNT = "select * from flights where route = ? and discount != 0";
    private static final String GET = "select * from flights where id = ?";
    private static final String LAST = "select * from flights where id = ( select max(id) from flights )";
    
    public FlightsDao(){
        conn = ConnDB.getInstance();
        routeDao = new RoutesDao();
    }
    
    public void insert(Flights flight){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(INSERT);
            cs.setString(1, flight.getRoute().getId());
            cs.setDate(2, flight.getDepartureDate());
            cs.setDate(3, flight.getReturnDate());
            cs.setDouble(4, flight.getPrice());
            cs.setDouble(5, flight.getDiscount());
            cs.setInt(6, flight.getAvailableSeats());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to insert flight.");
        }
    }
    
    public void updateFlightInfo(Flights flight){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(UPDATE_FLIGHTINFO);
            cs.setInt(1, flight.getId());
            cs.setString(2, flight.getRoute().getId());
            cs.setDate(3, flight.getDepartureDate());
            cs.setDate(4, flight.getReturnDate());
            cs.setDouble(5, flight.getPrice());
            cs.setDouble(6, flight.getDiscount());
            cs.setInt(7, flight.getAvailableSeats());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to update flight info.");
        }
    }
    
    public void updateFlightSeats(Flights flight){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(UPDATE_FLIGHTSEATS);
            cs.setInt(1, flight.getId());
            cs.setInt(2, flight.getAvailableSeats());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to update flight seats.");
        }
    }
    
    public void delete(Integer id){
        try {
            CallableStatement ps;
            ps = conn.getConn().prepareCall(DELETE);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to delete flight.");
        }
    }
    
    public List<Flights> all(){
        List<Flights> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructFlights(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all Flights.");
        }
        return result;
    }
    
    public List<Flights> allDiscount(){
        List<Flights> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL_DISCOUNT_FLIGHTS);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructFlights(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all Flights with Discount.");
        }
        return result;
    }
    
    public List<Flights> searchByRoute(String route){
        List<Flights> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(SEARCH_BY_ROUTE);
            ps.setString(1, route);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructFlights(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list Flights with specified route.");
        }
        return result;
    }
    
    public List<Flights> searchByRouteWithDiscount(String route){
        List<Flights> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(SEARCH_BY_ROUTE_DISCOUNT);
            ps.setString(1, route);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructFlights(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list Flights with discount and specified route.");
        }
        return result;
    }
    
    public Flights getLastAdded(){
        Flights result = new Flights();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(LAST);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = constructFlights(rs);
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to get flight.");
        }
        return result;
    }
    
    public Flights get(Integer id){
        Flights result = new Flights();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(GET);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = constructFlights(rs);
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to get flight.");
        }
        return result;
    }
    
    private Flights constructFlights(ResultSet rs) throws SQLException{
        Flights flight = new Flights();
        flight.setId(rs.getInt("id"));
        flight.setRoute(routeDao.get(rs.getString("route")));
        flight.setDepartureDate(rs.getDate("departureDate"));
        flight.setReturnDate(rs.getDate("returnDate"));
        flight.setPrice(rs.getDouble("price"));
        flight.setDiscount(rs.getDouble("discount"));
        flight.setAvailableSeats(rs.getInt("availableSeats"));
        return flight;
    }
}

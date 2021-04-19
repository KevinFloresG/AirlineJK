package com.airlinejk.daos;

import com.airlinejk.business_logic.Flights;
import com.airlinejk.business_logic.Reservations;
import com.airlinejk.business_logic.Tickets;
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
public class TicketsDao {
    
    private ConnDB conn;
    private FlightsDao flightsDao;
    private ReservationsDao reservationsDao;

    
    private static final String INSERT = "{call ins_ticket(?,?,?,?)}";
    private static final String UPDATE = "{call upd_ticket(?,?,?,?)}";
    private static final String DELETE = "{call del_reservation(?)}";
    private static final String ALL = "select * from tickets";
    private static final String ALL_RES_TICK = "select * from tickets where reservation = ?";
    private static final String ALL_FLIGHT_TICK = "select * from tickets where flight = ?";
    private static final String GET = "select * from tickets where id = ?";
    
    public TicketsDao(){
        conn = ConnDB.getInstance();
        flightsDao = new FlightsDao();
        reservationsDao = new ReservationsDao();
    }
    
    public void insert(Tickets ticket){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(INSERT);
            cs.setInt(1, ticket.getReservation().getId());
            cs.setInt(2, ticket.getFlight().getId());
            cs.setInt(3, ticket.getRowN());
            cs.setInt(4, ticket.getColumnN());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to insert the ticket.");
        }
    }
    
    public void update(Tickets ticket){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(UPDATE);
            cs.setInt(1, ticket.getId());
            cs.setString(2, ticket.getOwner());
            cs.setInt(3, ticket.getRowN());
            cs.setInt(4, ticket.getColumnN());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to update ticket info.");
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
            System.out.println("Error: It was imposible to delete ticket.");
        }
    }
    
    public List<Tickets> all(){
        List<Tickets> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructTickets(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all tickets.");
        }
        return result;
    }
    
        
    public List<Tickets> allResTickets(Integer resId){
        List<Tickets> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL_RES_TICK);
            ps.setInt(1, resId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructTickets(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all reservation tickets.");
        }
        return result;
    }
    
    public List<Tickets> allFlightTickets(Integer flightId){
        List<Tickets> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL_FLIGHT_TICK);
            ps.setInt(1, flightId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructTickets(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all flight tickets.");
        }
        return result;
    }
    
    
    public Tickets get(Integer id){
        Tickets result = new Tickets();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(GET);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = constructTickets(rs);
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to get reservation.");
        }
        return result;
    }


    
    private Tickets constructTickets(ResultSet rs) throws SQLException{
        Tickets ticket = new Tickets();
        Reservations r = new Reservations();
        Flights f = new Flights();
        ticket.setId(rs.getInt("id"));
        f.setId(rs.getInt("flight"));
        r.setId(rs.getInt("reservation"));
        ticket.setFlight(f);
        ticket.setReservation(r);
        ticket.setRowN(rs.getInt("rowN"));
        ticket.setColumnN(rs.getInt("columnN"));

        return ticket;
    }
}

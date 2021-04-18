package com.airlinejk.daos;

import com.airlinejk.business_logic.Reservations;
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
public class ReservationsDao {
    
    private ConnDB conn;
    private UsersDao userDao;
    private PaymentTypesDao payTypeDao;

    
    private static final String INSERT = "{call ins_reservation(?,?,?,?,?,?,?)}";
    private static final String UPDATE = "{call upd_resCSeats(?,?)}";
    private static final String DELETE = "{call del_reservation(?)}";
    private static final String ALL = "select * from reservations";
    private static final String ALL_USER_RES = "select * from reservations where userID = ?";
    private static final String ALL_FLIGHT_RES = "select * from reservations where flight like ?";
    private static final String GET = "select * from reservations where id = ?";
    
    public ReservationsDao(){
        conn = ConnDB.getInstance();
        userDao = new UsersDao();
        payTypeDao = new PaymentTypesDao();
    }
    
    public void insert(Reservations reservation){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(INSERT);
            cs.setString(1, reservation.getFlightInfo());
            cs.setInt(2, reservation.getFlightId());
            cs.setString(3, reservation.getUser().getUsername());
            cs.setDouble(4, reservation.getTotalPrice());
            cs.setString(5, reservation.getAirplane());
            cs.setString(6, reservation.getTypeOfPayment().getCode());
            cs.setInt(7, reservation.getSeatQuantity());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to insert reservation.");
        }
    }
    
    public void update(Reservations reservation){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(UPDATE);
            cs.setInt(1, reservation.getId());
            cs.setInt(2, reservation.getCheckedInQuantity());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Error: It was imposible to update reservation info.");
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
            System.out.println("Error: It was imposible to delete reservation.");
        }
    }
    
    public List<Reservations> all(){
        List<Reservations> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructReservations(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all Reservations.");
        }
        return result;
    }
    
    public List<Reservations> allUserRes(String userID){
        List<Reservations> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL_USER_RES);
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructReservations(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all User Reservations.");
        }
        return result;
    }
    
    public List<Reservations> allFlightRes(String flightInfo){
        List<Reservations> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL_FLIGHT_RES);
            ps.setString(1, "%" + flightInfo + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructReservations(rs));
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to list all Flight Reservations.");
        }
        return result;
    }
    
    public Reservations get(Integer id){
        Reservations result = new Reservations();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(GET);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = constructReservations(rs);
            }
        }catch(SQLException ex){
            System.out.println("Error: It was imposible to get reservation.");
        }
        return result;
    }
    
    private Reservations constructReservations(ResultSet rs) throws SQLException{
        Reservations reservation = new Reservations();
        reservation.setId(rs.getInt("id"));
        reservation.setFlightInfo(rs.getString("flightInfo"));
        reservation.setFlightId(rs.getInt("flightId"));
        reservation.setUser(userDao.get(rs.getString("userID")));
        reservation.setTotalPrice(rs.getDouble("totalPrice"));
        reservation.setAirplane(rs.getString("airplane_id"));
        reservation.setTypeOfPayment(payTypeDao.get(rs.getString("typeOfPayment")));
        reservation.setSeatQuantity(rs.getInt("seatQuantity"));
        reservation.setCheckedInQuantity(rs.getInt("checkedInQuantity"));
        return reservation;
    }
}

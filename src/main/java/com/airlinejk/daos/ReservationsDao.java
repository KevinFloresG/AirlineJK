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
 * @author Kevin Flores
 */
public class ReservationsDao {
    
    private ConnDB conn;
    private UsersDao userDao;
    private AirplanesDao airplaneDao;
    private PaymentTypesDao payTypeDao;
    //PFlight in varchar2, PUser in varchar2, 
    //PPrice in number, PAId in varchar2, 
    //PPType in varchar2, PSeatQ in number
    
    private static final String INSERT = "{call ins_reservation(?,?,?,?,?,?)}";
    private static final String UPDATE = "{call upd_resCSeats(?,?)}";
    private static final String DELETE = "{call del_reservation(?)}";
    private static final String ALL = "select * from reservations";
    private static final String GET = "select * from reservations where id = ?";
    
    public ReservationsDao(){
        conn = ConnDB.getInstance();
        userDao = new UsersDao();
        airplaneDao = new AirplanesDao();
        payTypeDao = new PaymentTypesDao();
    }
    
    public void insert(Reservations reservation){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(INSERT);
            cs.setInt(1, reservation.getFlight());
            cs.setString(2, reservation.getUser().getUsername());
            cs.setDouble(3, reservation.getTotalPrice());
            cs.setString(4, reservation.getAirplane().getId());
            cs.setString(5, reservation.getTypeOfPayment().getCode());
            cs.setInt(6, reservation.getSeatQuantity());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Was imposible to insert reservation.");
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
            System.out.println("Was imposible to update reservation info.");
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
            System.out.println("Was imposible to delete reservation.");
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
            System.out.println("Was imposible to list all Reservations.");
        }
        return result;
    }
    
    public Reservations get(String id){
        Reservations result = new Reservations();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(GET);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = constructReservations(rs);
            }
        }catch(SQLException ex){
            System.out.println("Was imposible to get reservation.");
        }
        return result;
    }
    
    private Reservations constructReservations(ResultSet rs) throws SQLException{
        Reservations reservation = new Reservations();
        reservation.setId(rs.getInt("id"));
        reservation.setFlight(rs.getInt("flight"));
        reservation.setUser(userDao.get("userID"));
        reservation.setTotalPrice(rs.getDouble("totalPrice"));
        reservation.setAirplane(airplaneDao.get("airplane_id"));
        reservation.setTypeOfPayment(payTypeDao.get("typeOfPayment"));
        reservation.setSeatQuantity(rs.getInt("seatQuantity"));
        reservation.setCheckedInQuantity(rs.getInt("checkedInQuantity"));
        return reservation;
    }
}

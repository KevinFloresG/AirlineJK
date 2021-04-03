package com.airlinejk.daos;

import com.airlinejk.business_logic.Schedules;
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
public class SchedulesDao {
    
    private ConnDB conn;
    
    private static final String INSERT = "{call ins_schedule(?,?)}";
    private static final String UPDATE = "{call upd_schedule(?,?,?)}";
    private static final String DELETE = "{call del_schedule(?)}";
    private static final String ALL = "select * from schedules";
    private static final String GET = "select * from schedules where id = ?";
    
    public SchedulesDao(){
        conn = ConnDB.getInstance();
    }
    
    public void insert(Schedules schedule){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(INSERT);
            cs.setString(1, schedule.getWeekday());
            cs.setDate(2, schedule.getDepartureTime());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Was imposible to insert schedule.");
        }
    }
    
    public void update(Schedules schedule){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(UPDATE);
            cs.setInt(1, schedule.getId());
            cs.setString(2, schedule.getWeekday());
            cs.setDate(3, schedule.getDepartureTime());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Was imposible to update schedule info.");
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
            System.out.println("Was imposible to delete schedule.");
        }
    }
    
    public List<Schedules> all(){
        List<Schedules> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructSchedules(rs));
            }
        }catch(SQLException ex){
            System.out.println("Was imposible to list all schedules.");
        }
        return result;
    }
    
    public Schedules get(Integer id){
        Schedules result = new Schedules();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(GET);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = constructSchedules(rs);
            }
        }catch(SQLException ex){
            System.out.println("Was imposible to get schedule.");
        }
        return result;
    }
    
    private Schedules constructSchedules(ResultSet rs) throws SQLException{
        Schedules schedule = new Schedules();
        schedule.setId(rs.getInt("id"));
        schedule.setWeekday(rs.getString("weekday"));
        schedule.setDepartureTime(rs.getDate("departureTime"));
        return schedule;
    }
}

package com.airlinejk.daos;

import com.airlinejk.business_logic.Paymenttypes;
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
public class PaymentTypesDao {
    
    private ConnDB conn;
    
    private static final String INSERT = "{call ins_payType(?,?)}";
    private static final String UPDATE = "{call upd_payType(?,?)}";
    private static final String DELETE = "{call del_payType(?)}";
    private static final String ALL = "select * from paymentTypes";
    private static final String GET = "select * from paymentTypes where id = ?";
    
    public PaymentTypesDao(){
        conn = ConnDB.getInstance();
    }
    
    public void insert(Paymenttypes payType){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(INSERT);
            cs.setString(1, payType.getCode());
            cs.setString(2, payType.getDescription());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Was imposible to insert payType.");
        }
    }
    
    public void update(Paymenttypes payType){
        try {
            CallableStatement cs;
            cs = conn.getConn().prepareCall(UPDATE);
            cs.setString(1, payType.getCode());
            cs.setString(2, payType.getDescription());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException ex) {
            System.out.println("Was imposible to update payType info.");
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
            System.out.println("Was imposible to delete payType.");
        }
    }
    
    public List<Paymenttypes> all(){
        List<Paymenttypes> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructPaymenttypes(rs));
            }
        }catch(SQLException ex){
            System.out.println("Was imposible to list all Paymenttypes.");
        }
        return result;
    }
    
    public Paymenttypes get(String id){
        Paymenttypes result = new Paymenttypes();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(GET);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = constructPaymenttypes(rs);
            }
        }catch(SQLException ex){
            System.out.println("Was imposible to get payType.");
        }
        return result;
    }
    
    private Paymenttypes constructPaymenttypes(ResultSet rs) throws SQLException{
        Paymenttypes payType = new Paymenttypes();
        payType.setCode(rs.getString("code"));
        payType.setDescription(rs.getString("description"));
        return payType;
    }
}

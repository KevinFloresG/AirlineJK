package com.airlinejk.daos;

import com.airlinejk.business_logic.Userss;
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
public class UsersDao {
    
    private ConnDB conn;
    
    private static final String INSERT = "{call ins_user(?,?,?,?,?,?,?,?,?,?)}";
    private static final String UPDATE_USERINFO = "{call upd_userInfo(?,?,?,?,?)}";
    private static final String UPDATE_USERPASS = "{call upd_userPassW(?,?)}";
    private static final String DELETE = "{call del_user(?)}";
    private static final String ALL = "select * from userss";
    private static final String GET = "select * from userss where username = ?";
    
    public UsersDao(){
        conn = ConnDB.getInstance();
    }
    
    public void insert(Userss user){
        try {
            CallableStatement ps;
            ps = conn.getConn().prepareCall(INSERT);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getLastname());
            ps.setString(5, user.getEmail());
            ps.setDate(6, user.getDateOfBirth());
            ps.setString(7, user.getAddress());
            ps.setString(8, user.getWorkphone());
            ps.setString(9, user.getCellphone());
            ps.setInt(10, user.getIsAdmin());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Was imposible to insert user.");
        }
    }
    
    public void updateUserInfo(Userss user){
        try {
            CallableStatement ps;
            ps = conn.getConn().prepareCall(UPDATE_USERINFO);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getAddress());
            ps.setString(4, user.getWorkphone());
            ps.setString(5, user.getCellphone());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Was imposible to update user info.");
        }
    }
    
    public void updateUserPass(Userss user){
        try {
            CallableStatement ps;
            ps = conn.getConn().prepareCall(UPDATE_USERPASS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Was imposible to update user password.");
        }
    }
    
    public void delete(String username){
        try {
            CallableStatement ps;
            ps = conn.getConn().prepareCall(DELETE);
            ps.setString(1, username);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Was imposible to delete user.");
        }
    }
    
    public List<Userss> all(){
        List<Userss> result = new ArrayList<>();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(constructUser(rs));
            }
        }catch(SQLException ex){
            System.out.println("Was imposible to list all users.");
        }
        return result;
    }
    
    public Userss get(String username){
        Userss result = new Userss();
        try{
            PreparedStatement ps = conn.getConn().prepareStatement(GET);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result = constructUser(rs);
            }
        }catch(SQLException ex){
            System.out.println("Was imposible to get user.");
        }
        return result;
    }
    
    private Userss constructUser(ResultSet rs) throws SQLException{
        Userss user = new Userss();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));
        user.setLastname(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setDateOfBirth(rs.getDate("dateOfBirth"));
        user.setAddress(rs.getString("address"));
        user.setWorkphone(rs.getString("workphone"));
        user.setCellphone(rs.getString("cellphone"));
        user.setIsAdmin(rs.getInt("isAdmin"));
        return user;
    }
    
}

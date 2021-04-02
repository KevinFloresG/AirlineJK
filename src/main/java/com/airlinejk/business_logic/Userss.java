package com.airlinejk.business_logic;

import java.sql.Date;

/**
 *
 * @author Kevin Flores
 */
public class Userss {
    
    private String username, password, name, lastname;
    private String email, address, workphone, cellphone;
    private Date dateOfBirth;
    private Integer isAdmin;

    public Userss() {
        this.username = "";
        this.password = "";
        this.name = "";
        this.lastname = "";
        this.email = "";
        this.address = "";
        this.workphone = "";
        this.cellphone = "";
        this.dateOfBirth = null;
        this.isAdmin = 0;
    }

    public Userss(String username, String password, String name, String last_name,
            String email, String address, String workphone, String cellphone, 
            Date dateOfBirth, Integer isAdmin) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = last_name;
        this.email = email;
        this.address = address;
        this.workphone = workphone;
        this.cellphone = cellphone;
        this.dateOfBirth = dateOfBirth;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String last_name) {
        this.lastname = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public String getWorkphone() {
        return workphone;
    }

    public void setWorkphone(String workphone) {
        this.workphone = workphone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
    
}

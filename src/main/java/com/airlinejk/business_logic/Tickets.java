/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airlinejk.business_logic;

/**
 *
 * @author Kevin Flores, Javier Amador
 */
public class Tickets {
    
    private Integer id, rowN, columnN;
    private String owner;
    private Reservations reservation;
    private Flights flight;
    
    public Tickets() {
        this.id = null;
        this.rowN = null;
        this.columnN = null;
        this.owner = null;
        this.reservation = null;
        this.flight = null;
    }

    public Tickets(Integer id, Integer rowN, Integer columnN, String owner, Reservations reservation, Flights flight) {
        this.id = id;
        this.rowN = rowN;
        this.columnN = columnN;
        this.owner = owner;
        this.reservation = reservation;
        this.flight = flight;
    }

    public Tickets(Integer id, Reservations reservation, Flights flight) {
        this.id = id;
        this.reservation = reservation;
        this.flight = flight;
        this.owner = null;
        this.rowN = null;
        this.columnN = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRowN() {
        return rowN;
    }

    public void setRowN(Integer rowN) {
        this.rowN = rowN;
    }

    public Integer getColumnN() {
        return columnN;
    }

    public void setColumnN(Integer columnN) {
        this.columnN = columnN;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Reservations getReservation() {
        return reservation;
    }

    public void setReservation(Reservations reservation) {
        this.reservation = reservation;
    }

    public Flights getFlight() {
        return flight;
    }

    public void setFlight(Flights flight) {
        this.flight = flight;
    }
    
    
    
    
}

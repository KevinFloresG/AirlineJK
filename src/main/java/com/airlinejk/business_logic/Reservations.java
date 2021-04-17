package com.airlinejk.business_logic;

/**
 *
 * @author Kevin Flores, Javier Amador
 */
public class Reservations {
    
    private Integer id, seatQuantity, checkedInQuantity, flightId;
    private String flightInfo;
    private Userss user;
    private double totalPrice;
    private Airplanes airplane;
    private Paymenttypes typeOfPayment;
    
    

    public Reservations() {
    }

    public Reservations(Integer id, Integer seatQuantity, Integer checkedInQuantity, String flightInfo, Integer FlightId, Userss user, double totalPrice, Airplanes airplane, Paymenttypes typeOfPayment) {
        this.id = id;
        this.seatQuantity = seatQuantity;
        this.checkedInQuantity = checkedInQuantity;
        this.flightInfo = flightInfo;
        this.flightId = FlightId;
        this.user = user;
        this.totalPrice = totalPrice;
        this.airplane = airplane;
        this.typeOfPayment = typeOfPayment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeatQuantity() {
        return seatQuantity;
    }

    public void setSeatQuantity(Integer seatQuantity) {
        this.seatQuantity = seatQuantity;
    }

    public Integer getCheckedInQuantity() {
        return checkedInQuantity;
    }

    public void setCheckedInQuantity(Integer checkedInQuantity) {
        this.checkedInQuantity = checkedInQuantity;
    }

    public String getFlightInfo() {
        return flightInfo;
    }

    public void setFlightInfo(String flight) {
        this.flightInfo = flight;
    }
    
    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public Userss getUser() {
        return user;
    }

    public void setUser(Userss user) {
        this.user = user;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Airplanes getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplanes airplane) {
        this.airplane = airplane;
    }

    public Paymenttypes getTypeOfPayment() {
        return typeOfPayment;
    }

    public void setTypeOfPayment(Paymenttypes typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }
    
}

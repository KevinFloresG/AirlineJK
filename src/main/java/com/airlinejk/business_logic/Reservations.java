package com.airlinejk.business_logic;

/**
 *
 * @author Kevin Flores
 */
public class Reservations {
    
    private Integer id, seatQuantity, checkedInQuantity, flight;
    private Userss user;
    private double totalPrice;
    private Airplanes airplane;
    private Paymenttypes typeOfPayment;

    public Reservations() {
    }

    public Reservations(Integer id, Integer seatQuantity, Integer checkedInQuantity, Integer flight, Userss user, double totalPrice, Airplanes airplane, Paymenttypes typeOfPayment) {
        this.id = id;
        this.seatQuantity = seatQuantity;
        this.checkedInQuantity = checkedInQuantity;
        this.flight = flight;
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

    public Integer getFlight() {
        return flight;
    }

    public void setFlight(Integer flight) {
        this.flight = flight;
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

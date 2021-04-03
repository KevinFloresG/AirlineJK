package com.airlinejk.business_logic;

import java.sql.Date;

/**
 *
 * @author Kevin Flores
 */
public class Flights {
    
    private Integer id, availableSeats;
    private double price, discount;
    private Date departureDate, returnDate;
    private Routes route;

    public Flights() {
    }

    public Flights(Integer id, Integer availableSeats, double price, double discount, Date departureDate, Date returnDate, Routes route) {
        this.id = id;
        this.availableSeats = availableSeats;
        this.price = price;
        this.discount = discount;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.route = route;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Routes getRoute() {
        return route;
    }

    public void setRoute(Routes route) {
        this.route = route;
    }
     
}

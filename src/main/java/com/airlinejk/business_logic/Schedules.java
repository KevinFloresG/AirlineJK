package com.airlinejk.business_logic;



/**
 *
 * @author Kevin Flores, Javier Amador
 */
public class Schedules {
    
    private Integer id;
    private String weekday;
    private String departureTime;

    public Schedules() {
        this.id = 0;
        this.weekday = "";
        this.departureTime = "";
    }

    public Schedules(Integer id, String weekday, String departureTime) {
        this.id = id;
        this.weekday = weekday;
        this.departureTime = departureTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
    
}

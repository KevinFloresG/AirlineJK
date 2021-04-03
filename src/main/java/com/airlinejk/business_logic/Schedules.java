package com.airlinejk.business_logic;

import java.sql.Date;

/**
 *
 * @author Kevin Flores
 */
public class Schedules {
    
    private Integer id;
    private String weekday;
    private Date departureTime;

    public Schedules() {
        this.id = 0;
        this.weekday = "";
        this.departureTime = null;
    }

    public Schedules(Integer id, String weekday, Date departureTime) {
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

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
    
}

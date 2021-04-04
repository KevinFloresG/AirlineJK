package com.airlinejk.business_logic;

/**
 *
 * @author Kevin Flores, Javier Amador
 */

public class Routes{

    private String id;
    private Integer durationhours, durationminutes;
    private Airplanes airplaneId;
    private Cities origin, destination;
    private Schedules schedule;

    public Routes() {
    }

    public Routes(String id) {
        this.id = id;
    }

    public Routes(String id, Integer durationhours, Integer durationminutes) {
        this.id = id;
        this.durationhours = durationhours;
        this.durationminutes = durationminutes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDurationhours() {
        return durationhours;
    }

    public void setDurationhours(Integer durationhours) {
        this.durationhours = durationhours;
    }

    public Integer getDurationminutes() {
        return durationminutes;
    }

    public void setDurationminutes(Integer durationminutes) {
        this.durationminutes = durationminutes;
    }

    public Airplanes getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(Airplanes airplaneId) {
        this.airplaneId = airplaneId;
    }

    public Cities getOrigin() {
        return origin;
    }

    public void setOrigin(Cities origin) {
        this.origin = origin;
    }

    public Cities getDestination() {
        return destination;
    }

    public void setDestination(Cities destination) {
        this.destination = destination;
    }

    public Schedules getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedules schedule) {
        this.schedule = schedule;
    }
    
}

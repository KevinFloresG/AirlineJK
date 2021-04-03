package com.airlinejk.business_logic;

/**
 *
 * @author Kevin Flores
 */
public class Airplanetypes {
    
    private String id, model, brand;
    private Integer passengersQ, rowQ, columnQ;

    public Airplanetypes() {
        this.id = "";
        this.model = "";
        this.brand = "";
        this.passengersQ = 0;
        this.rowQ = 0;
        this.columnQ = 0;
    }

    public Airplanetypes(String id, String model, String brand, Integer passengersQ, Integer rowQ, Integer columnQ) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.passengersQ = passengersQ;
        this.rowQ = rowQ;
        this.columnQ = columnQ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPassengersQ() {
        return passengersQ;
    }

    public void setPassengersQ(Integer passengersQ) {
        this.passengersQ = passengersQ;
    }

    public Integer getRowQ() {
        return rowQ;
    }

    public void setRowQ(Integer rowQ) {
        this.rowQ = rowQ;
    }

    public Integer getColumnQ() {
        return columnQ;
    }

    public void setColumnQ(Integer columnQ) {
        this.columnQ = columnQ;
    }
     
}

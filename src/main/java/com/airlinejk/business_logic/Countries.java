package com.airlinejk.business_logic;

/**
 *
 * @author Kevin Flores
 */
public class Countries {
    
    private String id, name;

    public Countries() {
        this.id = "";
        this.name = "";
    }

    public Countries(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

package com.airlinejk.business_logic;

/**
 *
 * @author Kevin Flores
 */
public class Cities {
    
    private String id, name;
    private Countries country;

    public Cities() {
        this.id = "";
        this.name = "";
        this.country = new Countries();
    }

    public Cities(String id, String name, Countries country) {
        this.id = id;
        this.name = name;
        this.country = country;
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

    public Countries getCountry() {
        return country;
    }

    public void setCountry(Countries country) {
        this.country = country;
    }
    
}

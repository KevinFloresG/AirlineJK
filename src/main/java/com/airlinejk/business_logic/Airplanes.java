package com.airlinejk.business_logic;

/**
 *
 * @author Kevin Flores
 */
public class Airplanes {
    
    private String id;
    private Integer anno;
    private Airplanetypes airplaneType;

    public Airplanes() {
        this.id = "";
        this.anno = 0;
        this.airplaneType = new Airplanetypes();
    }

    public Airplanes(String id, Integer anno, Airplanetypes airplaneType) {
        this.id = id;
        this.anno = anno;
        this.airplaneType = airplaneType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public Airplanetypes getAirplaneType() {
        return airplaneType;
    }

    public void setAirplaneType(Airplanetypes airplaneType) {
        this.airplaneType = airplaneType;
    }
    
}

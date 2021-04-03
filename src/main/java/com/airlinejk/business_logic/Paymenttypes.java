package com.airlinejk.business_logic;

/**
 *
 * @author Kevin Flores
 */
public class Paymenttypes {
    
    private String code, description;

    public Paymenttypes() {
    }

    public Paymenttypes(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}

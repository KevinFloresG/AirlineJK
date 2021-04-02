/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airlinejk.business_logic;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kevin Flores
 */
@Entity
@Table(name = "ROUTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Routes.findAll", query = "SELECT r FROM Routes r"),
    @NamedQuery(name = "Routes.findById", query = "SELECT r FROM Routes r WHERE r.id = :id"),
    @NamedQuery(name = "Routes.findByDurationhours", query = "SELECT r FROM Routes r WHERE r.durationhours = :durationhours"),
    @NamedQuery(name = "Routes.findByDurationminutes", query = "SELECT r FROM Routes r WHERE r.durationminutes = :durationminutes")})
public class Routes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DURATIONHOURS")
    private BigInteger durationhours;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DURATIONMINUTES")
    private BigInteger durationminutes;
    @JoinColumn(name = "AIRPLANE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Airplanes airplaneId;
    @JoinColumn(name = "ORIGIN", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cities origin;
    @JoinColumn(name = "DESTINATION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cities destination;
    @JoinColumn(name = "SCHEDULE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Schedules schedule;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
    private List<Flights> flightsList;

    public Routes() {
    }

    public Routes(String id) {
        this.id = id;
    }

    public Routes(String id, BigInteger durationhours, BigInteger durationminutes) {
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

    public BigInteger getDurationhours() {
        return durationhours;
    }

    public void setDurationhours(BigInteger durationhours) {
        this.durationhours = durationhours;
    }

    public BigInteger getDurationminutes() {
        return durationminutes;
    }

    public void setDurationminutes(BigInteger durationminutes) {
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

    @XmlTransient
    public List<Flights> getFlightsList() {
        return flightsList;
    }

    public void setFlightsList(List<Flights> flightsList) {
        this.flightsList = flightsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Routes)) {
            return false;
        }
        Routes other = (Routes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "business_logic.Routes[ id=" + id + " ]";
    }
    
}

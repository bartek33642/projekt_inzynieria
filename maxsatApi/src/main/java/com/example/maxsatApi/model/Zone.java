package com.example.maxsatApi.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ_ZONE")
    public int zoneId;
    public int cordX;
    public int cordY;
    public double demandFactor;
    public double accessibilityFactor;
    public double attractivenessFactor;
    @OneToMany(mappedBy = "zone", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<ParkingLot> parkingLots;

    public Zone(int cordX, int cordY, double demandFactor, double accessibilityFactor, double attractivenessFactor) {
        this.cordX = cordX;
        this.cordY = cordY;
        this.demandFactor = demandFactor;
        this.accessibilityFactor = accessibilityFactor;
        this.attractivenessFactor = attractivenessFactor;
    }

    public Zone() {

    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public int getCordX() {
        return cordX;
    }

    public void setCordX(int cordX) {
        this.cordX = cordX;
    }

    public int getCordY() {
        return cordY;
    }

    public void setCordY(int cordY) {
        this.cordY = cordY;
    }

    public double getDemandFactor() {
        return demandFactor;
    }

    public void setDemandFactor(double demandFactor) {
        this.demandFactor = demandFactor;
    }

    public double getAccessibilityFactor() {
        return accessibilityFactor;
    }

    public void setAccessibilityFactor(double accessibilityFactor) {
        this.accessibilityFactor = accessibilityFactor;
    }

    public double getAttractivenessFactor() {
        return attractivenessFactor;
    }

    public void setAttractivenessFactor(double attractivenessFactor) {
        this.attractivenessFactor = attractivenessFactor;
    }
}

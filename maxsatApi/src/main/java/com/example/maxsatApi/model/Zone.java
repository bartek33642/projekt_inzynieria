package com.example.maxsatApi.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ_ZONE")
    private int zoneId;
    private int cordX;
    private int cordY;
    private double demandFactor;
    private double accessibilityFactor;
    private double attractivenessFactor;
    @OneToMany(mappedBy = "zone", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ParkingLot> parkingLots;

    public Zone(int cordX, int cordY, double demandFactor, double accessibilityFactor, double attractivenessFactor) {
        this.cordX = cordX;
        this.cordY = cordY;
        this.demandFactor = demandFactor;
        this.accessibilityFactor = accessibilityFactor;
        this.attractivenessFactor = attractivenessFactor;
    }

    public Zone() {

    }

    public Set<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(Set<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
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

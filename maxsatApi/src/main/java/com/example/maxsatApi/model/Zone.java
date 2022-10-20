package com.example.maxsatApi.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer zoneId;
    public int cordX;
    public int cordY;
    public double demandFactor;
    public double accessibilityFactor;
    public double attractivenessFactor;
    @OneToMany(mappedBy = "zone", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
}

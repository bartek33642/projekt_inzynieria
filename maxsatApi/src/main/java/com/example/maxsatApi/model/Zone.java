package com.example.maxsatApi.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Zone {
    @Id
    public int zoneId;
    public int cordX;
    public int cordY;
    public double demandFactor;
    public double accessibilityFactor;
    public double attractivenessFactor;
    @OneToMany(mappedBy = "zone", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<ParkingLot> parkingLots;
}

package com.example.maxsatApi.model;

import org.hibernate.mapping.Set;

import javax.persistence.*;

@Entity
public class ParkingLot {
    @Id
    public int parkingLotId;
    @Column(insertable = false, updatable = false)
    public int zoneId;
    public boolean haveSpaceForHandicapped;
    public boolean isGuarded;
    public boolean isPaid;
    public int freeSpaces;
    public boolean isPrivate;
    public boolean haveSpacesForElectrics;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zoneId", nullable = false)
    public Zone zone;
}

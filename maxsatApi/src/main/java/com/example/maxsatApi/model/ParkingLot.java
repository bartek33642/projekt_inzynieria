package com.example.maxsatApi.model;

import javax.persistence.*;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ_PARKING_LOT")
    public Integer parkingLotId;
    @Column(insertable = false, updatable = false)
    public Integer zoneId;
    public boolean haveSpaceForHandicapped;
    public boolean isGuarded;
    public boolean isPaid;
    public int freeSpaces;
    public boolean isPrivate;
    public boolean haveSpacesForElectrics;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "zoneId", nullable = false)
    public Zone zone;

    public ParkingLot(boolean haveSpaceForHandicapped, boolean isGuarded, boolean isPaid, int freeSpaces, boolean isPrivate, boolean haveSpacesForElectrics, Zone zone) {
        this.haveSpaceForHandicapped = haveSpaceForHandicapped;
        this.isGuarded = isGuarded;
        this.isPaid = isPaid;
        this.freeSpaces = freeSpaces;
        this.isPrivate = isPrivate;
        this.haveSpacesForElectrics = haveSpacesForElectrics;
        this.zone = zone;
    }

    public ParkingLot() {

    }
}

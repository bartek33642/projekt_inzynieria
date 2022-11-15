package com.example.maxsatApi.model;

import javax.persistence.*;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ_PARKING_LOT")
    private Integer parkingLotId;
    @Column(insertable = false, updatable = false)
    private Integer zoneId;
    private boolean haveSpaceForHandicapped;
    private boolean isGuarded;
    private boolean isPaid;
    private int freeSpaces;
    private boolean isPrivate;
    private boolean haveSpacesForElectrics;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "zoneId", nullable = false)
    private Zone zone;
    private int points;

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

    public Integer getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Integer parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public boolean getHaveSpaceForHandicapped() {
        return haveSpaceForHandicapped;
    }

    public void setHaveSpaceForHandicapped(boolean haveSpaceForHandicapped) {
        this.haveSpaceForHandicapped = haveSpaceForHandicapped;
    }

    public boolean isGuarded() {
        return isGuarded;
    }

    public void setGuarded(boolean guarded) {
        isGuarded = guarded;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public int getFreeSpaces() {
        return freeSpaces;
    }

    public void setFreeSpaces(int freeSpaces) {
        this.freeSpaces = freeSpaces;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isHaveSpacesForElectrics() {
        return haveSpacesForElectrics;
    }

    public void setHaveSpacesForElectrics(boolean haveSpacesForElectrics) {
        this.haveSpacesForElectrics = haveSpacesForElectrics;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int countOfPicks) {
        this.points = countOfPicks;
    }
}

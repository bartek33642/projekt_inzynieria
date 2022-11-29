package com.example.maxsatApi.dto;

public class MapParkingLotDto {
    private Integer parkingLotId;
    private boolean haveSpaceForHandicapped;
    private boolean isGuarded;
    private boolean isPaid;
    private int freeSpaces;
    private boolean isPrivate;
    private boolean haveSpacesForElectrics;
    private int points;

    public Integer getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Integer parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public boolean isHaveSpaceForHandicapped() {
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

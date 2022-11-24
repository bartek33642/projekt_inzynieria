package com.example.maxsatApi.dto;

public class ParkingLotDto {
    public Integer parkingLotId;
    public boolean haveSpaceForHandicapped;
    public boolean isGuarded;
    public boolean isPaid;
    public int freeSpaces;
    public boolean isPrivate;
    public boolean haveSpacesForElectrics;

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
}

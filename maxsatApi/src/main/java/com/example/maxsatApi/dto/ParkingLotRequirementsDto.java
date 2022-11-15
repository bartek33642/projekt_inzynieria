package com.example.maxsatApi.dto;

public class ParkingLotRequirementsDto {
    private boolean haveSpaceForHandicapped;
    private boolean isGuarded;
    private boolean isPaid;
    private boolean atLeast15FreePlaces;
    private boolean isPrivate;
    private boolean haveSpacesForElectrics;
    private int zoneCordX;
    private int zoneCordY;

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

    public boolean isAtLeast15FreePlaces() {
        return atLeast15FreePlaces;
    }

    public void setAtLeast15FreePlaces(boolean atLeast15FreePlaces) {
        this.atLeast15FreePlaces = atLeast15FreePlaces;
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

    public int getZoneCordX() {
        return zoneCordX;
    }

    public void setZoneCordX(int zoneCordX) {
        this.zoneCordX = zoneCordX;
    }

    public int getZoneCordY() {
        return zoneCordY;
    }

    public void setZoneCordY(int zoneCordY) {
        this.zoneCordY = zoneCordY;
    }
}

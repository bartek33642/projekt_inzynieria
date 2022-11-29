package com.example.maxsatApi.dto;

import java.util.List;

public class ResultZoneDto {
    private int zoneId;
    private int cordX;
    private int cordY;
    private double demandFactor;
    private double accessibilityFactor;
    private double attractivenessFactor;
    private List<ResultParkingLotDto> parkingLot;

    public List<ResultParkingLotDto> getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(List<ResultParkingLotDto> parkingLot) {
        this.parkingLot = parkingLot;
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

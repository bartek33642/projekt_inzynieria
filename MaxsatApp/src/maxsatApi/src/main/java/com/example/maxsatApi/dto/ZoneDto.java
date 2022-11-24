package com.example.maxsatApi.dto;

import com.example.maxsatApi.model.ParkingLot;
import com.example.maxsatApi.model.Zone;

import javax.persistence.*;
import java.util.Set;

public class ZoneDto {
    public int zoneId;
    public int cordX;
    public int cordY;
    public double demandFactor;
    public double accessibilityFactor;
    public double attractivenessFactor;

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

    public Set<ParkingLotDto> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(Set<ParkingLotDto> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public Set<ParkingLotDto> parkingLots;
}

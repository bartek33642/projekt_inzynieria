package com.example.maxsatApi.dto;

import com.example.maxsatApi.model.ParkingLot;
import com.example.maxsatApi.model.Zone;

public class ParkingLotWithScore {
    private ParkingLot parkingLot;
    private int score;

    public ParkingLotWithScore(ParkingLot parkingLot, int score) {
        this.parkingLot = parkingLot;
        this.score = score;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

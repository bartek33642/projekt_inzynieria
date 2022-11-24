package com.example.maxsatApi.dto;

public class AnswerDto {
    private int parkingLotId;
    private int score;

    public int getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(int parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public AnswerDto(int parkingLotId, int score) {
        this.parkingLotId = parkingLotId;
        this.score = score;
    }
}

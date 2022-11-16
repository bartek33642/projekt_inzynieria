package com.example.maxsatApi.dto;

public class AnswerDto {
    private ParkingLotDto firstParkingLot;
    private ParkingLotDto secondParkingLot;
    private ParkingLotDto thirdParkingLot;

    public AnswerDto(ParkingLotDto firstParkingLot, ParkingLotDto secondParkingLot, ParkingLotDto thirdParkingLot) {
        this.firstParkingLot = firstParkingLot;
        this.secondParkingLot = secondParkingLot;
        this.thirdParkingLot = thirdParkingLot;
    }

    public ParkingLotDto getFirstParkingLot() {
        return firstParkingLot;
    }

    public void setFirstParkingLot(ParkingLotDto firstParkingLot) {
        this.firstParkingLot = firstParkingLot;
    }

    public ParkingLotDto getSecondParkingLot() {
        return secondParkingLot;
    }

    public void setSecondParkingLot(ParkingLotDto secondParkingLot) {
        this.secondParkingLot = secondParkingLot;
    }

    public ParkingLotDto getThirdParkingLot() {
        return thirdParkingLot;
    }

    public void setThirdParkingLot(ParkingLotDto thirdParkingLot) {
        this.thirdParkingLot = thirdParkingLot;
    }
}

package com.example.maxsatApi.service;

import com.example.maxsatApi.model.ParkingLot;
import com.example.maxsatApi.repository.ParkingLotRepository;

import java.util.ArrayList;

public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;

    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    public Iterable<ParkingLot> getParkingLots(){return new ArrayList<>();}
}

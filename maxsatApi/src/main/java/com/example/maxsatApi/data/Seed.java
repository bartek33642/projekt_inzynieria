package com.example.maxsatApi.data;

import com.example.maxsatApi.model.ParkingLot;
import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ParkingLotRepository;
import com.example.maxsatApi.repository.ZoneRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Seed {
    private  ZoneRepository zoneRepository;
    private ParkingLotRepository parkingLotRepository;

    public Seed(ZoneRepository zoneRepository, ParkingLotRepository parkingLotRepository) {
        this.zoneRepository = zoneRepository;
        this.parkingLotRepository = parkingLotRepository;
    }


    public void seedData(){
        if (zoneRepository.count() == 0) {
            List<Zone> zones = new ArrayList<>();
            Random random = new Random();
            int cordXWidth = 8;
            int cordYWidth = 5;
            for (int cordX = 0; cordX <= cordXWidth; ++cordX) {
                for (int cordY = 0; cordY <= cordYWidth; ++cordY) {
                    if ((cordY == 0 && cordX >= 1 && cordX <= 7) || cordY == 1 || cordY == 2 || (cordY == 3 && cordX >= 2 && cordX <= 7) || (cordY == 4 && cordX >= 2 && cordX <= 6) || (cordY == 5 && cordX ==2)) {
                        double demandFactor = random.nextInt(100) / 100.0;
                        double accessibilityFactor = random.nextInt(100) / 100.0;
                        double attractivenessFactor = random.nextInt(100) / 100.0;
                        zones.add(new Zone(cordX, cordY, demandFactor, accessibilityFactor, attractivenessFactor));
                    }
                }
            }
            zoneRepository.saveAll(zones);
        }
        if (parkingLotRepository.count() == 0) {
            List<Zone> zones = (List<Zone>) zoneRepository.findAll();
            for (int zoneIndex = 0; zoneIndex < zones.size(); ++zoneIndex) {
                if (zones.get(zoneIndex).parkingLots.size() == 0) {
                    List<ParkingLot> parkingLots = new ArrayList<>();
                    Random random = new Random();
                    int numberOfParkingLots = random.nextInt(4) + 1;
                    for (int parkingIndex = 0; parkingIndex < numberOfParkingLots; ++parkingIndex) {
                        Zone zone = ((List<Zone>) zoneRepository.findAll()).get(zoneIndex);
                        boolean haveSpaceForHandicapped = random.nextBoolean();
                        boolean isGuarded = random.nextBoolean();
                        boolean isPaid = random.nextBoolean();
                        int freeSpaces = random.nextInt(10);
                        boolean isPrivate = random.nextBoolean();
                        boolean haveSpacesForElectrics = random.nextBoolean();
                        parkingLots.add(new ParkingLot(haveSpaceForHandicapped, isGuarded, isPaid, freeSpaces, isPrivate, haveSpacesForElectrics, zone));
                    }
                    parkingLotRepository.saveAll(parkingLots);
                }
            }
        }
    }
}

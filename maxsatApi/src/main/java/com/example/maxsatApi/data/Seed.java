package com.example.maxsatApi.data;

import com.example.maxsatApi.model.ParkingLot;
import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ParkingLotRepository;
import com.example.maxsatApi.repository.ZoneRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.System.in;

public class Seed {
    private  ZoneRepository zoneRepository;
    private ParkingLotRepository parkingLotRepository;

    public Seed(ZoneRepository zoneRepository, ParkingLotRepository parkingLotRepository) {
        this.zoneRepository = zoneRepository;
        this.parkingLotRepository = parkingLotRepository;
    }


    public void seedData(){
        generateZonesIfNotExists();
        generateParkingLotsIfNotExists();
    }

    private void generateZonesIfNotExists() {
        if (zoneRepository.count() == 0) {
            List<Zone> zones = new ArrayList<>();
            Random random = new Random();
            int cordXWidth = 14;
            int cordYWidth = 6;
            for (int cordX = 0; cordX <= cordXWidth; ++cordX) {
                for (int cordY = 0; cordY <= cordYWidth; ++cordY) {
                    double demandFactor = random.nextInt(100) / 100.0;
                    double accessibilityFactor = random.nextInt(100) / 100.0;
                    double attractivenessFactor = random.nextInt(100) / 100.0;
                    zones.add(new Zone(cordX, cordY, demandFactor, accessibilityFactor, attractivenessFactor));
                }
            }
            zoneRepository.saveAll(zones);
        }
    }

    private void generateParkingLotsIfNotExists() {
        if (parkingLotRepository.count() == 0) {
            List<Zone> zones = (List<Zone>) zoneRepository.findAll();
            for (Zone zone : zones) {
                if (zone.getParkingLots().size() == 0) {
                    List<ParkingLot> parkingLots = new ArrayList<>();
                    Random random = new Random();
                    int numberOfParkingLots = random.nextInt(1,5) ;
                    for (int parkingIndex = 0; parkingIndex < numberOfParkingLots; ++parkingIndex) {
                        boolean haveSpaceForHandicapped = random.nextBoolean();
                        boolean isGuarded = random.nextBoolean();
                        boolean isPaid = random.nextBoolean();
                        int maximalCountOfFreeSpaces = 40;
                        int freeSpaces = random.nextInt(maximalCountOfFreeSpaces);
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

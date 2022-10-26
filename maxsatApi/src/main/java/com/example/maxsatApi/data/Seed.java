package com.example.maxsatApi.data;

import com.example.maxsatApi.model.ParkingLot;
import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ParkingLotRepository;
import com.example.maxsatApi.repository.ZoneRepository;
import com.example.maxsatApi.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
        List<Zone> zones;
        if (zoneRepository.count() == 0) {
            zones = new ArrayList<>();
            Random random = new Random();
            int cordXWidth = 10;
            int cordYWidth = 10;
            for (int cordX = 0; cordX < cordXWidth; ++cordX) {
                for (int cordY = 0; cordY < cordYWidth; ++cordY) {
                    double demandFactor = random.nextInt(100) / 100.0;
                    double accessibilityFactor = random.nextInt(100) / 100.0;
                    double attractivenessFactor = random.nextInt(100) / 100.0;
                    zones.add(new Zone(cordX, cordY, demandFactor, accessibilityFactor, attractivenessFactor));
                }
            }
            zoneRepository.saveAll(zones);
        }
        if (parkingLotRepository.count() == 0) {
            Zone zone = ((List<Zone>) zoneRepository.findAll()).get(0);
            List<ParkingLot> parkingLots = new ArrayList<>();
            Random random = new Random();
            boolean haveSpaceForHandicapped = random.nextBoolean();
            boolean isGuarded = random.nextBoolean();
            boolean isPaid = random.nextBoolean();
            int freeSpaces = random.nextInt(10);
            boolean isPrivate = random.nextBoolean();
            boolean haveSpacesForElectrics = random.nextBoolean();
            parkingLots.add(new ParkingLot(haveSpaceForHandicapped, isGuarded, isPaid, freeSpaces, isPrivate, haveSpacesForElectrics, zone));
            parkingLotRepository.saveAll(parkingLots);

        }
        Iterable<Zone> zones2 = zoneRepository.findAll();
    }
}

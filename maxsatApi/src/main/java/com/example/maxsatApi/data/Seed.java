package com.example.maxsatApi.data;

import com.example.maxsatApi.model.Zone;
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

    public Seed(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }


    public void seedData(){
        if (zoneRepository.count() == 0) {
            List<Zone> zones = new ArrayList<>();
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
    }
}

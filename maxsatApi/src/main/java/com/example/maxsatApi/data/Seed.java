package com.example.maxsatApi.data;

import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ZoneRepository;
import com.example.maxsatApi.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class Seed {
    private  ZoneRepository zoneRepository;

    public Seed(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public void seedData(){
        if (zoneRepository.count() == 0) {
            List<Zone> zones = new ArrayList<>();
            int cordXWidth = 10;
            int cordYWidth = 10;
            double demandFactor = Math.round((Math.random() * (0.99 - 0.01) + 0.01) * 100.0) / 100.0;
            double accessibilityFactor = Math.round((Math.random() * (0.99 - 0.01) + 0.01) * 100.0) / 100.0;
            double attractivenessFactor = Math.round((Math.random() * (0.99 - 0.01) + 0.01) * 100.0) / 100.0;
            for (int cordX = 0; cordX < cordXWidth; ++cordX) {
                for (int cordY = 0; cordY < cordYWidth; ++cordY) {
                    zones.add(new Zone(cordX, cordY, demandFactor, accessibilityFactor, attractivenessFactor));
                }
            }
            zoneRepository.saveAll(zones);
        }
    }
}

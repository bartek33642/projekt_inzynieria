package com.example.maxsatApi.data;

import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ZoneRepository;
import com.example.maxsatApi.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Seed {

    private  ZoneRepository zoneRepository;

    public Seed(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public void seedData(){
        if (zoneRepository.count() == 0) {
            for (int x = 0; x < 10; ++x) {
                for (int y = 0; y < 10; ++y) {
                    Zone zone = new Zone(x, y, Math.round((Math.random() * (0.99 - 0.01) + 0.01) * 100.0) / 100.0, Math.round((Math.random() * (0.99 - 0.01) + 0.01) * 100.0) / 100.0, Math.round((Math.random() * (0.99 - 0.01) + 0.01) * 100.0) / 100.0);
                    zoneRepository.save(zone);
                }
            }
        }
    }
}

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
            Zone zone = new Zone(0, 0, 0.5, 0.25, 0.74);
            zoneRepository.save(zone);
        }
    }
}

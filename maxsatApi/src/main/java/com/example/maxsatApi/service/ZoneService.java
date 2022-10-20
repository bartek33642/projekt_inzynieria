package com.example.maxsatApi.service;

import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoneService {

    private final ZoneRepository zoneRepository;

    public ZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public Zone SaveZone(Zone zoneToSave) {
        return zoneRepository.save(zoneToSave);
    }
}

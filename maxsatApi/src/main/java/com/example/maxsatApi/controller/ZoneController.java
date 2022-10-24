package com.example.maxsatApi.controller;

import com.example.maxsatApi.dto.ParkingLotRequirementsDto;
import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ZoneController {

    @Autowired
    public ZoneRepository zoneRepository;
    @GetMapping(value = "/zones")
    public Iterable<Zone> getZones(){
        return zoneRepository.findAll();
    }

    @GetMapping(value = "/zones/{id}")
    public Optional<Zone> getZoneById(@PathVariable Integer id){
        return zoneRepository.findById(id);
    }

    @GetMapping(value = "/requiredzone")
    public Zone getZoneById(@RequestBody ParkingLotRequirementsDto parkingLotRequirementsDto){
        //TODO: maxsat4j things
        return new Zone();
    }
    //TODO: Add DTOs
}

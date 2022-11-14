package com.example.maxsatApi.controller;

import com.example.maxsatApi.dto.ParkingLotRequirementsDto;
import com.example.maxsatApi.dto.ZoneDto;
import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ZoneRepository;
import com.example.maxsatApi.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ZoneController {

    @Autowired
    public ZoneRepository zoneRepository;
    @Autowired
    public ZoneService zoneService;
    @GetMapping(value = "/zones")
    public Iterable<ZoneDto> getZones(){
        return zoneService.getZones();
    }

    @GetMapping(value = "/zones/{id}")
    public ZoneDto getZoneById(@PathVariable Integer id){
        return zoneService.getZone(id);
    }

    @GetMapping(value = "/requiredzone")
    public Zone getZoneById(@RequestBody ParkingLotRequirementsDto parkingLotRequirementsDto){
        //TODO: maxsat4j things
        return new Zone();
    }
    //TODO: Add DTOs
}

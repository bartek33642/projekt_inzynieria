package com.example.maxsatApi.controller;

import com.example.maxsatApi.dto.*;
import com.example.maxsatApi.extension.Solver;
import com.example.maxsatApi.model.ParkingLot;
import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ParkingLotRepository;
import com.example.maxsatApi.repository.ZoneRepository;
import com.example.maxsatApi.service.ParkingLotService;
import com.example.maxsatApi.service.ZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
public class ZoneController {

    public final ZoneRepository zoneRepository;
    private final ParkingLotService parkingLotService;
    public ParkingLotRepository parkingLotRepository;
    public final ZoneService zoneService;

    public ZoneController(ZoneRepository zoneRepository, ZoneService zoneService, ParkingLotRepository parkingLotRepository, ParkingLotService parkingLotService) {
        this.zoneRepository = zoneRepository;
        this.zoneService = zoneService;
        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotService = parkingLotService;

    }

    @GetMapping(value = "/zones")
    public Iterable<ZoneDto> getZones(){
        return zoneService.getZones();
    }

    @GetMapping(value = "/zones/{id}")
    public ZoneDto getZoneById(@PathVariable Integer id){
        return zoneService.getZone(id);
    }

    @GetMapping(value = "/requiredparkinglot")
    public ResponseEntity<AnswerDto> getRequiredParkingLots(@RequestBody ParkingLotRequirementsDto parkingLotRequirementsDto) throws Exception {
        AnswerDto answerDto = parkingLotService.getRequiredParkingLots(parkingLotRequirementsDto);
        return new ResponseEntity<>(answerDto, HttpStatus.OK);
    }
}

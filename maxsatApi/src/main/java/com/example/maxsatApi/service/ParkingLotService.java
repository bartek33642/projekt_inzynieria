package com.example.maxsatApi.service;

import com.example.maxsatApi.dto.AnswerDto;
import com.example.maxsatApi.dto.ParkingLotDto;
import com.example.maxsatApi.dto.ParkingLotRequirementsDto;
import com.example.maxsatApi.dto.ParkingLotWithScore;
import com.example.maxsatApi.extension.Solver;
import com.example.maxsatApi.model.ParkingLot;
import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ParkingLotRepository;
import com.example.maxsatApi.repository.ZoneRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;
    private final ZoneRepository zoneRepository;
    private final Mapper mapper;

    public ParkingLotService(ParkingLotRepository parkingLotRepository, ZoneRepository zoneRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.zoneRepository = zoneRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    public AnswerDto getRequiredParkingLots(ParkingLotRequirementsDto parkingLotRequirementsDto) throws Exception {
        List<ParkingLot> preferredParkingLots = parkingLotRepository.findThreeParkingLotsWithMostPoints();
        List<Zone> allZones = (List<Zone>) zoneRepository.findAll();
        List<ParkingLot> allParkingLots = (List<ParkingLot>) parkingLotRepository.findAll();
        Solver solver = new Solver(allZones, parkingLotRequirementsDto,preferredParkingLots);
        List<ParkingLotWithScore> parkingLotsWithScores = new ArrayList<>();
        allParkingLots.forEach(parkingLot ->
                parkingLotsWithScores.add(new ParkingLotWithScore(
                        parkingLot, solver.test(parkingLot)
                )));
        parkingLotsWithScores.sort(Comparator.comparingInt(ParkingLotWithScore::getScore).reversed());
        List<ParkingLotWithScore> bestResults = parkingLotsWithScores.subList(0,3);
        ParkingLot firstParkingLot = bestResults.get(0).getParkingLot();
        int firstParkingLotPoints = firstParkingLot.getPoints()+3;
        firstParkingLot.setPoints(firstParkingLotPoints);
        ParkingLot secondParkingLot = bestResults.get(1).getParkingLot();
        int secondParkingLotPoints = secondParkingLot.getPoints()+2;
        secondParkingLot.setPoints(secondParkingLotPoints);
        ParkingLot thirdParkingLot = bestResults.get(2).getParkingLot();
        int thirdParkingLotPoints = thirdParkingLot.getPoints()+1;
        thirdParkingLot.setPoints(thirdParkingLotPoints);
        parkingLotRepository.saveAll(List.of(firstParkingLot,secondParkingLot,thirdParkingLot));
        ParkingLotDto firstParkingLotDto = mapper.map(firstParkingLot, ParkingLotDto.class);
        ParkingLotDto secondParkingLotDto = mapper.map(secondParkingLot, ParkingLotDto.class);
        ParkingLotDto thirdParkingLotDto = mapper.map(thirdParkingLot, ParkingLotDto.class);
        AnswerDto answerDto = new AnswerDto(
                firstParkingLotDto,
                secondParkingLotDto,
                thirdParkingLotDto
        );
        return answerDto;
    }

}

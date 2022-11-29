package com.example.maxsatApi.service;

import com.example.maxsatApi.dto.AnswerDto;
import com.example.maxsatApi.dto.ParkingLotRequirementsDto;
import com.example.maxsatApi.dto.ParkingLotWithScore;
import com.example.maxsatApi.dto.ResultParkingLotDto;
import com.example.maxsatApi.extension.Solver;
import com.example.maxsatApi.model.Answer;
import com.example.maxsatApi.model.ParkingLot;
import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ParkingLotRepository;
import com.example.maxsatApi.repository.ZoneRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

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

    public AnswerDto getAnswerFromSolver(ParkingLotRequirementsDto parkingLotRequirementsDto) throws Exception {
        List<ParkingLot> preferredParkingLots = parkingLotRepository.findThreeParkingLotsWithMostPoints();
        List<Zone> allZones = (List<Zone>) zoneRepository.findAll();
        Solver solver = new Solver(allZones, parkingLotRequirementsDto,preferredParkingLots);
        Answer answer = solver.createAnswerWithScore();
        List<ParkingLotWithScore> bestParkingLotsWithScores = answer.getParkingLotWithScores();
        ParkingLotWithScore firstResult = bestParkingLotsWithScores.get(0);
        ParkingLotWithScore secondResult = bestParkingLotsWithScores.get(1);
        ParkingLotWithScore thirdResult = bestParkingLotsWithScores.get(2);
        ParkingLot firstParkingLot = firstResult.getParkingLot();
        ParkingLot secondParkingLot = secondResult.getParkingLot();
        ParkingLot thirdParkingLot = thirdResult.getParkingLot();
        updateScoresOfBestParkingLots(firstParkingLot, secondParkingLot, thirdParkingLot);
        ResultParkingLotDto bestParkingDto = new ResultParkingLotDto(firstResult.getParkingLot(),firstResult.getScore());
        return new AnswerDto(bestParkingDto, answer.getResultZoneDtos());
    }

    private void updateScoresOfBestParkingLots(ParkingLot firstParkingLot, ParkingLot secondParkingLot, ParkingLot thirdParkingLot){
        int firstParkingLotPoints = firstParkingLot.getPoints()+3;
        firstParkingLot.setPoints(firstParkingLotPoints);
        int secondParkingLotPoints = secondParkingLot.getPoints()+2;
        secondParkingLot.setPoints(secondParkingLotPoints);
        int thirdParkingLotPoints = thirdParkingLot.getPoints()+1;
        thirdParkingLot.setPoints(thirdParkingLotPoints);
        parkingLotRepository.saveAll(List.of(firstParkingLot,secondParkingLot,thirdParkingLot));
    }

}

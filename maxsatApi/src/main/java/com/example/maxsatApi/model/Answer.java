package com.example.maxsatApi.model;

import com.example.maxsatApi.dto.ParkingLotWithScore;
import com.example.maxsatApi.dto.ResultZoneDto;

import java.util.List;

public class Answer {
    private List<ParkingLotWithScore> parkingLotWithScores;
    private List<ResultZoneDto> resultZoneDtos;

    public Answer(List<ParkingLotWithScore> parkingLotWithScores, List<ResultZoneDto> resultZoneDtos) {
        this.parkingLotWithScores = parkingLotWithScores;
        this.resultZoneDtos = resultZoneDtos;
    }

    public List<ParkingLotWithScore> getParkingLotWithScores() {
        return parkingLotWithScores;
    }

    public void setParkingLotWithScores(List<ParkingLotWithScore> parkingLotWithScores) {
        this.parkingLotWithScores = parkingLotWithScores;
    }

    public List<ResultZoneDto> getResultZoneDtos() {
        return resultZoneDtos;
    }

    public void setResultZoneDtos(List<ResultZoneDto> resultZoneDtos) {
        this.resultZoneDtos = resultZoneDtos;
    }
}

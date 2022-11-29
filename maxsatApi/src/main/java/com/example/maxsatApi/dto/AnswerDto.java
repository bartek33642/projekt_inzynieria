package com.example.maxsatApi.dto;

import java.util.List;

public class AnswerDto {
    private ResultParkingLotDto bestParkingLotDto;
    private List<ResultZoneDto> resultZoneDtos;

    public AnswerDto(ResultParkingLotDto bestParkingLotDto, List<ResultZoneDto> resultZoneDtos) {
        this.bestParkingLotDto = bestParkingLotDto;
        this.resultZoneDtos = resultZoneDtos;
    }

    public ResultParkingLotDto getBestParkingLotDto() {
        return bestParkingLotDto;
    }

    public void setBestParkingLotDto(ResultParkingLotDto bestParkingLotDto) {
        this.bestParkingLotDto = bestParkingLotDto;
    }

    public List<ResultZoneDto> getResultZoneDtos() {
        return resultZoneDtos;
    }

    public void setResultZoneDtos(List<ResultZoneDto> resultZoneDtos) {
        this.resultZoneDtos = resultZoneDtos;
    }
}

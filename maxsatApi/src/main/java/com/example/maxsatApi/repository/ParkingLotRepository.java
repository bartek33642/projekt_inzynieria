package com.example.maxsatApi.repository;

import com.example.maxsatApi.model.ParkingLot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository extends CrudRepository<ParkingLot, Integer> {
    List<ParkingLot> findByZoneId(Integer zoneId);
}

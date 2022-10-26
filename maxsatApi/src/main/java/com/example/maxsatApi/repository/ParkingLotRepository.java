package com.example.maxsatApi.repository;

import com.example.maxsatApi.model.ParkingLot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends CrudRepository<ParkingLot, Integer> {
}

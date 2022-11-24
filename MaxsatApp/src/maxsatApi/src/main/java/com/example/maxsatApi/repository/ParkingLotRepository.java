package com.example.maxsatApi.repository;

import com.example.maxsatApi.model.ParkingLot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository extends CrudRepository<ParkingLot, Integer> {
    @Query(nativeQuery = true, value = "select * from parking_lot pl order by pl.points desc limit 3")
    List<ParkingLot> findThreeParkingLotsWithMostPoints();
}

package com.example.maxsatApi.repository;

import com.example.maxsatApi.model.Zone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ZoneRepository extends CrudRepository<Zone, Integer> {

}

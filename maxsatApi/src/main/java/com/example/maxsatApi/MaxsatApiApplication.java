package com.example.maxsatApi;

import com.example.maxsatApi.data.Seed;
import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ParkingLotRepository;
import com.example.maxsatApi.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MaxsatApiApplication implements CommandLineRunner {

	@Autowired
	public ZoneRepository zoneRepository;
	@Autowired
	public ParkingLotRepository parkingLotRepository;
	public static void main(String[] args) {
		SpringApplication.run(MaxsatApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new Seed(zoneRepository, parkingLotRepository).seedData();
	}
}

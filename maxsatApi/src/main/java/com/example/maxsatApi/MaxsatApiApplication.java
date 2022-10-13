package com.example.maxsatApi;

import com.example.maxsatApi.data.DatabaseGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MaxsatApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaxsatApiApplication.class, args);
		DatabaseGenerator databaseGenerator = new DatabaseGenerator();
		databaseGenerator.createDatabase();
		databaseGenerator.createTables();
	}

}

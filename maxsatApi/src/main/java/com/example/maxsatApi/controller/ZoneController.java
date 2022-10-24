package com.example.maxsatApi.controller;

import com.example.maxsatApi.model.Zone;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZoneController {
    @GetMapping(value = "/zones")
    public Iterable<Zone> getZones(){

        return
    }
}

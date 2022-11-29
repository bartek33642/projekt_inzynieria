package com.example.maxsatApi.service;

import com.example.maxsatApi.dto.MapZoneDto;
import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ZoneRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final Mapper mapper;

    public ZoneService(ZoneRepository zoneRepository) {

        this.zoneRepository = zoneRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    public Iterable<MapZoneDto> getZones() {
        Iterable<Zone> zones = zoneRepository.findAll();
        List<MapZoneDto> zoneDtos = new ArrayList<MapZoneDto>();
        for(Zone zone : zones){
            MapZoneDto zoneDto = mapper.map(zone, MapZoneDto.class);
            zoneDtos.add(zoneDto);
        }
        return zoneDtos;
    }

    public MapZoneDto getZone(Integer id) {
        Zone zone = zoneRepository.findById(id).get();
        return mapper.map(zone, MapZoneDto.class);
    }
}

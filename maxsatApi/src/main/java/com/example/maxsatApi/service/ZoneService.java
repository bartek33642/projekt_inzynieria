package com.example.maxsatApi.service;

import com.example.maxsatApi.dto.ZoneDto;
import com.example.maxsatApi.model.Zone;
import com.example.maxsatApi.repository.ZoneRepository;
import org.dozer.DozerBeanMapper;
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

    public Iterable<ZoneDto> getZones() {
        Iterable<Zone> zones = zoneRepository.findAll();
        List<ZoneDto> zoneDtos = new ArrayList<ZoneDto>();
        for(Zone zone : zones){
            ZoneDto zoneDto = mapper.map(zone, ZoneDto.class);
            zoneDtos.add(zoneDto);
        }
        return zoneDtos;
    }

    public ZoneDto getZone(Integer id) {
        Zone zone = zoneRepository.findById(id).get();
        return mapper.map(zone, ZoneDto.class);
    }
}

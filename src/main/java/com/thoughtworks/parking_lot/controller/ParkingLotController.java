package com.thoughtworks.parking_lot.controller;


import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingLotController {


    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @PostMapping("/parkinglots")
    public ResponseEntity<ParkingLot> createParkinglot(@RequestBody ParkingLot parkingLot){
        ParkingLot newParkingLot = parkingLotRepository.save(parkingLot);
        return ResponseEntity.ok(newParkingLot);
    }

    @DeleteMapping("/parkinglots/{id}")
    public String deleteeParkinglot(@PathVariable(value = "id") long id){
        parkingLotRepository.deleteById(id);
        return "delete scuessfully";
    }

    @GetMapping("/parkinglots")
    public ResponseEntity<List<ParkingLot>> getParkinglots(@RequestParam(value = "page",required = false,defaultValue = "0")int page){
        if(page == 0){
            return ResponseEntity.ok(parkingLotRepository.findAll());
        }
        else {
            return ResponseEntity.ok(parkingLotRepository.findAll(PageRequest.of(page-1,15)).getContent());
        }
    }
}

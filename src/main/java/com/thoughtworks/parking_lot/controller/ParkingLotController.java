package com.thoughtworks.parking_lot.controller;


import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

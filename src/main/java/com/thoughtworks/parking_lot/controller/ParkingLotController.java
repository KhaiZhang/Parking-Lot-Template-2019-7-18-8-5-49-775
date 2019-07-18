package com.thoughtworks.parking_lot.controller;


import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingLotController {


    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @PostMapping("/parkinglots")
    public ResponseEntity<ParkingLot> createParkinglot(@RequestBody ParkingLot parkingLot){
        ParkingLot newParkingLot = parkingLotRepository.save(parkingLot);
        return ResponseEntity.ok(newParkingLot);
    }
}

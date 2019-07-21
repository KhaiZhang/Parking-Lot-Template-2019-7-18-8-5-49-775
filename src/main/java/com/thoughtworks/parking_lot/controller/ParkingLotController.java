package com.thoughtworks.parking_lot.controller;


import com.thoughtworks.parking_lot.Exception.ParkingLotIsFullException;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingLotController {


    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingLotService parkingLotService;

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

    @GetMapping("/parkinglots/{id}")
    public ResponseEntity<ParkingLot> getParkinglotById(@PathVariable(value = "id") long id){
        return ResponseEntity.ok(parkingLotRepository.findById(id).orElse(null));
    }

    @PutMapping("/parkinglots")
    public ResponseEntity<Integer> updateCapacityById(@RequestBody ParkingLot parkingLot){
        return ResponseEntity.ok(parkingLotRepository.updateCapacityById(parkingLot));
    }

    @PostMapping("/parkinglots/{id}/parkingOrders")
    public ResponseEntity<ParkingOrder> createNewOrder(@PathVariable(value = "id") long id,@RequestBody ParkingOrder parkingOrder) throws ParkingLotIsFullException{
        ParkingOrder newParkingOrder = parkingLotService.addNewPakringOrderById(id,parkingOrder);
        if(newParkingOrder == null ) throw new ParkingLotIsFullException("parking lot is full");
        else return ResponseEntity.ok(newParkingOrder);
    }

    @ExceptionHandler(ParkingLotIsFullException.class)
    public String returnParkingLotIsFull(ParkingLotIsFullException ex){
        return "'error':"+ex.getMessage();
    }
}

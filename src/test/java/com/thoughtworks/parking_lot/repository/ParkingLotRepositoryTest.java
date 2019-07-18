package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingLot;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotRepositoryTest {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Test
    public void should_update_capacity_by_id() {
        ParkingLot parkingLot = parkingLotRepository.findAll().get(2);
        parkingLot.setCapacity(33);
        parkingLotRepository.updateCapacityById(parkingLot);
        Assertions.assertEquals(33,parkingLotRepository.findById(parkingLot.getId()).get().getCapacity());
    }
}
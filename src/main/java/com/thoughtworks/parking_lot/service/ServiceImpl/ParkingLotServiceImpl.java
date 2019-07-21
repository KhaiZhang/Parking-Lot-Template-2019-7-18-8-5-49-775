package com.thoughtworks.parking_lot.service.ServiceImpl;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.repository.ParkingOrderRepository;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Override
    public ParkingOrder addNewPakringOrderById(long id,ParkingOrder order) {
        ParkingLot parkingLot = parkingLotRepository.findAll()
                .stream()
                .filter(element -> element.getId() == id)
                .findAny()
                .orElse(null);
        int size = parkingLot.getParkingOrders()
                .stream()
                .filter(element -> element.getStatus() == 1)
                .collect(Collectors.toList()
                ).size();
        if(size < parkingLot.getCapacity()){
            ParkingOrder parkingOrder = new ParkingOrder();
            parkingOrder.setCarNumber(UUID.randomUUID().toString());
            parkingOrder.setParkingLot(parkingLot);
            parkingOrder.setParkingLotName(parkingLot.getName());
            parkingOrder.setCarNumber(order.getCarNumber());
            parkingOrder.setStatus(1);
            parkingOrder.setStartingTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            ParkingOrder savedParkingOder = parkingOrderRepository.save(parkingOrder);
            return  savedParkingOder;
        }
        else return null;
    }
}

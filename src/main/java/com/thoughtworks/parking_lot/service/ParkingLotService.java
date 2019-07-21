package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingOrder;

public interface ParkingLotService {
    public ParkingOrder addNewPakringOrderById(long id,ParkingOrder parkingOrder);
}

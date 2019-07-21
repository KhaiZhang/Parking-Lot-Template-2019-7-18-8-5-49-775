package com.thoughtworks.parking_lot.Exception;

public class ParkingLotIsFullException extends Exception {
    public ParkingLotIsFullException(String msg){
        super(msg);
    }
}

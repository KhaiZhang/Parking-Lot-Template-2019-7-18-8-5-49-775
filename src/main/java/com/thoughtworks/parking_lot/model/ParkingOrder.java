package com.thoughtworks.parking_lot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ParkingOrder {
    @Id
    @GeneratedValue
    private long orderId;

    private String ParkingLotName;

    private String carNumber;

    private String startingTime;

    private String endingTime;

    private int status;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private ParkingLot parkingLot;

    public ParkingOrder() {
    }

    public ParkingOrder(String parkingLotName, String carNumber, String startingTime, int status, ParkingLot parkingLot) {
        ParkingLotName = parkingLotName;
        this.carNumber = carNumber;
        this.startingTime = startingTime;
        this.status = status;
        this.parkingLot = parkingLot;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getParkingLotName() {
        return ParkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        ParkingLotName = parkingLotName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}

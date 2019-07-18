package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {

    @Modifying
    @Transactional
    @Query("update ParkingLot set capacity = :#{#parkinglot.capacity} where id = :#{#parkinglot.id}")
    public int updateCapacityById(@Param(value = "parkinglot")ParkingLot parkinglot);
}

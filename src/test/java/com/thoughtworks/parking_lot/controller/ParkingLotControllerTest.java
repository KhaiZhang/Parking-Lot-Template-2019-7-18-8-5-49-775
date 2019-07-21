package com.thoughtworks.parking_lot.controller;

import com.google.gson.Gson;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.hibernate.criterion.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingLotRepository parkingLotRepository;

    @MockBean
    private ParkingLotService parkingLotService;

    @org.junit.Test
    public void should_sava_a_new_parkinglot() throws Exception{
        Gson gson = new Gson();
        String name = UUID.randomUUID().toString();
        ParkingLot parkingLot = new ParkingLot(name, 10, "where");
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(parkingLot);
        mockMvc.perform(post("/parkinglots").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(parkingLot)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }

    @org.junit.Test
    public void should_delete_a_parkinglot() throws Exception{
        Gson gson = new Gson();
        String name = UUID.randomUUID().toString();
        ParkingLot parkingLot = new ParkingLot(name, 12, "where12");
        parkingLot.setId(10);
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(parkingLot);
        ParkingLot save = parkingLotRepository.save(parkingLot);
        mockMvc.perform(delete("/parkinglots/{id}",save.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @org.junit.Test
    public void should_return_parkinglots() throws Exception{
        int size = parkingLotRepository.findAll().size();
        mockMvc.perform(get("/parkinglots"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(size));
    }

    @org.junit.Test
    public void should_return_parkinglots_by_page() throws Exception{
        ParkingLot firstParkingLot = new ParkingLot("xxx", 32, "where");
        ParkingLot secondParkingLot = new ParkingLot("dsd", 31,"root");
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);
        Page<ParkingLot> page = new PageImpl<>(parkingLots);
        when(parkingLotRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get("/parkinglots?page={page}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(page.getContent().size()));
    }

    @org.junit.Test
    public void should_return_parkinglot_by_id() throws Exception{
        ParkingLot parkingLot = new ParkingLot("X1", 15, "noWhere");
        when(parkingLotRepository.findById(anyLong())).thenReturn(java.util.Optional.of(parkingLot));
        mockMvc.perform(get("/parkinglots/{id}",parkingLot.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(parkingLot.getName()));
    }

    @Test
    public void should_update_parkinglot_capacity() throws Exception{
        Gson gson = new Gson();
        ParkingLot parkingLot = new ParkingLot("XX", 90, "where2");
        when(parkingLotRepository.updateCapacityById(any(ParkingLot.class))).thenReturn(1);
        mockMvc.perform(post("/parkinglots").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(parkingLot)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_create_new_Order()  throws Exception{
        ParkingLot parkingLot = new ParkingLot("1k", 10, "where");
        parkingLot.setId(10);
        parkingLot.setParkingOrders(null);
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        ParkingOrder parkingOrder = new ParkingOrder("1k", "C:123", date, 1, parkingLot);
        when(parkingLotService.addNewPakringOrderById(anyLong(),any(ParkingOrder.class))).thenReturn(parkingOrder);

        mockMvc.perform(post("/parkinglots/{id}/parkingOrders",parkingLot.getId()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(parkingOrder)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parkingLotName").value(parkingOrder.getParkingLotName()));
    }

    @Test
    public void should_return_error_parkinglot_is_full()  throws Exception{
        ParkingLot parkingLot = new ParkingLot("1k", 10, "where");
        parkingLot.setId(10);
        parkingLot.setParkingOrders(null);
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        ParkingOrder parkingOrder = new ParkingOrder("1k", "C:123", date, 1, parkingLot);
        when(parkingLotService.addNewPakringOrderById(anyLong(),any(ParkingOrder.class))).thenReturn(null);

        mockMvc.perform(post("/parkinglots/{id}/parkingOrders",parkingLot.getId()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(parkingOrder)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("'error':parking lot is full"));
    }

    @Test
    public void should_update_order_when_fetch_car()  throws Exception{
        ParkingLot parkingLot = new ParkingLot("1k", 10, "where");
        parkingLot.setId(10);
        parkingLot.setParkingOrders(null);
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        ParkingOrder parkingOrder = new ParkingOrder("1k", "D:1112", date, 0, parkingLot);
        parkingOrder.setEndingTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        when(parkingLotService.updateOrderByCarNumber(anyLong(),anyString())).thenReturn(parkingOrder);

        mockMvc.perform(put("/parkinglots/{id}/parkingOrders",parkingLot.getId()).param("carNumber","D:1112"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.endingTime").exists());
    }
}
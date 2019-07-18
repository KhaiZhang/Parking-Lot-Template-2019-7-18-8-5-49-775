package com.thoughtworks.parking_lot;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Test
    public void should_sava_a_new_parkinglot() throws Exception{
        Gson gson = new Gson();
        String name = UUID.randomUUID().toString();
        ParkingLot parkingLot = new ParkingLot(name, 10, "where");

        mockMvc.perform(post("/parkinglots").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(parkingLot)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void should_delete_a_parkinglot() throws Exception{
        Gson gson = new Gson();
        String name = UUID.randomUUID().toString();
        ParkingLot parkingLot = new ParkingLot(name, 12, "where12");
        ParkingLot save = parkingLotRepository.save(parkingLot);
        long id = save.getId();
        mockMvc.perform(delete("/parkinglots/{id}",id))
                .andDo(print())
                .andExpect(content().string("delete scuessfully"));
    }

    @Test
    public void should_return_parkinglots() throws Exception{
        int size = parkingLotRepository.findAll().size();
        mockMvc.perform(get("/parkinglots"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(size));
    }

    @Test
    public void should_return_parkinglots_by_page() throws Exception{
        int size = parkingLotRepository.findAll().size();
        int result=15;
        if(size<15) result = size;
        mockMvc.perform(get("/parkinglots?page={page}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(result));
    }

    @Test
    public void should_return_parkinglot_by_id() throws Exception{
        ParkingLot parkingLot = parkingLotRepository.findAll().get(0);
        mockMvc.perform(get("/parkinglots/{id}",parkingLot.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(parkingLot.getName()));
    }

    @Test
    public void should_update_parkinglot_capacity() throws Exception{
        Gson gson = new Gson();
        ParkingLot parkingLot = parkingLotRepository.findAll().get(0);
        parkingLot.setCapacity(90);
        mockMvc.perform(post("/parkinglots").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(parkingLot)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.capacity").value(90));
    }
}

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}

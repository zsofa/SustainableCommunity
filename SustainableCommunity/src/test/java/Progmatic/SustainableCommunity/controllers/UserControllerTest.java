package Progmatic.SustainableCommunity.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @Test
    void getUserNameUnique() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/user/isUsernameUnique"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn(); //lehet más jobb a 200-nál
    }

    @Test
    void createUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/create"))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
    }



}
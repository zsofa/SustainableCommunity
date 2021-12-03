package Progmatic.SustainableCommunity.controllers;

import Progmatic.SustainableCommunity.models.UserAuthorities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @Test
    void getUserNameUnique() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("localhost:8080/user/isUsernameUnique"))
                .andExpect(MockMvcResultMatchers.status().isOk()); //lehet más jobb a 200-nál
    }

    @Test
    // a create user egy post req, ezt át kell írni
    void createUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("user/create"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }




}
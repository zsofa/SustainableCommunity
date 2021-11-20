package Progmatic.SustainableCommunity.configurations;

import Progmatic.SustainableCommunity.testConfigs.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class WebSecConfigTest {

    @Autowired
    MockMvc mockMvc;

    @WithMockUser("GUEST")
    @Test
    void getInformationAboutUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("user/create")).andExpect(
                MockMvcResultMatchers.status().isOk()).andReturn();
    }

}
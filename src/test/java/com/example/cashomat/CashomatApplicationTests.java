package com.example.cashomat;

import com.example.cashomat.config.ATMProperties;
import com.example.cashomat.model.Deposit;
import com.example.cashomat.service.ATMService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CashomatApplicationTests {

    @Autowired
    ATMProperties atmProperties;
    @Autowired
    private MockMvc mvc;

    @Autowired
    ATMService atmService;


    @BeforeEach
    public void before() {
        atmService.resetStorage();
    }

    @Test
    public void injectCassettesFromMap()  {
        assertEquals(5, atmProperties.getCassettes().size());
        assertEquals(25, atmProperties.getMaxCapacity());
    }

    @Test
    public void depositSuccess() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .post("/api/deposit")
                        .content("""
                                {
                                  "10000": 1,
                                  "20000": 5
                                }""")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("110000"));
    }

    @Test
    public void depositInvalidInputError() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .post("/api/deposit")
                        .content("{\"15000\" : 1}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void depositInvalidOverMaxCapacityError() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .post("/api/deposit")
                        .content("{\"1000\" : 100}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void withdrawalSuccess() throws Exception
    {
        atmService.deposit(Set.of(new Deposit(10000, 5),
                new Deposit(1000, 5)));
        mvc.perform( MockMvcRequestBuilders
                        .post("/api/withdrawal")
                        .content("15000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("['10000']").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("['1000']").value(5));
    }

    @Test
    public void withdrawalInsufficientFoundsError() throws Exception
    {
        atmService.deposit(Set.of(new Deposit(10000, 5),
                new Deposit(1000, 5)));
        mvc.perform( MockMvcRequestBuilders
                        .post("/api/withdrawal")
                        .content("16000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void withdrawalInvalidValue() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .post("/api/withdrawal")
                        .content("15500")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}

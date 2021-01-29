package com.axis

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class EndPointControllerTest {

    @Autowired
    private val mockmvc: MockMvc? = null

    @Test
    @Throws(Exception::class)
    fun getAllEndPointTest(){
        mockmvc?.perform(MockMvcRequestBuilders
                .get("/endPoints")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Throws(Exception::class)
    fun getEndpointForProductOfPartnerTest(){
        mockmvc?.perform(MockMvcRequestBuilders
                .get("/endPoints/PROD_01/P_001")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Throws(Exception::class)
    fun getAllEndpointsForProductTest(){
        mockmvc?.perform(MockMvcRequestBuilders
                .get("/endPoints/PROD_01")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());

    }


    @Test
    @Throws(Exception::class)
    fun deleteEndPointsForAProductTest() {
        mockmvc?.perform(MockMvcRequestBuilders
                .delete("/endPoints")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Throws(Exception::class)
    fun  deleteEndPointsOfAProductForAPartnerTest() {
        mockmvc?.perform(MockMvcRequestBuilders
                .delete("/endPoints/PROD_01/P_001")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Throws(Exception::class)
    fun addEndPointsTest(){
        val objectMapper = ObjectMapper()
        val endPoint={"PROD_01" ; "P_001"; "http://localhost:9090/tata-aig/car-insurance/quote"; {"kulk@gmail.com"; "Nishchith" ; "7483093932" ; "KA38M4771"} ; "GET"};
        mockmvc?.perform(MockMvcRequestBuilders
                .post("/endPoints").content(objectMapper.writeValueAsString(endPoint))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Throws(Exception::class)
    fun updateEndPointTest(){
        val objectMapper = ObjectMapper()
        val endPoint={"PROD_01" ; "P_001"; "http://localhost:9090/tata-aig/car-insurance/quote"; {"kulk@gmail.com"; "Nishchith" ; "7483093932" ; "KA38M4771"} ; "GET"};


        mockmvc?.perform(MockMvcRequestBuilders
                .put("/endPoints").content(objectMapper.writeValueAsString(endPoint))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
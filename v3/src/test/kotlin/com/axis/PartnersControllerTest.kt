package com.axis

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class PartnersControllerTest {

    @Autowired
    private val mockmvc: MockMvc? = null

    @Test
    @Throws(Exception::class)
    fun getAllPartnersTest(){
        mockmvc?.perform(MockMvcRequestBuilders
                .get("/partners")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Throws(Exception::class)
    fun getPartnersForProductTest(){
        mockmvc?.perform(MockMvcRequestBuilders
                .get("/partners/PROD_01")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Throws(Exception::class)
    fun addPartnersTest(){
        val objectMapper = ObjectMapper()
        val partner={"P_001"; "Bharati Axa"; "https://odishatv.in/wp-content/uploads/2018/11/Bharti-Axa.jpg"};


        mockmvc?.perform(MockMvcRequestBuilders
                .post("/partners").content(objectMapper.writeValueAsString(partner))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Throws(Exception::class)
    fun updatePartnersTest(){
        val objectMapper = ObjectMapper()
        val partner={"P_001"; "Bharati Axa"; "https://odishatv.in/wp-content/uploads/2018/11/Bharti-Axa.jpg"};


        mockmvc?.perform(MockMvcRequestBuilders
                .put("/partners").content(objectMapper.writeValueAsString(partner))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
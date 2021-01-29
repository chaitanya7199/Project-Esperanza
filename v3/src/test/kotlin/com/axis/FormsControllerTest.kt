package com.axis

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class FormsControllerTest {

    @Autowired
    private val mockmvc: MockMvc? = null

    @Test
    @Throws(Exception::class)
    fun getFormsForProductTest(){
        mockmvc?.perform(MockMvcRequestBuilders
                .get("/forms/PROD_01")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Throws(Exception::class)
    fun deleteFormForAProductTest() {
        mockmvc?.perform(MockMvcRequestBuilders
                .delete("/forms")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Throws(Exception::class)
    fun addFormFieldsTest(){
        val objectMapper = ObjectMapper()
        val fields={"1" ; "1" ; "registrationNo" ; "registrationNo" ; "text" ; "Registration No" ; "Enter your Registration No" ; "input" ; "form-control mb-1" ; "{true}" ;
            "(val)=>{return val.match(/^[A-Za-z]{2}\\d{2}[A-Za-z]{1,3}\\d{4}$/)}" ; "Invalid Registration No."
        };
        mockmvc?.perform(MockMvcRequestBuilders
                .post("/formFields").content(objectMapper.writeValueAsString(fields))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Throws(Exception::class)
    fun updateFormFieldsForAProductTest(){
        val objectMapper = ObjectMapper()
        val fields={"1" ; "1" ; "registrationNo" ; "registrationNo" ; "text" ; "Registration No" ; "Enter your Registration No" ; "input" ; "form-control mb-1" ; "{true}" ;
            "(val)=>{return val.match(/^[A-Za-z]{2}\\d{2}[A-Za-z]{1,3}\\d{4}$/)}" ; "Invalid Registration No."
        };


        mockmvc?.perform(MockMvcRequestBuilders
                .put("/forms").content(objectMapper.writeValueAsString(fields))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());
    }




}
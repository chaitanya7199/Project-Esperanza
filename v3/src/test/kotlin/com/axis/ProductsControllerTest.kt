package com.axis

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class ProductsControllerTest {

    @Autowired
    private val mockmvc: MockMvc? = null

    @Test
    @Throws(Exception::class)
    fun getProductsTest(){
        mockmvc?.perform(MockMvcRequestBuilders
                .get("/products")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Throws(Exception::class)
    fun getProductByIdTest(){
        mockmvc?.perform(MockMvcRequestBuilders
                .get("/products/PROD_01")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Throws(Exception::class)
    fun getProductByTitleTest(){
        mockmvc?.perform(MockMvcRequestBuilders
                .get("/products/by/Car Insurance")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());

    }


    @Test
    @Throws(Exception::class)
    fun deleteProductTest() {
        mockmvc?.perform(MockMvcRequestBuilders
                .delete("/products")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Throws(Exception::class)
    fun addProductsTest(){
        val objectMapper = ObjectMapper()
        val product={"PROD_01"; "car insurance"; "Car Insurance"; "Car Insurance provided by Tata AIG"; "https://image.flaticon.com/icons/png/512/55/55283.png"; "/car-insurance"; "https://www.wallpapertip.com/wmimgs/220-2203876_beautiful-car-background-hd-3d-car-background-hd.jpg"};


        mockmvc?.perform(MockMvcRequestBuilders
                .post("/products").content(objectMapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Throws(Exception::class)
    fun updateProductTest(){
        val objectMapper = ObjectMapper()
        val product={"PROD_01"; "car insurance"; "Car Insurance"; "Car Insurance provided by Tata AIG"; "https://image.flaticon.com/icons/png/512/55/55283.png"; "/car-insurance"; "https://www.wallpapertip.com/wmimgs/220-2203876_beautiful-car-background-hd-3d-car-background-hd.jpg"};


        mockmvc?.perform(MockMvcRequestBuilders
                .put("/partners").content(objectMapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
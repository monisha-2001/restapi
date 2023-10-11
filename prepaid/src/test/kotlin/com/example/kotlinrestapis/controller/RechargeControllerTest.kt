package com.example.kotlinrestapis.controller

import com.example.kotlinrestapis.model.Recharge
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
class RechargeControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper) {

//    @Autowired
//    lateinit var mockMvc: MockMvc
//
//    @Autowired
//    lateinit var objectMapper : ObjectMapper

    val baseUrl = "/api/recharge"

    @Test
    fun `should return all recharge`() {

        mockMvc.get(baseUrl)
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }

    }

    @Test
    fun `should return details with a given phoneNumber`() {
        val phoneNumber = 9986766741

        mockMvc.get("$baseUrl/$phoneNumber")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }

    }

    @Test
    fun `should return NOT FOUND if the account number doesnot exist`() {

        val phoneNumber = "does_not_exist"

        mockMvc.get("$baseUrl/$phoneNumber")
            .andDo { print() }
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should add a new recharge`() {
        val newRecharge = Recharge("234567891", 280.0, "USSD")


        val performPost = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newRecharge)

        }

        performPost
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(newRecharge))
                }
            }


    }
}
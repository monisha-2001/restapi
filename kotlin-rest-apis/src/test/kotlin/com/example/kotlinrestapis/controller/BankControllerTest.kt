package com.example.kotlinrestapis.controller

import com.example.kotlinrestapis.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.match.MockRestRequestMatchers.content
import org.springframework.test.web.servlet.*
import org.springframework.web.servlet.function.RequestPredicates.contentType

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper) {

//    @Autowired
//    lateinit var mockMvc: MockMvc
//
//    @Autowired
//    lateinit var objectMapper : ObjectMapper

    val baseUrl = "/api/banks"
    @Test
    fun `should return all banks`() {

        mockMvc.get(baseUrl)
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }

    }

    @Test
    fun `should return bank with a given account number`() {
        val accountNumber = 2023

        mockMvc.get("$baseUrl/$accountNumber")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.trust") { value(3.14) }
                jsonPath("$.transcationFee") { value(7) }
            }

    }

    @Test
    fun `should return NOT FOUND if the account number doesnot exist`(){

        val accountNumber = "does_not_exist"

        mockMvc.get("$baseUrl/$accountNumber")
            .andDo { print() }
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should add a new bank account`(){
        val newBank = Bank("2013",28.0,11)



        val performPost = mockMvc.post(baseUrl){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newBank)

        }

        performPost
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(newBank))
                }
            }

        //to check whether it is created, we will get for this account no.
        mockMvc.get("$baseUrl/${newBank.accountNumber}")
            .andExpect {
                content {
                    json(objectMapper.writeValueAsString(newBank))
                }
            }
    }


    @Test
    fun `should return BAD REQUEST if bank account number already exists`() {
        val invalidBank = Bank("2023", 10.0, 2)

        val post = mockMvc.post(baseUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidBank)

        }

        post
            .andDo { print() }
            .andExpect {
                status { isBadRequest() }
            }
    }



    @Test
    fun `should update the existing bank`(){
        val updateBank = Bank("2025",34.0,4)

        val performPatch = mockMvc.patch(baseUrl){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateBank)
        }

        performPatch
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(updateBank))
                }
            }

        //and we can extact the updated record
       mockMvc.get("$baseUrl/${updateBank.accountNumber}")
            .andExpect {
                content {
                    json(objectMapper.writeValueAsString(updateBank))
                }
            }

        }

    @Test
    fun `should return a BAD REQUEST if no bank with account number exists`(){
        val invalidBank = Bank("does_not_exist",1.0,1)

        var performPatch = mockMvc.patch(baseUrl){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidBank)
        }

        performPatch
            .andDo { print() }
            .andExpect { status { isNotFound() } }

    }

    @Test
    fun `should delete the bank with the given account number`(){

        val accountNumber = 2023

        mockMvc.delete("$baseUrl/$accountNumber")
            .andDo { print() }
            .andExpect {
                status { isNoContent() } }

        mockMvc.get("$baseUrl/$accountNumber")
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should return a NOT FOUND if no bank with account number exists`(){
        val invalidaccountNumber = "does_not_exist"

        mockMvc.delete("$baseUrl/$invalidaccountNumber")
            .andDo { print() }
            .andExpect { status { isNotFound() } }


    }
}
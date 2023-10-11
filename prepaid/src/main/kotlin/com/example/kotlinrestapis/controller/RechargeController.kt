package com.example.kotlinrestapis.controller

import com.example.kotlinrestapis.model.Recharge
import com.example.kotlinrestapis.model.RegisterDetails
import com.example.kotlinrestapis.service.RechargeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class RechargeController(private val rechargeService : RechargeService)
 {


    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e:NoSuchElementException):ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

     @ExceptionHandler(IllegalArgumentException::class)
     fun handleBadRequest(e : IllegalArgumentException): ResponseEntity<String> =
         ResponseEntity(e.message,HttpStatus.BAD_REQUEST)


    @GetMapping("/recharge")
     fun getDetails() :Collection<Recharge> = rechargeService.getRechargeDetails()

     @GetMapping("/recharge/{phoneNumber}")
     fun getDetailsById(@PathVariable phoneNumber:String) : Recharge =rechargeService.getRechargeDetails(phoneNumber)

     @PostMapping("/recharge")
     @ResponseStatus(HttpStatus.CREATED)  //to get 201 status instead of 200
     fun do_Recharge(@RequestBody recharge:Recharge) : Recharge = rechargeService.addRecharge(recharge)




     @GetMapping("/register")
     fun GetRegisterDetails() :Collection<RegisterDetails> = rechargeService.getRegisterDetails()

     @GetMapping("/register/{phoneNumber}")
     fun GetRegisterDetailsById(@PathVariable phoneNumber:String) : RegisterDetails =rechargeService.getRegisterDetailsById(phoneNumber)

     @PostMapping("/register")
     @ResponseStatus(HttpStatus.CREATED)  //to get 201 status instead of 200
     fun create_account(@RequestBody register:RegisterDetails) : RegisterDetails = rechargeService.add_account(register)

     @PatchMapping("/register")
     fun updateDetails(@RequestBody details: RegisterDetails): RegisterDetails = rechargeService.updateDetails(details)

     @DeleteMapping("/register/{phoneNumber}")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     fun deleteBank(@PathVariable phoneNumber: String) : Unit = rechargeService.deleteaccount(phoneNumber)

}
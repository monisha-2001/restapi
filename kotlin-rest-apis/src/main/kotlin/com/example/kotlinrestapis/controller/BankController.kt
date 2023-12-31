package com.example.kotlinrestapis.controller

import com.example.kotlinrestapis.datasource.BankDataSource
import com.example.kotlinrestapis.model.Bank
import com.example.kotlinrestapis.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/banks")
class BankController(private val bankService : BankService)
 {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e:NoSuchElementException):ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

     @ExceptionHandler(IllegalArgumentException::class)
     fun handleBadRequest(e : IllegalArgumentException): ResponseEntity<String> =
         ResponseEntity(e.message,HttpStatus.BAD_REQUEST)


    @GetMapping()
//    fun getBank() : String = "work"
     fun getBanks() :Collection<Bank> = bankService.getBanks()

     @GetMapping("/{accountNumber}")
     fun  getBank(@PathVariable accountNumber:String) : Bank = bankService.getBanks(accountNumber)

     @PostMapping("/display")
     @ResponseStatus(HttpStatus.CREATED)  //to get 201 status instead of 200
     fun add_bank(@RequestBody bank:Bank) : Bank = bankService.addBank(bank)

     @PatchMapping
     fun updateBank(@RequestBody bank: Bank):Bank = bankService.updateBank(bank)

     @DeleteMapping("/{accountNumber}")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     fun deleteBank(@PathVariable accountNumber: String) : Unit = bankService.deleteBank(accountNumber)

}
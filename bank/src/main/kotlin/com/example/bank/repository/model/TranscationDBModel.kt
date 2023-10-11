package com.example.bank.repository.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.*

@Entity
class TransactionDBModel (
    var accountIdentifier:String,
    var value:Double,
    val description:String="",
){

    @Id
    @GeneratedValue
    var id: UUID?=null
        private set

    val date: Date = Date()
}
package com.example.kotlinrestapis.datasource

import com.example.kotlinrestapis.model.Recharge
import com.example.kotlinrestapis.model.RegisterDetails

interface RechargeDataSource {


    fun getRechargeDetails(): Collection<Recharge>

    fun getRechargeDetailsById(phoneNumber:String) :Recharge

    fun addRecharge(recharge: Recharge): Recharge



    fun getRegisterDetails():Collection<RegisterDetails>

    fun getRegisterDetailsById(phoneNumber: String):RegisterDetails

    fun add_account(details:RegisterDetails):RegisterDetails

    fun updateDetails(details : RegisterDetails):RegisterDetails

    fun deleteAccount(phoneNumber: String)
//    fun reteriveBank() : Collection<Recharge>
//
//    fun reteriveBank(accountNumber:String) : Recharge
//
//    fun createBank(recharge: Recharge): Recharge
//
//    fun updateBank(recharge: Recharge): Recharge
//
//    fun deleteBank(accountNumber: String)

}
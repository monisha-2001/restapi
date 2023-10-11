package com.example.kotlinrestapis.service

import com.example.kotlinrestapis.datasource.RechargeDataSource
import com.example.kotlinrestapis.model.Recharge
import com.example.kotlinrestapis.model.RegisterDetails
import org.springframework.stereotype.Service

@Service
class RechargeService(private val rechargeDataSource: RechargeDataSource) {

    fun getRechargeDetails(): Collection<Recharge> = rechargeDataSource.getRechargeDetails()

    fun getRechargeDetails(phoneNumber:String): Recharge = rechargeDataSource.getRechargeDetailsById(phoneNumber)

    fun addRecharge(recharge: Recharge): Recharge = rechargeDataSource.addRecharge(recharge)


    fun getRegisterDetails(): Collection<RegisterDetails> = rechargeDataSource.getRegisterDetails()

    fun getRegisterDetailsById(phoneNumber: String): RegisterDetails = rechargeDataSource.getRegisterDetailsById(phoneNumber)

    fun add_account(register: RegisterDetails): RegisterDetails = rechargeDataSource.add_account(register)

    fun updateDetails(details: RegisterDetails): RegisterDetails = rechargeDataSource.updateDetails(details)

    fun deleteaccount(phoneNumber: String) :Unit = rechargeDataSource.deleteAccount(phoneNumber)


}
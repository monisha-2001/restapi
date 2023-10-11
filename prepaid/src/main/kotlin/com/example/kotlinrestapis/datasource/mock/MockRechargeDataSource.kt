package com.example.kotlinrestapis.datasource.mock

import com.example.kotlinrestapis.datasource.RechargeDataSource
import com.example.kotlinrestapis.model.Recharge
import com.example.kotlinrestapis.model.RegisterDetails
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
 class MockRechargeDataSource : RechargeDataSource {//its means account no. should be equal to given account no.

    val recharges = mutableListOf(
        Recharge("9986766741",200.0,"TelstraApp"),
        Recharge("9902642173",500.0,"USSD"),
        Recharge("9663662173",600.0,"PPR"),
        )

    val register = mutableListOf(
        RegisterDetails("Ram","9663662173",21),
        RegisterDetails("Raju","9902642173",50),
        RegisterDetails("Rani","9986766741",38),
    )

    override fun getRechargeDetails(): Collection<Recharge> = recharges

    override fun getRechargeDetailsById(phoneNumber: String): Recharge =
        recharges.firstOrNull(){it.phoneNumber == phoneNumber }
        ?: throw NoSuchElementException("Could not find details for phone number $phoneNumber")

    override fun addRecharge(recharge: Recharge): Recharge {
        if (recharges.any { it.phoneNumber == recharge.phoneNumber }){
            throw IllegalArgumentException("Recharge is already done for phone number ${recharge.phoneNumber} ")
        }
        recharges.add(recharge)
        return recharge
    }



    override fun getRegisterDetails(): Collection<RegisterDetails> = register

    override fun getRegisterDetailsById(phoneNumber: String): RegisterDetails = register.firstOrNull(){it.phoneNumber == phoneNumber }
        ?: throw NoSuchElementException("Could not find details for phone number $phoneNumber")

    override fun add_account(details: RegisterDetails): RegisterDetails {
        if(register.any { it.phoneNumber == details.phoneNumber }){
            throw IllegalArgumentException("account with the phone number ${details.phoneNumber} already exists ")
        }
        register.add(details)
        return details
    }


    override fun updateDetails(details: RegisterDetails):RegisterDetails {
        val currentDetails = register.firstOrNull{it.phoneNumber == details.phoneNumber}
            ?: throw NoSuchElementException("Could not find account with phone number ${details.phoneNumber}")

        register.remove(currentDetails)
        register.add(details)
        return details

    }

    override fun deleteAccount(phoneNumber: String) {
        val currentBank = register.firstOrNull{it.phoneNumber == phoneNumber}
            ?: throw NoSuchElementException("Could not find account with phone number ${phoneNumber}")

        register.remove(currentBank)

    }

}
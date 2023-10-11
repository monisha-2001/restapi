package com.example.kotlinrestapis.datasource

import com.example.kotlinrestapis.model.Bank

interface BankDataSource {

    fun reteriveBank() : Collection<Bank>

    fun reteriveBank(accountNumber:String) : Bank

    fun createBank(bank: Bank): Bank

    fun updateBank(bank: Bank): Bank

    fun deleteBank(accountNumber: String)

}
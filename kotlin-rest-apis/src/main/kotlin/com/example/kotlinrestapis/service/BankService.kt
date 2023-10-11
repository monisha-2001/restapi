package com.example.kotlinrestapis.service

import com.example.kotlinrestapis.datasource.BankDataSource
import com.example.kotlinrestapis.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val bankDataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> = bankDataSource.reteriveBank()

    fun getBanks(accountNumber: String): Bank = bankDataSource.reteriveBank(accountNumber)

    fun addBank(bank: Bank): Bank = bankDataSource.createBank(bank)

    fun updateBank(bank: Bank): Bank = bankDataSource.updateBank(bank)

    fun deleteBank(accountNumber: String) : Unit = bankDataSource.deleteBank(accountNumber)


}
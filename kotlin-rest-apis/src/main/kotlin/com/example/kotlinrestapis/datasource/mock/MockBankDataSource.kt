package com.example.kotlinrestapis.datasource.mock

import com.example.kotlinrestapis.datasource.BankDataSource
import com.example.kotlinrestapis.model.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class MockBankDataSource : BankDataSource {//its account no. should be equal to given account no.

    val banks = mutableListOf(
        Bank("2023",3.14,7),
        Bank("2024",18.0,4),
        Bank("2025",20.0,6),
        )




    override fun reteriveBank(): Collection<Bank> = banks

    override fun reteriveBank(accountNumber: String): Bank = banks.firstOrNull() { it.accountNumber==accountNumber }
        ?: throw NoSuchElementException("Could not find bank with account number $accountNumber")


    override fun createBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber }){
            throw IllegalArgumentException("bank with account number ${bank.accountNumber} already exists")
        }
        banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull{it.accountNumber == bank.accountNumber}
            ?: throw NoSuchElementException("Could not find bank with account number ${bank.accountNumber}")

        banks.remove(currentBank)
        banks.add(bank)
        return bank
    }

    override fun deleteBank(accountNumber: String) {
        val currentBank = banks.firstOrNull{it.accountNumber == accountNumber}
            ?: throw NoSuchElementException("Could not find bank with account number ${accountNumber}")

        banks.remove(currentBank)

    }
}
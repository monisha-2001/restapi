package com.example.kotlinrestapis.service

import com.example.kotlinrestapis.datasource.BankDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BankServiceTest{

    private val bankDataSource : BankDataSource = mockk(relaxed = true)

    private val bankService = BankService(bankDataSource)

    @Test
    fun `should call its data source to reterive banks`(){

        val banks = bankService.getBanks()

        verify(exactly = 1) { bankDataSource.reteriveBank() } //checks whether it has called exactly once by service layer
    }


}
package com.example.kotlinrestapis.datasource.mock

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MockBankDataSourceTest{

    private val mockBankDataSource = MockBankDataSource()


    @Test
    fun `should provide a collection of banks`(){
        val banks = mockBankDataSource.reteriveBank()

        assertThat(banks).isNotEmpty
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`(){
        val banks = mockBankDataSource.reteriveBank()

        assertThat(banks).allMatch{ it.accountNumber.isNotBlank()}
        assertThat(banks).anyMatch { it.trust != 0.0 }
        assertThat(banks).allMatch{ it.transcationFee != 1}

    }
}
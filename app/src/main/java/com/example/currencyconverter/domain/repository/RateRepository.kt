package com.example.currencyconverter.domain.repository

import Currency
import com.example.currencyconverter.data.dataSource.remote.RemoteRatesServiceImpl
import com.example.currencyconverter.data.dataSource.remote.dto.RateDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RateRepository @Inject constructor() {

    suspend fun getRates(currency: Currency, amount: Double): List<RateDto> {
        val api = RemoteRatesServiceImpl()
        return api.getRates(currency.name, amount)
    }

}

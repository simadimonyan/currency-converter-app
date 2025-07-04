package com.example.currencyconverter.domain.usecase

import Currency
import com.example.currencyconverter.domain.repository.RateRepository
import com.example.currencyconverter.ui.shared.state.RateStateHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountByRatesUseCase @Inject constructor(
    private val rateRepository: RateRepository,
    private val rateStateHolder: RateStateHolder
){

    suspend fun count(currency: Currency, amount: Double) {
        val countedUnit = rateRepository.getRates(currency, amount)
        rateStateHolder.updateRates(countedUnit.toMutableList())
    }

}
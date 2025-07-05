package com.example.currencyconverter.domain.usecase

import Currency
import com.example.currencyconverter.domain.repository.RateRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetOneRateCostUseCase @Inject constructor(
    private val rateRepository: RateRepository,
) {

    suspend fun getOneRateCost(targetCurrencyCode: Currency, fundCurrencyCode: String): Double  {
        return rateRepository.getRates(targetCurrencyCode, 1.0)
            .first { it.currency == fundCurrencyCode }.value
    }

}
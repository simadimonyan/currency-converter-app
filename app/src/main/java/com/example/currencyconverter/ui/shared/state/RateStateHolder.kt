package com.example.currencyconverter.ui.shared.state

import Currency
import androidx.compose.runtime.Immutable
import com.example.currencyconverter.data.dataSource.remote.dto.RateDto
import com.example.currencyconverter.domain.entity.Balance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Immutable
data class RateState(
    val targetCurrency: Currency = Currency.RUB,
    val targetValue: Double = 1.0,
    val balances: MutableList<Balance> = mutableListOf<Balance>(),
    val rates: MutableList<RateDto> = mutableListOf<RateDto>()
)

@Singleton
@Immutable
class RateStateHolder @Inject constructor() {

    private val _rateState = MutableStateFlow(RateState())
    val rateState: StateFlow<RateState> = _rateState

    fun updateRateState(state: RateState) {
        _rateState.update { state }
    }

    fun updateBalances(balances: MutableList<Balance>) {
        _rateState.update { it.copy(balances=balances) }
    }

    fun updateTarget(currency: Currency, amount: Double) {
        _rateState.update { it.copy(targetCurrency = currency, targetValue = amount) }
    }

    fun updateRates(rates: MutableList<RateDto>) {
        _rateState.update { it.copy(rates = rates) }
    }

}
package com.example.currencyconverter.ui.screens.exchange

import Currency
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.usecase.ExchangePairUseCase
import com.example.currencyconverter.domain.usecase.GetOneRateCostUseCase
import com.example.currencyconverter.ui.shared.state.ExchangeStateHolder
import com.example.currencyconverter.ui.shared.state.RateStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val getOneRateCostUseCase: GetOneRateCostUseCase,
    private val exchangePairUseCase: ExchangePairUseCase,
    val exchangeStateHolder: ExchangeStateHolder,
    val rateStateHolder: RateStateHolder
) : ViewModel() {

    fun handleEvent(event: ExchangeEvents) {
        when(event) {
            is ExchangeEvents.CalculateCostEvent -> calculateCost(event.code)
            is ExchangeEvents.ExchangeEvent -> exchange(event.fromFunds, event.toTarget, event.targetAmount)
        }
    }

    private fun calculateCost(code: String) {
        viewModelScope.launch {
            val rateState = rateStateHolder.rateState.value
            val rate = getOneRateCostUseCase.getOneRateCost(rateState.targetCurrency, code)
            exchangeStateHolder.updateOneBuyRate(rate)
        }
    }

    private fun exchange(fromFunds: Currency, toTarget: Currency, targetAmount: Double) {
        viewModelScope.launch {
            exchangePairUseCase.transfer(fromFunds, toTarget, targetAmount)
        }
    }

}
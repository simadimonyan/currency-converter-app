package com.example.currencyconverter.ui.screens.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.usecase.GetOneRateCostUseCase
import com.example.currencyconverter.ui.shared.state.ExchangeStateHolder
import com.example.currencyconverter.ui.shared.state.RateStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val getOneRateCostUseCase: GetOneRateCostUseCase,
    val exchangeStateHolder: ExchangeStateHolder,
    val rateStateHolder: RateStateHolder
) : ViewModel() {

    fun handleEvent(event: ExchangeEvents) {
        when(event) {
            is ExchangeEvents.CalculateCostEvent -> calculateCost(event.code)
            is ExchangeEvents.ExchangeEvent -> TODO()
        }
    }

    private fun calculateCost(code: String) {
        viewModelScope.launch {
            val rateState = rateStateHolder.rateState.value
            val rate = getOneRateCostUseCase.getOneRateCost(rateState.targetCurrency, code)
            exchangeStateHolder.updateOneBuyRate(rate)
        }
    }

}
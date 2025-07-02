package com.example.currencyconverter.ui.screens.currency

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.usecase.CountByRatesUseCase
import com.example.currencyconverter.ui.shared.state.RateStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val countByRatesUseCase: CountByRatesUseCase,
    val rateStateHolder: RateStateHolder
) : ViewModel() {

    fun handleEvent(event: CurrencyEvents) {
        when(event) {
            is CurrencyEvents.RecountEvent -> TODO()
            CurrencyEvents.UpdateRatesEvent -> updateRateState()
        }
    }

    private fun updateRateState() {
        viewModelScope.launch {
            val state = rateStateHolder.rateState.value
            countByRatesUseCase.count(state.targetCurrency, state.targetValue)
            Log.d("CurrencyViewModel", "Rate Update...")
        }
    }

}
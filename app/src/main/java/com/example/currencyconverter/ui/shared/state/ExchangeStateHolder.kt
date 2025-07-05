package com.example.currencyconverter.ui.shared.state

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Immutable
data class ExchangeState(
    val oneBuyRate: Double = 0.0
)

@Singleton
@Immutable
class ExchangeStateHolder @Inject constructor() {

    private val _exchangeState = MutableStateFlow(ExchangeState())
    val exchangeState: StateFlow<ExchangeState> = _exchangeState

    fun updateOneBuyRate(rate: Double) {
        _exchangeState.update { it.copy(oneBuyRate = rate) }
    }

}


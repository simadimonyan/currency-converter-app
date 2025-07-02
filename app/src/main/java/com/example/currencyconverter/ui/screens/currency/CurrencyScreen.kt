package com.example.currencyconverter.ui.screens.currency

import Currency
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.data.dataSource.remote.dto.RateDto
import com.example.currencyconverter.ui.shared.components.CurrencyCard
import com.example.currencyconverter.ui.shared.state.RateState
import kotlinx.coroutines.delay

@Preview
@Composable
fun CurrencyScreenPreview() {
    CurrencyScreenContent(
        RateState(
            targetCurrency = Currency.CAD,
            targetValue = 0.00,
            rates = mutableListOf(
                RateDto(
                    "RUB", 0.0
                ),
                RateDto(
                    "RUB", 0.0
                ),
                RateDto(
                    "RUB", 0.0
                ),
                RateDto(
                    "RUB", 0.0
                )
            )
        ), {}
    )
}

@Composable
fun CurrencyScreen(viewModel: CurrencyViewModel = hiltViewModel()) {

    val state by viewModel.rateStateHolder.rateState.collectAsState()

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.handleEvent(CurrencyEvents.UpdateRatesEvent)
            delay(1000)
        }
    }

    val chooseTarget: (Currency) -> Unit = { currency ->
        viewModel.rateStateHolder.updateTarget(currency, 1.0)
        viewModel.handleEvent(CurrencyEvents.UpdateRatesEvent)
    }

    CurrencyScreenContent(state, chooseTarget)
}

@Composable
fun CurrencyScreenContent(state: RateState, chooseTarget: (Currency) -> Unit) {

    LazyColumn {

        item {
            val balance: String = state.balances.firstOrNull() { it.currency == state.targetCurrency.name }
                ?.amount.toString()

            CurrencyCard(
                state.targetCurrency,
                balance,
                state.targetValue.toString(),
                chooseTarget
            )
        }
        
        items(state.rates.size) { index ->
            val rate = state.rates[index]

            if (rate.currency != state.targetCurrency.name) {
                val balance: String = state.balances.firstOrNull() { it.currency == state.targetCurrency.name }
                    ?.amount.toString()

                CurrencyCard(
                    Currency.valueOf(rate.currency),
                    balance,
                    rate.value.toString(),
                    chooseTarget
                )
            }
        }
    }

}
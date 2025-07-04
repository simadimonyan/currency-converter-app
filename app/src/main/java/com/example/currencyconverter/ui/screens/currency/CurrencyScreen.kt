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
        ), {}, {}
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

    val chooseTarget: (Currency) -> Unit = {
        viewModel.rateStateHolder.updateTarget(it, state.targetValue)
        viewModel.handleEvent(CurrencyEvents.UpdateRatesEvent)
    }

    val recountRate: (Double) -> Unit = {
        viewModel.rateStateHolder.updateTarget(state.targetCurrency, it)
        viewModel.handleEvent(CurrencyEvents.UpdateRatesEvent)
    }

    CurrencyScreenContent(state, chooseTarget, recountRate)
}

@Composable
fun CurrencyScreenContent(
    state: RateState,
    chooseTarget: (Currency) -> Unit,
    recountRate: (Double) -> Unit
) {

    LazyColumn {

        item {
            val balance: String = state.balances.firstOrNull() { it.currency == state.targetCurrency.name }
                ?.amount.toString()

            CurrencyCard(
                true,
                state.targetCurrency,
                balance,
                state.targetValue.toString(),
                chooseTarget,
                recountRate
            )
        }
        
        items(state.rates.size) { index ->
            val rate = state.rates[index]

            if (rate.currency != state.targetCurrency.name) {
                val balance: String = state.balances.firstOrNull() { it.currency == state.targetCurrency.name }
                    ?.amount.toString()

                CurrencyCard(
                    false,
                    Currency.valueOf(rate.currency),
                    balance,
                    rate.value.toString(),
                    chooseTarget,
                    recountRate
                )
            }
        }
    }

}
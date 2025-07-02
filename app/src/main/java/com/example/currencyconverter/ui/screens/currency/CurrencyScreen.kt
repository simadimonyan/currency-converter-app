package com.example.currencyconverter.ui.screens.currency

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay

@Preview
@Composable
fun CurrencyScreenPreview() {
    CurrencyScreenContent()
}

@Composable
fun CurrencyScreen(viewModel: CurrencyViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.handleEvent(CurrencyEvents.UpdateRatesEvent)
            delay(1000)
        }
    }

    CurrencyScreenContent()
}

@Composable
fun CurrencyScreenContent() {

}
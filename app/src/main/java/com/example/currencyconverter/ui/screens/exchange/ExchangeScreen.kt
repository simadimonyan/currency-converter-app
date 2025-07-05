package com.example.currencyconverter.ui.screens.exchange

import Currency
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.currencyconverter.ui.shared.components.ExchangeCardContent
import com.example.currencyconverter.ui.shared.state.ExchangeState
import com.example.currencyconverter.ui.theme.lightBlue
import kotlinx.coroutines.delay

@Preview
@Composable
fun ExchangePreview() {
    ExchangeContent(
        Currency.EUR,
        100.0,
        Currency.RUB,
        10000.0,
        ExchangeState(90.5436),
        {}
    )
}

@Composable
fun ExchangeScreen(
    navHostController: NavHostController,
    code: String,
    amount: Double,
    viewModel: ExchangeViewModel = hiltViewModel()
) {

    val rateState by viewModel.rateStateHolder.rateState.collectAsState()
    val exchangeState by viewModel.exchangeStateHolder.exchangeState.collectAsState()

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.handleEvent(ExchangeEvents.CalculateCostEvent(code))
            delay(1000)
        }
    }

    val onExit: () -> Unit = {
        navHostController.navigateUp()
    }

    val fundCurrency = Currency.valueOf(code)


    ExchangeContent(rateState.targetCurrency, rateState.targetValue, fundCurrency, amount, exchangeState, onExit)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeContent(
    targetCurrency: Currency,
    targetCurrencyAmount: Double,
    fundCurrency: Currency,
    fundCurrencyAmount: Double,
    exchangeState: ExchangeState,
    onExit: () -> Unit
) {

    val cost = targetCurrencyAmount*exchangeState.oneBuyRate

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("${fundCurrency.fullName} to ${targetCurrency.fullName}", modifier = Modifier.padding(10.dp), color = Color.Black)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.White,
        content = { innerPaddings ->
            Column(modifier = Modifier.padding(innerPaddings).padding(horizontal = 15.dp)) {
                Text("${targetCurrency.symbol} 1 = ${fundCurrency.symbol} ${exchangeState.oneBuyRate.toString().takeWhile { it != '.' } 
                        + exchangeState.oneBuyRate.toString().dropWhile { it != '.' }.take(6)}")

                Spacer(modifier = Modifier.height(23.dp))

                ExchangeCardContent(
                    true,
                    targetCurrency,
                    "null",
                    targetCurrencyAmount.toString(),
                )

                Spacer(modifier = Modifier.height(10.dp))

                ExchangeCardContent(
                    false,
                    fundCurrency,
                    fundCurrencyAmount.toString(),
                    cost.toString(),
                )

                Spacer(modifier = Modifier.height(50.dp))

                Button(
                    onClick = {

                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = lightBlue),
                    shape = RoundedCornerShape(5.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp)
                ) {
                    Text("Buy ${targetCurrency.fullName} for ${fundCurrency.fullName}", color = Color.Black)
                }

            }
        }
    )
}
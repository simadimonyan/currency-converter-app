package com.example.currencyconverter.ui.screens.exchange

import Currency
import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.currencyconverter.ui.screens.currency.CurrencyEvents
import com.example.currencyconverter.ui.screens.currency.CurrencyViewModel
import com.example.currencyconverter.ui.shared.components.cards.ExchangeCardContent
import com.example.currencyconverter.ui.shared.state.ExchangeState
import com.example.currencyconverter.ui.theme.darkGray
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
        {_, _, _ ->}
    ) {}
}

@SuppressLint("ContextCastToActivity")
@Composable
fun ExchangeScreen(
    navHostController: NavHostController,
    code: String,
    amount: Double,
    viewModel: ExchangeViewModel = hiltViewModel(),
    currencyViewModel: CurrencyViewModel
) {

    val rateState by viewModel.rateStateHolder.rateState.collectAsState()
    val exchangeState by viewModel.exchangeStateHolder.exchangeState.collectAsState()

    val view = LocalView.current
    val activity = LocalContext.current as ComponentActivity

    LaunchedEffect(Unit) {
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.White.toArgb(), Color.White.toArgb()),
            navigationBarStyle = SystemBarStyle.light(Color.White.toArgb(), Color.White.toArgb())
        )
        WindowInsetsControllerCompat(activity.window, view).isAppearanceLightStatusBars = true
    }

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.handleEvent(ExchangeEvents.CalculateCostEvent(code))
            delay(1100)
        }
    }

    val onExit: () -> Unit = {
        navHostController.navigateUp()
    }

    val exchange: (Currency, Currency, Double) -> Unit = { fromFunds, toTarget, targetAmount ->
        viewModel.handleEvent(ExchangeEvents.ExchangeEvent(fromFunds, toTarget, targetAmount))

        // откат к базовым значениям
        currencyViewModel.rateStateHolder.updateTarget(rateState.targetCurrency, 1.0)
        currencyViewModel.handleEvent(CurrencyEvents.UpdateRatesEvent)

        onExit()
    }

    val fundCurrency = Currency.valueOf(code)


    ExchangeContent(rateState.targetCurrency, rateState.targetValue, fundCurrency, amount, exchangeState, exchange, onExit)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeContent(
    targetCurrency: Currency,
    targetCurrencyAmount: Double,
    fundCurrency: Currency,
    fundCurrencyAmount: Double,
    exchangeState: ExchangeState,
    exchange: (Currency, Currency, Double) -> Unit,
    onExit: () -> Unit
) {

    val cost = targetCurrencyAmount*exchangeState.oneBuyRate
    var debounceFlag by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        debounceFlag = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("${fundCurrency.fullName} to ${targetCurrency.fullName}", color = Color.Black)
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back",
                        modifier = Modifier.padding(horizontal = 10.dp).clickable(
                            interactionSource = null,
                            indication = null
                        ) {
                            onExit()
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.White,
        content = { innerPaddings ->
            Column(modifier = Modifier.padding(innerPaddings).padding(horizontal = 15.dp)) {

                Spacer(modifier = Modifier.height(23.dp))

                Text("${targetCurrency.symbol} 1 = ${fundCurrency.symbol} ${exchangeState.oneBuyRate.toString().takeWhile { it != '.' } 
                        + exchangeState.oneBuyRate.toString().dropWhile { it != '.' }.take(6)}",
                    fontSize = 16.sp, fontWeight = FontWeight.Bold
                )

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
                        exchange(fundCurrency, targetCurrency, targetCurrencyAmount)
                    },
                    enabled = debounceFlag,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = darkGray),
                    shape = RoundedCornerShape(5.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp)
                ) {
                    Text("Buy ${targetCurrency.fullName} for ${fundCurrency.fullName}",
                        color = if (debounceFlag) Color.White else Color.Black)
                }

            }
        }
    )
}
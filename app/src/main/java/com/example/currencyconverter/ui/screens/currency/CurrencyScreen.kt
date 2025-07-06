package com.example.currencyconverter.ui.screens.currency

import Currency
import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
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
import com.example.currencyconverter.ui.navigation.Routes
import com.example.currencyconverter.ui.shared.components.cards.CurrencyCard
import com.example.currencyconverter.ui.shared.components.Shimmer
import com.example.currencyconverter.ui.shared.state.RateState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CurrencyPreview() {
    CurrencyContent(
        RateState(
            targetCurrency = Currency.CAD,
            targetValue = 0.00,
            rates = mutableListOf(
//                RateDto(
//                    "RUB", 0.0
//                ),
//                RateDto(
//                    "RUB", 0.0
//                ),
//                RateDto(
//                    "RUB", 0.0
//                ),
//                RateDto(
//                    "RUB", 0.0
//                )
            )
        ), {}, {}, false, {}, { _, _, ->}, rememberBottomSheetScaffoldState()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ContextCastToActivity")
@Composable
fun CurrencyScreen(
    navHostController: NavHostController,
    viewModel: CurrencyViewModel = hiltViewModel(),
    bottomSheetState: BottomSheetScaffoldState
) {

    val state by viewModel.rateStateHolder.rateState.collectAsState()
    var exchangeProcessFlag by remember { mutableStateOf(false) }

    val view = LocalView.current
    val activity = LocalContext.current as ComponentActivity

    LaunchedEffect(Unit) {
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.Black.toArgb(), Color.Black.toArgb()),
            navigationBarStyle = SystemBarStyle.light(Color.White.toArgb(), Color.White.toArgb())
        )
        WindowInsetsControllerCompat(activity.window, view).isAppearanceLightStatusBars = false
    }

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.handleEvent(CurrencyEvents.UpdateRatesEvent)
            viewModel.handleEvent(CurrencyEvents.UpdateBalances)
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

    val toggleExchangeFlag: () -> Unit = {
        exchangeProcessFlag = !exchangeProcessFlag
    }

    val navigateToExchange: (String, Double) -> Unit = { code, amount ->
        navHostController.navigate(Routes.Exchange.routeWithArgs(code, amount.toString())) {
            popUpTo(navHostController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    CurrencyContent(state, chooseTarget, recountRate, exchangeProcessFlag, toggleExchangeFlag, navigateToExchange, bottomSheetState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyContent(
    state: RateState,
    chooseTarget: (Currency) -> Unit,
    recountRate: (Double) -> Unit,
    exchangeProcessFlag: Boolean,
    toggleExchangeFlag: () -> Unit,
    navigateToExchange: (String, Double) -> Unit,
    bottomSheetState: BottomSheetScaffoldState,
) {
    Column(
        modifier = Modifier
    ) {
        TopAppBar(
            title = {
                Text("Currency", fontSize = 23.sp, fontWeight = FontWeight.Bold, color = Color.White)
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )

        Card(modifier = Modifier.padding(horizontal = 10.dp)) {
            val balance: String = state.balances.firstOrNull { it.currency == state.targetCurrency.name }
                ?.amount.toString()

            CurrencyCard(
                true,
                state.targetCurrency,
                balance,
                state.targetValue.toString(),
                chooseTarget,
                recountRate,
                exchangeProcessFlag,
                toggleExchangeFlag,
                navigateToExchange
            )
        }
    }

    BottomSheetScaffold(
        sheetContent = {
            Text(if (exchangeProcessFlag) "Accounts" else "Rates", fontSize = 22.sp,
                fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp))

            if (!state.rates.isEmpty()) {
                val filteredRates = state.rates.filter { rate ->
                    rate.currency != state.targetCurrency.name &&
                            (!exchangeProcessFlag || (exchangeProcessFlag &&
                                    (state.balances.firstOrNull { it.currency == rate.currency }?.amount ?: 0.0) >= rate.value))
                }

                LazyColumn {
                    if (filteredRates.isNotEmpty()) {
                        items(filteredRates.size) { index ->

                            val rate = filteredRates[index]

                            val balance: String = state.balances.firstOrNull { it.currency == rate.currency }
                                ?.amount.toString()

                            CurrencyCard(
                                false,
                                Currency.valueOf(rate.currency),
                                balance,
                                rate.value.toString(),
                                chooseTarget,
                                recountRate,
                                exchangeProcessFlag,
                                toggleExchangeFlag,
                                navigateToExchange
                            )
                        }
                    } else if (exchangeProcessFlag) {
                        item {
                            Spacer(modifier = Modifier.height(180.dp))
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "У вас не хватает средств на счете",
                                    color = Color.Gray,
                                    fontSize = 18.sp,
                                )
                            }
                        }
                    }
                }
            }
            else {
                (1..7).forEach { i ->
                    Shimmer(modifier = Modifier.padding(10.dp).fillMaxWidth().height(50.dp))
                }
            }

        },
        modifier = Modifier,
        scaffoldState = bottomSheetState,
        sheetContainerColor = Color.White,
        sheetPeekHeight = 580.dp,
        sheetSwipeEnabled = false,
        containerColor = Color.White,
    ) {}

}
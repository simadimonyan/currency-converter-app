package com.example.currencyconverter.ui.screens.transactions

import Currency
import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo
import com.example.currencyconverter.ui.shared.components.Shimmer
import com.example.currencyconverter.ui.shared.components.cards.TransactionCard
import com.example.currencyconverter.ui.shared.state.TransactionsState
import kotlinx.coroutines.delay
import java.time.LocalDateTime

@Preview
@Composable
fun TransactionsPreview() {
    TransactionsContent(TransactionsState(
        transactions = mutableListOf(
            TransactionDbo(
                0,
                Currency.EUR.name,
                Currency.RUB.name,
                79.62950497133914,
                1.0,
                LocalDateTime.now()
            )
        )
    ))
}

@SuppressLint("ContextCastToActivity")
@Composable
fun TransactionsScreen(viewModel: TransactionsViewModel = hiltViewModel()) {

    val state by viewModel.transactionsStateHolder.transactionsState.collectAsState()

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
        viewModel.handleEvent(TransactionsEvents.UpdateTransactions)
    }

    TransactionsContent(state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsContent(state: TransactionsState) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
        LazyColumn(modifier = Modifier.padding().fillMaxSize()) {

            val transactions = state.transactions.reversed()

            item {
                TopAppBar(title = {
                    Text("Transactions", fontWeight = FontWeight.Bold)
                }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent))
            }


            if (state.transactions.isNotEmpty()) {
                items(transactions.size) { index ->
                    val transaction = transactions[index]

                    TransactionCard(
                        Currency.valueOf(transaction.from),
                        Currency.valueOf(transaction.to),
                        transaction.toAmount,
                        transaction.fromAmount,
                        transaction.dateTime
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
            else if (!state.isLoading) {
                item {
                    Spacer(modifier = Modifier.height(250.dp))
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "У вас нет совершенных транзакций",
                            color = Color.Gray,
                            fontSize = 18.sp,
                        )
                    }
                }
            }
            else {
                items(7) {
                    Shimmer(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                }
            }
        }

    }
}
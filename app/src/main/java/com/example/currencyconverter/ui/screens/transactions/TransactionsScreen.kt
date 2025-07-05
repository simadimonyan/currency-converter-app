package com.example.currencyconverter.ui.screens.transactions

import Currency
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo
import com.example.currencyconverter.ui.shared.components.TransactionCard
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

@Composable
fun TransactionsScreen(viewModel: TransactionsViewModel = hiltViewModel()) {

    val state by viewModel.transactionsStateHolder.transactionsState.collectAsState()

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            viewModel.handleEvent(TransactionsEvents.UpdateTransactions)
        }
    }

    TransactionsContent(state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsContent(state: TransactionsState) {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = {
                    Text("Transactions", fontWeight = FontWeight.Bold)
                }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent))
            }
        },
        containerColor = Color.White
    ) { innerPaddins ->
        LazyColumn(modifier = Modifier.padding(innerPaddins).fillMaxSize()) {

            val transactions = state.transactions.reversed()

            items(transactions.size) { index ->
                val transaction = transactions[index]

                TransactionCard(
                    Currency.valueOf(transaction.from),
                    Currency.valueOf(transaction.to),
                    transaction.toAmount,
                    transaction.fromAmount,
                    transaction.dateTime
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
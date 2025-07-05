package com.example.currencyconverter.ui.shared.state

import androidx.compose.runtime.Immutable
import com.example.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

data class TransactionsState(
    val transactions: List<TransactionDbo> = mutableListOf()
)

@Singleton
@Immutable
class TransactionsStateHolder @Inject constructor() {

    private val _transactionsState = MutableStateFlow(TransactionsState())
    val transactionsState: StateFlow<TransactionsState> = _transactionsState

    fun updateTransactions(transactions: List<TransactionDbo>) {
        _transactionsState.update { it.copy(transactions) }
    }

}
package com.example.currencyconverter.ui.shared.state

import android.util.Log
import androidx.compose.runtime.Immutable
import com.example.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

data class TransactionsState(
    val transactions: List<TransactionDbo> = mutableListOf(),
    val isLoading: Boolean = false
)

@Singleton
@Immutable
class TransactionsStateHolder @Inject constructor() {

    private val _transactionsState = MutableStateFlow(TransactionsState())
    val transactionsState: StateFlow<TransactionsState> = _transactionsState

    fun updateTransactions(transactions: List<TransactionDbo>) {
        _transactionsState.update { it.copy(transactions) }
    }

    fun updateLoading(isLoading: Boolean) {
        Log.d("TransactionsStateHolder", "updateLoading called with isLoading = $isLoading")
        _transactionsState.update { it.copy(isLoading = isLoading) }
        Log.d("TransactionsStateHolder", "after update, state.isLoading = ${_transactionsState.value.isLoading}")
    }

}
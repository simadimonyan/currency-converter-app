package com.example.currencyconverter.ui.screens.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.usecase.GetAllTransactionsUseCase
import com.example.currencyconverter.ui.shared.state.TransactionsStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    val transactionsStateHolder: TransactionsStateHolder
) : ViewModel() {

    fun handleEvent(event: TransactionsEvents) {
        when(event) {
            is TransactionsEvents.UpdateTransactions -> updateTransactions()
        }
    }

    private fun updateTransactions() {
        viewModelScope.launch {
            transactionsStateHolder.updateTransactions(getAllTransactionsUseCase.getAll())
        }
    }

}
package com.example.currencyconverter.ui.screens.transactions

sealed class TransactionsEvents {

    /**
     * Событие обновляет состояние транзакций
     */
    object UpdateTransactions : TransactionsEvents()

}
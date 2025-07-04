package com.example.currencyconverter.ui.screens.currency

sealed class CurrencyEvents {

    /**
     * Событие обновляет курс валют
     */
    object UpdateRatesEvent : CurrencyEvents()

    /**
     * Событие обновляет баланс счетов в базе
     */
    object UpdateBalances : CurrencyEvents()

}
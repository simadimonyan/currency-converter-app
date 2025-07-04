package com.example.currencyconverter.ui.screens.currency

import android.icu.util.CurrencyAmount

sealed class CurrencyEvents {

    /**
     * Событие обновляет курс валют
     */
    object UpdateRatesEvent : CurrencyEvents()

}
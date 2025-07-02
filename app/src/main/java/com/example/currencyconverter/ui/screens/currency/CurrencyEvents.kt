package com.example.currencyconverter.ui.screens.currency

sealed class CurrencyEvents {

    /**
     * Событие обновляет курс валют
     */
    object UpdateRatesEvent : CurrencyEvents()

    /**
     * Событие пересчитывает валюты относительно курса и друг друга
     */
    data class RecountEvent(val value: Int) : CurrencyEvents()

}
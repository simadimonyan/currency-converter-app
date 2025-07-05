package com.example.currencyconverter.ui.screens.exchange

sealed class ExchangeEvents {

    /**
     * Событие просчитывает стоимость обмена
     */
    data class CalculateCostEvent(val code: String) : ExchangeEvents()

    /**
     * Событие обмена валюты
     */
    object ExchangeEvent : ExchangeEvents()

}
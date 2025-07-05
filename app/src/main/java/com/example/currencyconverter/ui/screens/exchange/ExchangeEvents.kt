package com.example.currencyconverter.ui.screens.exchange

import Currency

sealed class ExchangeEvents {

    /**
     * Событие просчитывает стоимость обмена
     */
    data class CalculateCostEvent(val code: String) : ExchangeEvents()

    /**
     * Событие обмена валюты
     */
    data class ExchangeEvent(val fromFunds: Currency, val toTarget: Currency, val targetAmount: Double) : ExchangeEvents()

}
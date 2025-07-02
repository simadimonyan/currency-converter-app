package com.example.currencyconverter.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(val route: String) {

    /**
     * Окно мониторинга валют
     */
    object Currency : Routes("currency")

    /**
     * Окно для обмена валют
     */
    object Exchange : Routes("exchange")

    /**
     * Окно для перевода средств
     */
    object Transaction : Routes("transaction")

}
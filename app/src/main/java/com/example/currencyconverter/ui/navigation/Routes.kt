package com.example.currencyconverter.ui.navigation

import com.example.currencyconverter.domain.entity.Balance
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(val route: String) {

    /**
     * Окно мониторинга валют
     */
    @Serializable object Currency : Routes("currency")

    /**
     * Окно для обмена валют
     */
    @Serializable object Exchange : Routes("exchange/{code}/{balance}") {
        fun routeWithArgs(code: String, balance: String) = "exchange/$code/$balance"
    }

    /**
     * Окно для перевода средств
     */
    @Serializable object Transaction : Routes("transaction")



}
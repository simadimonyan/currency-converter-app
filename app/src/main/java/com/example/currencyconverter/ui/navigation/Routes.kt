package com.example.currencyconverter.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(val route: String) {

    /**
     * Окно для отрисовки
     */
    @Serializable object Activity : Routes("activity")

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
     * Окно транзакций
     */
    @Serializable object Transaction : Routes("transaction")



}
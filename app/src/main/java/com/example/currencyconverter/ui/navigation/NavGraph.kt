package com.example.currencyconverter.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.currencyconverter.ui.screens.currency.CurrencyScreen
import com.example.currencyconverter.ui.screens.currency.CurrencyViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController,
) {

    NavHost(
        navController = navHostController,
        startDestination = Routes.Currency.route
    ) {

        composable( // Мониторинг
            route = Routes.Currency.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            CurrencyScreen()
        }

        composable( // Обмен
            route = Routes.Exchange.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {

        }

        composable( // Перевод
            route = Routes.Transaction.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {

        }

    }

}
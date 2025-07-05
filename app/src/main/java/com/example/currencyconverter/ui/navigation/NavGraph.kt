package com.example.currencyconverter.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.example.currencyconverter.ui.screens.currency.CurrencyScreen
import com.example.currencyconverter.ui.screens.exchange.ExchangeScreen

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
            CurrencyScreen(navHostController)
        }

        composable( // Обмен
            route = Routes.Exchange.routeWithArgs("{code}", "{balance}"),
            arguments = listOf(
                navArgument("code") {
                    type = NavType.StringType
                },
                navArgument("balance") {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) { backstack ->
            val code = backstack.arguments?.getString("code") ?: ""
            val amount = backstack.arguments?.getString("balance")?.toDoubleOrNull() ?: 0.00

            ExchangeScreen(navHostController, code, amount)
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
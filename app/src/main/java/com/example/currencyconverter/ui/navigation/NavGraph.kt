package com.example.currencyconverter.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.currencyconverter.ui.screens.ActivityScreen
import com.example.currencyconverter.ui.screens.currency.CurrencyScreen
import com.example.currencyconverter.ui.screens.currency.CurrencyViewModel
import com.example.currencyconverter.ui.screens.exchange.ExchangeScreen
import com.example.currencyconverter.ui.screens.exchange.ExchangeViewModel
import com.example.currencyconverter.ui.screens.transactions.TransactionsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverlayNavGraph(
    navHostController: NavHostController,
    exchangeViewModel: ExchangeViewModel,
    currencyViewModel: CurrencyViewModel
) {

    val bottomSheetState = rememberBottomSheetScaffoldState()

    NavHost(
        navController = navHostController,
        startDestination = Routes.Activity.route
    ) {

        composable(
            route = Routes.Activity.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            ActivityScreen(navHostController, currencyViewModel, bottomSheetState)
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

            ExchangeScreen(navHostController, code, amount, exchangeViewModel, currencyViewModel)
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestedNavGraph(
    navHostController: NavHostController,
    overlayNavController: NavHostController,
    currencyViewModel: CurrencyViewModel,
    bottomSheetState: BottomSheetScaffoldState,
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
            CurrencyScreen(overlayNavController, currencyViewModel, bottomSheetState)
        }

        composable( // Транзакции
            route = Routes.Transaction.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            TransactionsScreen()
        }

    }

}



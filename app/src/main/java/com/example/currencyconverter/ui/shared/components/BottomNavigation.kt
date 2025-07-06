package com.example.currencyconverter.ui.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.currencyconverter.ui.navigation.Routes
import com.example.currencyconverter.ui.theme.darkGray

@Composable
fun BottomNavigation(navHostController: NavHostController) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column {
        HorizontalDivider()

        NavigationBar(
            modifier = Modifier,
            containerColor = Color.White
        ) {

            NavigationBarItem(
                selected = currentRoute == Routes.Currency.route,
                onClick = {
                    if (currentRoute == Routes.Transaction.route) {
                        navHostController.navigate(Routes.Currency.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        tint = if (currentRoute == Routes.Currency.route) Color.White else Color.Black,
                        contentDescription = "Currency"
                    )
                },
                enabled = true,
                label = {
                    Text("Currency", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = darkGray,
                    selectedTextColor = darkGray,
                    unselectedIconColor = darkGray,
                    unselectedTextColor = darkGray,
                    indicatorColor = darkGray
                )
            )

            NavigationBarItem(
                selected = currentRoute == Routes.Transaction.route,
                onClick = {
                    if (currentRoute == Routes.Currency.route) {
                        navHostController.navigate(Routes.Transaction.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        tint = if (currentRoute == Routes.Transaction.route) Color.White else Color.Black,
                        contentDescription = "Transactions"
                    )
                },
                enabled = true,
                label = {
                    Text("Transactions", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = darkGray,
                    selectedTextColor = darkGray,
                    unselectedIconColor = darkGray,
                    unselectedTextColor = darkGray,
                    indicatorColor = darkGray
                )
            )

        }
    }

}
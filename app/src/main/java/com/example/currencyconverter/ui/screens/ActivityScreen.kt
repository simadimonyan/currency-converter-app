package com.example.currencyconverter.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.ui.navigation.NestedNavGraph
import com.example.currencyconverter.ui.screens.currency.CurrencyViewModel
import com.example.currencyconverter.ui.shared.components.BottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    overlayNavController: NavHostController,
    currencyViewModel: CurrencyViewModel,
    bottomSheetState: BottomSheetScaffoldState
) {
    val navHostController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation(navHostController)
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NestedNavGraph(navHostController, overlayNavController, currencyViewModel, bottomSheetState)
        }
    }
}
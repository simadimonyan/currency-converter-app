package com.example.currencyconverter.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.ui.navigation.OverlayNavGraph
import com.example.currencyconverter.ui.screens.currency.CurrencyViewModel
import com.example.currencyconverter.ui.screens.exchange.ExchangeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        setContent {
            val overlayNavController = rememberNavController()

            val currencyViewModel: CurrencyViewModel = hiltViewModel()
            val exchangeViewModel: ExchangeViewModel = hiltViewModel()

            Box(modifier = Modifier.fillMaxSize()) {
                OverlayNavGraph(overlayNavController, exchangeViewModel, currencyViewModel)
            }
        }
    }
}
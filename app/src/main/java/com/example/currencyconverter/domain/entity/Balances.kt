package com.example.currencyconverter.domain.entity

import Currency

data class Balances(
    val currency: String,
    val amount: Double
)
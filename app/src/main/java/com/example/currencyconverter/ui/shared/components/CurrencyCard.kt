package com.example.currencyconverter.ui.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.domain.entity.Currency

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    CardContent(Currency.RUB)
}

@Composable
fun CurrencyCard(currency: Currency) {
    CardContent(currency)
}

@Composable
fun CardContent(currency: Currency) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row {
            //image

            Column {
                Text(currency.name)
            }
        }
    }
}
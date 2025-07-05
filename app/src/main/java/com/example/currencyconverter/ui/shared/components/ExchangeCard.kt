package com.example.currencyconverter.ui.shared.components

import Currency
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.ui.theme.lightBlue

@Preview
@Composable
fun ExchangeCardPreview() {
    Column {
        ExchangeCardContent(
            true,
            Currency.EUR,
            "null",
            "150",
        )
        ExchangeCardContent(
            false,
            Currency.RUB,
            "10000",
            "1050",
        )
    }
}

@Composable
fun ExchangeCard(
    target: Boolean,
    currency: Currency,
    balance: String,
    rate: String
) {
    ExchangeCardContent(
        target,
        currency,
        balance,
        rate,
    )
}

@Composable
fun ExchangeCardContent(
    target: Boolean,
    currency: Currency,
    balance: String,
    rate: String
) {

    var mutableRate by remember { mutableStateOf(rate) }

    LaunchedEffect(rate) {
        mutableRate = rate
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(3.dp),
        colors = CardDefaults.cardColors(containerColor = lightBlue),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(currency.resourceId),
                modifier = Modifier.size(80.dp),
                contentDescription = currency.name
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(currency.name, fontWeight=FontWeight.Bold)
                Text(currency.fullName)
                if (balance != "null") Text("Balance: ${currency.symbol} $balance")
            }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                "${if (target) "+" else "-"}${currency.symbol} ${mutableRate.takeWhile { it != '.' } + mutableRate.dropWhile { it != '.' }.take(6)}",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }

}
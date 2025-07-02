package com.example.currencyconverter.ui.shared.components

import Currency
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.ui.theme.lightBlue

@Preview(showSystemUi = true)
@Composable
fun CardPreview() {
    Column {
        Spacer(modifier = Modifier.height(60.dp))
        CardContent(Currency.CAD, "0", "1", {})
    }
}

@Composable
fun CurrencyCard(
    currency: Currency,
    balance: String,
    rate: String,
    chooseTarget: (Currency) -> Unit
) {
    CardContent(currency, balance, rate, chooseTarget)
}

@Composable
fun CardContent(currency: Currency, balance: String, rate: String, chooseTarget: (Currency) -> Unit) {

    Card(
        modifier = Modifier.fillMaxWidth().clickable{ chooseTarget(currency) },
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
                "${currency.symbol} ${rate.takeWhile { it != '.' } + rate.dropWhile { it != '.' }.take(6)}",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}
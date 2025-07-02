package com.example.currencyconverter.ui.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.R
import com.example.currencyconverter.domain.entity.Currency

@Preview(showSystemUi = true)
@Composable
fun CardPreview() {
    Column {
        Spacer(modifier = Modifier.height(60.dp))
        CardContent(Currency.RUB)
    }
}

@Composable
fun CurrencyCard(currency: Currency) {
    CardContent(currency)
}

@Composable
fun CardContent(currency: Currency) {

    val currencyToResourceMap = mapOf(
        Currency.TRY to R.drawable.resource_try,
        Currency.USD to R.drawable.usd,
        Currency.GBP to R.drawable.gbp,
        Currency.EUR to R.drawable.eur,
        Currency.AUD to R.drawable.aud,
        Currency.BGN to R.drawable.bgn,
        Currency.BRL to R.drawable.brl,
        Currency.CAD to R.drawable.cad,
        Currency.CHF to R.drawable.chf,
        Currency.CNY to R.drawable.cny,
        Currency.CZK to R.drawable.czk,
        Currency.DKK to R.drawable.dkk,
        Currency.HKD to R.drawable.hkd,
        Currency.HRK to R.drawable.hrk,
        Currency.HUF to R.drawable.huf,
        Currency.IDR to R.drawable.idr,
        Currency.ILS to R.drawable.ils,
        Currency.INR to R.drawable.inr,
        Currency.ISK to R.drawable.isk,
        Currency.JPY to R.drawable.jpy,
        Currency.KRW to R.drawable.krw,
        Currency.MXN to R.drawable.mxn,
        Currency.MYR to R.drawable.myr,
        Currency.NOK to R.drawable.nok,
        Currency.NZD to R.drawable.nzd,
        Currency.PHP to R.drawable.php,
        Currency.PLN to R.drawable.pln,
        Currency.RON to R.drawable.ron,
        Currency.RUB to R.drawable.rus,
        Currency.SEK to R.drawable.sek,
        Currency.SGD to R.drawable.sgd,
        Currency.THB to R.drawable.thb,
        Currency.ZAR to R.drawable.zar
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row {
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(currencyToResourceMap.getValue(currency)),
                modifier = Modifier.size(80.dp),
                contentDescription = currency.name
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column {

                Text(currency.name)
            }
        }
    }
}
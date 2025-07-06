package com.example.currencyconverter.ui.shared.components.cards

import Currency
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.ui.theme.darkGreen
import com.example.currencyconverter.ui.theme.lightGreen
import java.time.LocalDateTime

@Preview
@Composable
fun TransactionPreview() {
    TransactionContent(
        Currency.EUR,
        Currency.RUB,
        100.023420,
        1.02342340,
        LocalDateTime.now()
    )
}

@Composable
fun TransactionCard(
    from: Currency,
    to: Currency,
    amountTo: Double,
    amountFrom: Double,
    timestamp: LocalDateTime
) {
    TransactionContent(
        from,
        to,
        amountTo,
        amountFrom,
        timestamp
    )
}

@Composable
fun TransactionContent(
    from: Currency,
    to: Currency,
    amountTo: Double,
    amountFrom: Double,
    timestamp: LocalDateTime
) {
    Card(
        modifier = Modifier.padding(horizontal = 5.dp).border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("$to/$from", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.width(5.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = darkGreen),
                    shape = RoundedCornerShape(2.dp)
                ) {
                    Text("Exchange", color = lightGreen, fontSize = 13.sp, modifier = Modifier.padding(3.dp))
                }
                Spacer(modifier = Modifier.weight(1f))
                Text("${timestamp.toLocalDate()} ${timestamp.toLocalTime().toString().take(8)}", fontSize = 15.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(10.dp))
            //HorizontalDivider()
            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Text(
                    "${from.symbol} -${amountFrom.toString().takeWhile { it != '.' } + amountFrom.toString().dropWhile { it != '.' }.take(3)}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "${to.symbol} +${amountTo.toString().takeWhile { it != '.' } + amountTo.toString().dropWhile { it != '.' }.take(3)}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
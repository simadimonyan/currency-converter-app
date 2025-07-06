package com.example.currencyconverter.ui.shared.components.cards

import Currency
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.ui.theme.darkGray

@Preview(showSystemUi = true)
@Composable
fun CardPreview() {
    Column {
        Spacer(modifier = Modifier.height(60.dp))
        CardContent(false, Currency.CAD, "0", "1", {}, {}, false, {}) { _, _, -> }
    }
}

@Composable
fun CurrencyCard(
    target: Boolean,
    currency: Currency,
    balance: String,
    rate: String,
    chooseTarget: (Currency) -> Unit,
    recountRate: (Double) -> Unit,
    exchangeProcessFlag: Boolean,
    toggleExchangeFlag: () -> Unit,
    navigateToExchange: (String, Double) -> Unit
) {
    CardContent(target, currency, balance, rate, chooseTarget, recountRate, exchangeProcessFlag, toggleExchangeFlag, navigateToExchange)
}

@Composable
fun CardContent(
    target: Boolean,
    currency: Currency,
    balance: String,
    rate: String,
    chooseTarget: (Currency) -> Unit,
    recountRate: (Double) -> Unit,
    exchangeProcessFlag: Boolean,
    toggleExchangeFlag: () -> Unit,
    navigateToExchange: (String, Double) -> Unit
) {

    var changeTargetValueFlag by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var mutableRate by remember { mutableStateOf(rate) }

    LaunchedEffect(rate) {
        if (!isEditing) {
            mutableRate = rate
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(interactionSource = null, indication = null) {
                if (exchangeProcessFlag && !target) {
                    navigateToExchange(currency.name, balance.toDouble())
                } else {
                    chooseTarget(currency)
                }
            },
        shape = RoundedCornerShape(3.dp),
        colors = CardDefaults.cardColors(containerColor = if (target) darkGray else Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Row(modifier = Modifier.padding(if (target) 10.dp else 0.dp), verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(currency.resourceId),
                    modifier = Modifier.size(80.dp),
                    contentDescription = currency.name
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column {
                    Text(currency.name, fontWeight=FontWeight.Bold, fontSize = if (target) 18.sp else 16.sp, color = if (target) Color.White else darkGray)
                    Text(currency.fullName, fontSize = if (target) 15.sp else 14.sp, color = if (target) Color.White else darkGray)
                    if (balance != "null") Text("Balance: ${currency.symbol} ${balance.takeWhile { it != '.' } + balance.dropWhile { it != '.' }.take(3)}",
                        fontSize = if (target) 15.sp else 14.sp, color = if (target) Color.White else darkGray)
                }

                Spacer(modifier = Modifier.weight(1f))
                if (!changeTargetValueFlag) {
                    Text(
                        "${currency.symbol} ${mutableRate.takeWhile { it != '.' } + mutableRate.dropWhile { it != '.' }.take(6)}",
                        fontWeight = FontWeight.Bold,
                        color = if (target) Color.White else darkGray,
                        fontSize = if (target) 18.sp else 16.sp,
                        modifier = Modifier.clickable(interactionSource = null, indication = null) {
                            if (target) {
                                toggleExchangeFlag()
                                changeTargetValueFlag = true
                            }
                        })
                }
                else {
                    Text("${currency.symbol} ", fontWeight = FontWeight.Bold, color = Color.White)

                    var textWidth by remember { mutableIntStateOf(0) }

                    BasicTextField(
                        value = mutableRate,
                        onValueChange = {
                            isEditing = true
                            mutableRate = it
                            recountRate(it.toDoubleOrNull() ?: 1.0)
                        },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.width(with(LocalDensity.current) { (textWidth+5).toDp().coerceAtLeast(20.dp) }),
                        cursorBrush = SolidColor(Color.White),
                        onTextLayout = { textLayoutResult ->
                            textWidth = textLayoutResult.getLineRight(0).toInt()
                        },
                        decorationBox = { innerTextField ->
                            Box {
                                innerTextField()
                                Box(
                                    modifier = Modifier
                                        .width(with(LocalDensity.current) {
                                            textWidth.toDp().coerceAtLeast(20.dp)
                                        })
                                        .height(1.dp)
                                        .background(Color.White)
                                        .align(Alignment.BottomStart)
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        interactionSource = remember { null },
                        keyboardActions = KeyboardActions(onAny = {
                            recountRate(mutableRate.toDoubleOrNull() ?: 1.0)
                            toggleExchangeFlag()
                            changeTargetValueFlag = false
                            isEditing = false
                        })
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .clickable(interactionSource = null, indication = null) {
                                mutableRate = "1.0"
                                recountRate(1.0)
                                toggleExchangeFlag()
                                changeTargetValueFlag = false
                                isEditing = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("x", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}
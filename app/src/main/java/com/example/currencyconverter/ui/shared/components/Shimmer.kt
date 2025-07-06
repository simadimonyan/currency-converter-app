package com.example.currencyconverter.ui.shared.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun Shimmer(
    modifier: Modifier = Modifier,
    durationMillis: Int = 1000
) {
    val transition = rememberInfiniteTransition()
    val translateAnimation by transition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = listOf(Color.LightGray.copy(alpha = 0.3f), Color.LightGray.copy(alpha = 0.7f), Color.LightGray.copy(alpha = 0.3f)),
        start = Offset(translateAnimation * 1000f, 0f),
        end = Offset(translateAnimation * 1000f + 1000f, 0f)
    )

    Spacer(modifier.clip(RoundedCornerShape(10)).background(brush))
}
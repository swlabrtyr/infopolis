package com.infopolis.infopolis.util

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun Modifier.shimmerBackground(shape: Shape = RectangleShape): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 5000, easing =  LinearOutSlowInEasing),
            RepeatMode.Restart
        ),
    )

    val shimmerColors = listOf(
        Color.White,
        MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation, translateAnimation),
        end = Offset(translateAnimation + 250f, translateAnimation), // Animate horizontally
        tileMode = TileMode.Mirror,
    )

    return@composed this.then(background(brush, shape))
}

@Preview
@Composable
fun GradientBoxAnimationPreview() {
    GradientBoxAnimation()

}
@Composable
fun GradientBoxAnimation() {
    Box(modifier = Modifier
        .size(50.dp)
        .shimmerBackground()) {

    }

}

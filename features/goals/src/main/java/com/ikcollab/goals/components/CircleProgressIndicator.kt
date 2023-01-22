package com.ikcollab.goals.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikcollab.goals.R


@Composable
fun CircleProgressIndicator(
    size: Dp = 100.dp,
    foregroundIndicatorColor: Color = Color(0xFF4CAF50),
    shadowColor: Color = Color.Gray,
    indicatorThickness: Dp = 12.dp,
    dataUsage: Float,
    animationDuration: Int = 1000,
) {

    var dataUsageRemember by remember {
        mutableStateOf(-1f)
    }

    val dataUsageAnimate = animateFloatAsState(
        targetValue = dataUsageRemember,
        animationSpec = tween(
            durationMillis = animationDuration
        )
    )

    LaunchedEffect(Unit) {
        dataUsageRemember = dataUsage
    }

    Box(
        modifier = Modifier
            .size(size),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier
                .size(size)
        ) {

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(shadowColor, Color.Gray),
                    center = Offset(x = this.size.width / 2, y = this.size.height / 2),
                    radius = this.size.height / 2
                ),
                radius = this.size.height / 2,
                center = Offset(x = this.size.width / 2, y = this.size.height / 2)
            )

            drawCircle(
                color = Color.White,
                radius = (size / 2 - indicatorThickness).toPx(),
                center = Offset(x = this.size.width / 2, y = this.size.height / 2)
            )

            val sweepAngle = (dataUsageAnimate.value) * 360 / 100

            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round),
                size = Size(
                    width = (size - indicatorThickness).toPx(),
                    height = (size - indicatorThickness).toPx()
                ),
                topLeft = Offset(
                    x = (indicatorThickness / 2).toPx(),
                    y = (indicatorThickness / 2).toPx()
                )
            )
        }

        DisplayText(
            animateNumber = dataUsageAnimate,
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

}


@Composable
private fun DisplayText(
    animateNumber: State<Float>,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = (animateNumber.value).toInt().toString() + "%",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
    }
}

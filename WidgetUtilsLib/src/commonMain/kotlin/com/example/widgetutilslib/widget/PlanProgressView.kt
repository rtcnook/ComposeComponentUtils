package com.example.widgetutilslib.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalTextApi::class)
@Composable
fun PlanProgressView(
    progress: Float, // 0.0 to 1.0
    modifier: Modifier = Modifier,
    text: String = "锻炼\n完成度",
    activeColor1: Color = Color(0xFF33A381),
    activeColor2: Color = Color(0xFF176A4A),
    inactiveColor: Color = Color(0xFFDEDEDE)
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier.size(200.dp)) {
        val center = androidx.compose.ui.geometry.Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2
        val strokeWidth = 20.dp.toPx()
        val innerRadius = radius - strokeWidth

        // Draw background circle
        drawCircle(
            color = inactiveColor,
            radius = radius,
            center = center
        )

        // Draw inner white circle
        drawCircle(
            color = Color.White,
            radius = innerRadius,
            center = center
        )

        // Draw progress arc
        val sweepGradient = Brush.sweepGradient(
            colors = listOf(activeColor1, activeColor2),
            center = center
        )

        drawArc(
            brush = sweepGradient,
            startAngle = 180f,
            sweepAngle = -360f * progress,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = androidx.compose.ui.geometry.Size(radius * 2 - strokeWidth, radius * 2 - strokeWidth),
            topLeft = androidx.compose.ui.geometry.Offset(strokeWidth / 2, strokeWidth / 2)
        )

        // Draw text in center
        val textLayoutResult = textMeasurer.measure(
            text = text,
            style = TextStyle(
                color = Color(0xFF666666),
                fontSize = 16.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        )
        
        val textOffset = androidx.compose.ui.geometry.Offset(
            center.x - textLayoutResult.size.width / 2,
            center.y - textLayoutResult.size.height / 2
        )
        
        drawText(textLayoutResult, topLeft = textOffset)
        
        // Draw the decorative small circle (bottom center-ish in Java code)
        drawCircle(
            color = Color.White,
            radius = 5.dp.toPx(),
            center = androidx.compose.ui.geometry.Offset(10.dp.toPx(), center.y + 5.dp.toPx())
        )
    }
}

@Preview
@Composable
fun PlanProgressViewPreview() {
    PlanProgressView(progress = 0.7f)
}

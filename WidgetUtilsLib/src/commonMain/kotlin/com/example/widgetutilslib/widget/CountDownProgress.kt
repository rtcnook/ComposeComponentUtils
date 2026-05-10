package com.example.widgetutilslib.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CountDownProgress(
    progress: Float, // 0.0 to 1.0
    timeLeftSeconds: Int,
    modifier: Modifier = Modifier,
    reachedBarColor: Color = Color(0xFF4291F1),
    unreachedBarColor: Color = Color(0xFFCCCCCC),
    textColor: Color = Color(0xFF4291F1),
    textSize: Int = 10,
    showText: Boolean = true
) {
    val textMeasurer = rememberTextMeasurer()
    val text = "${timeLeftSeconds}s"

    Canvas(modifier = modifier.fillMaxWidth().height(20.dp)) {
        val width = size.width
        val height = size.height
        val barHeight = 4.dp.toPx()
        val textLayoutResult = textMeasurer.measure(
            text = text,
            style = TextStyle(color = textColor, fontSize = textSize.sp)
        )
        val textWidth = if (showText) textLayoutResult.size.width.toFloat() else 0f
        val offset = 3.dp.toPx()

        val reachedWidthMax = width * progress
        val reachedWidth = if (showText) (reachedWidthMax - textWidth / 2 - offset).coerceAtLeast(0f) else reachedWidthMax

        // Reached Bar
        drawRoundRect(
            color = reachedBarColor,
            size = Size(reachedWidth, barHeight),
            topLeft = Offset(0f, (height - barHeight) / 2),
            cornerRadius = CornerRadius(10.dp.toPx())
        )

        // Text
        if (showText) {
            val textX = (reachedWidth + offset).coerceAtMost(width - textWidth)
            drawText(
                textLayoutResult,
                topLeft = Offset(textX, (height - textLayoutResult.size.height) / 2)
            )
            
            // Unreached Bar
            val unreachedStart = textX + textWidth + offset
            if (unreachedStart < width) {
                drawRoundRect(
                    color = unreachedBarColor,
                    size = Size(width - unreachedStart, barHeight),
                    topLeft = Offset(unreachedStart, (height - barHeight) / 2),
                    cornerRadius = CornerRadius(10.dp.toPx())
                )
            }
        } else {
            // Unreached Bar
            drawRoundRect(
                color = unreachedBarColor,
                size = Size(width - reachedWidth, barHeight),
                topLeft = Offset(reachedWidth, (height - barHeight) / 2),
                cornerRadius = CornerRadius(10.dp.toPx())
            )
        }
    }
}

@Preview
@Composable
fun CountDownProgressPreview() {
    CountDownProgress(progress = 0.5f, timeLeftSeconds = 15)
}

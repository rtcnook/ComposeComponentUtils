package com.example.widgetutilslib.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BkgProgress(
    progress: Int, // 0 to 100
    modifier: Modifier = Modifier,
    startColor: Color = Color(0xFF2951AC),
    endColor: Color = Color(0xFF548DFF)
) {
    Canvas(modifier = modifier.fillMaxWidth().height(20.dp)) {
        val width = size.width
        val height = size.height
        val progressWidth = width * (progress.coerceIn(0, 100) / 100f)
        
        val brush = Brush.linearGradient(
            colors = listOf(startColor, endColor),
            start = Offset(0f, 0f),
            end = Offset(progressWidth, 0f)
        )
        
        drawRoundRect(
            brush = brush,
            size = Size(progressWidth, height),
            cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
        )
    }
}

@Preview
@Composable
fun BkgProgressPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        BkgProgress(progress = 30)
        Spacer(Modifier.height(16.dp))
        BkgProgress(progress = 70, startColor = Color.Red, endColor = Color.Yellow)
        Spacer(Modifier.height(16.dp))
        BkgProgress(progress = 100)
    }
}

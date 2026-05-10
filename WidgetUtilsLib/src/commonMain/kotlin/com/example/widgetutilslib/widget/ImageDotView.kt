package com.example.widgetutilslib.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ImageDotView(
    total: Int,
    pos: Int,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(width = 100.dp, height = 20.dp)) {
        val dotRadius = 4.dp.toPx()
        val dotMargin = 57.dp.toPx()
        val bottomMargin = 10.dp.toPx()
        
        // The original Java code has a loop but draws at the same fixed location
        // canvas.drawCircle(UnitUtils.dp2px(getResources(), 57), getHeight() - UnitUtils.dp2px(getResources(), 10), ...)
        // This seems like it might be intended to be a row of dots, but the Java code draws them on top of each other.
        // I will implement it as a row of dots to make it functional.
        
        val spacing = 12.dp.toPx()
        val startX = dotMargin - (total - 1) * spacing / 2
        
        for (i in 0 until total) {
            val color = if (i == pos) Color(0xFF7CA698) else Color.White
            drawCircle(
                color = color,
                radius = dotRadius,
                center = androidx.compose.ui.geometry.Offset(
                    x = startX + i * spacing,
                    y = size.height - bottomMargin
                )
            )
        }
    }
}

@Preview
@Composable
fun ImageDotViewPreview() {
    Box(modifier = Modifier.background(Color.Gray).padding(16.dp)) {
        ImageDotView(total = 3, pos = 1)
    }
}

package com.example.widgetutilslib.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PlaceRecView(
    onPointsChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialPointsString: String = "",
    rectType: Int = 1 // 1: Rect, 2: Quad, 3: Lines
) {
    var points by remember {
        mutableStateOf(parsePoints(initialPointsString) ?: Array(4) { Offset(0f, 0f) })
    }
    
    var draggingIndex by remember { mutableStateOf(-1) } // 0-3 for points, 4 for center drag

    val touchRadius = 15.dp

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(rectType) {
                detectDragGestures(
                    onDragStart = { offset ->
                        // Check if near any point
                        val index = points.indexOfFirst { (it.x * size.width - offset.x).let { dx -> dx * dx } + (it.y * size.height - offset.y).let { dy -> dy * dy } < touchRadius.toPx() * touchRadius.toPx() }
                        if (index != -1) {
                            draggingIndex = index
                        } else if (isInside(offset, points, size.width.toFloat(), size.height.toFloat())) {
                            draggingIndex = 4
                        }
                    },
                    onDragEnd = { draggingIndex = -1 },
                    onDragCancel = { draggingIndex = -1 },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val newPoints = points.copyOf()
                        if (draggingIndex in 0..3) {
                            if (rectType == 1) { // Fixed Rect
                                updateRectPoint(draggingIndex, dragAmount, newPoints, size.width.toFloat(), size.height.toFloat())
                            } else { // Free points
                                newPoints[draggingIndex] = Offset(
                                    (newPoints[draggingIndex].x + dragAmount.x / size.width).coerceIn(0f, 1f),
                                    (newPoints[draggingIndex].y + dragAmount.y / size.height).coerceIn(0f, 1f)
                                )
                            }
                        } else if (draggingIndex == 4) {
                            // Move all
                            val moveX = dragAmount.x / size.width
                            val moveY = dragAmount.y / size.height
                            
                            val canMoveX = newPoints.all { (it.x + moveX) in 0f..1f }
                            val canMoveY = newPoints.all { (it.y + moveY) in 0f..1f }
                            
                            for (i in 0..3) {
                                newPoints[i] = Offset(
                                    if (canMoveX) newPoints[i].x + moveX else newPoints[i].x,
                                    if (canMoveY) newPoints[i].y + moveY else newPoints[i].y
                                )
                            }
                        }
                        points = newPoints
                        onPointsChange(formatPoints(points))
                    }
                )
            }
    ) {
        val w = size.width
        val h = size.height

        if (rectType == 3) {
            // Two lines: 0-3 and 1-2
            drawLine(Color(0xFFEC412F), Offset(points[0].x * w, points[0].y * h), Offset(points[3].x * w, points[3].y * h), strokeWidth = 2.dp.toPx())
            drawLine(Color(0xFFEC412F), Offset(points[1].x * w, points[1].y * h), Offset(points[2].x * w, points[2].y * h), strokeWidth = 2.dp.toPx())
        } else {
            val path = Path().apply {
                moveTo(points[0].x * w, points[0].y * h)
                lineTo(points[1].x * w, points[1].y * h)
                lineTo(points[2].x * w, points[2].y * h)
                lineTo(points[3].x * w, points[3].y * h)
                close()
            }
            drawPath(path, Color(0xFFEC412F), style = Stroke(width = 2.dp.toPx()))
        }
    }
}

private fun updateRectPoint(index: Int, dragAmount: androidx.compose.ui.geometry.Offset, points: Array<Offset>, w: Float, h: Float) {
    val dx = dragAmount.x / w
    val dy = dragAmount.y / h
    
    when (index) {
        0 -> { // Top-Left
            points[0] = Offset((points[0].x + dx).coerceIn(0f, points[2].x - 0.05f), (points[0].y + dy).coerceIn(0f, points[2].y - 0.05f))
            points[1] = Offset(points[2].x, points[0].y)
            points[3] = Offset(points[0].x, points[2].y)
        }
        2 -> { // Bottom-Right
            points[2] = Offset((points[2].x + dx).coerceIn(points[0].x + 0.05f, 1f), (points[2].y + dy).coerceIn(points[0].y + 0.05f, 1f))
            points[1] = Offset(points[2].x, points[0].y)
            points[3] = Offset(points[0].x, points[2].y)
        }
    }
}

private fun isInside(offset: Offset, points: Array<Offset>, w: Float, h: Float): Boolean {
    val minX = points.minOf { it.x } * w
    val maxX = points.maxOf { it.x } * w
    val minY = points.minOf { it.y } * h
    val maxY = points.maxOf { it.y } * h
    return offset.x in minX..maxX && offset.y in minY..maxY
}

private fun parsePoints(s: String): Array<Offset>? {
    if (s.isEmpty()) return null
    return try {
        val parts = s.split(",").map { it.trim().toFloat() }
        if (parts.size == 8) {
            Array(4) { i -> Offset(parts[i * 2], parts[i * 2 + 1]) }
        } else null
    } catch (e: Exception) {
        null
    }
}

private fun formatPoints(points: Array<Offset>): String {
    return points.joinToString(",") { "${it.x},${it.y}" }
}

@Preview
@Composable
fun PlaceRecViewPreview() {
    PlaceRecView(
        onPointsChange = {},
        initialPointsString = "0.2,0.2,0.8,0.2,0.8,0.8,0.2,0.8"
    )
}

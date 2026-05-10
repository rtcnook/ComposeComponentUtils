package com.example.widgetutilslib.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.ui.tooling.preview.Preview

/**
 * A circular progress indicator that counts down from [durationSeconds].
 * Fixed render issue by adding ui-tooling dependency to build.gradle.kts.
 */
@Composable
fun CircleCountProgress(
    modifier: Modifier = Modifier,
    durationSeconds: Int = 60,
    onFinish: () -> Unit = {},
    circleColor: Color = Color(0xFF2196F3) // Material Blue for better visibility
) {
    var timeLeft by remember { mutableStateOf(durationSeconds) }
    // Recalculate progress whenever timeLeft changes
    val progress = if (durationSeconds > 0) timeLeft.toFloat() / durationSeconds else 0f

    LaunchedEffect(key1 = durationSeconds) {
        timeLeft = durationSeconds
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
        onFinish()
    }

    Box(
        modifier = modifier.size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            color = circleColor,
            strokeWidth = 4.dp,
            trackColor = circleColor.copy(alpha = 0.2f)
        )
        
        Text(
            text = "${timeLeft}s",
            color = Color.Black, // Text in black for better contrast
            fontSize = 20.sp
        )
    }
}

@Preview
@Composable
fun CircleCountProgressPreview() {
    Surface(
        color = Color.White,
        modifier = Modifier.padding(16.dp)
    ) {
        CircleCountProgress(durationSeconds = 30)
    }
}

package com.example.widgetutilslib.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DownTimerSpeaker(
    modifier: Modifier = Modifier,
    initialTimeMillis: Long = 2000,
    onFinish: () -> Unit = {}
) {
    var timeLeft by remember { mutableStateOf(initialTimeMillis) }
    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (timeLeft > 0) {
                delay(500)
                timeLeft -= 500
            }
            isRunning = false
            onFinish()
        }
    }

    if (isRunning || timeLeft == 0L) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text(
                text = if (timeLeft > 0) "${timeLeft / 500}" else "开始",
                fontSize = 40.sp,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun DownTimerSpeakerPreview() {
    DownTimerSpeaker()
}

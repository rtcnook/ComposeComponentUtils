package com.example.widgetutilslib.widget

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TimekeeperTextView(
    isStarted: Boolean,
    modifier: Modifier = Modifier,
    initialTimeMillis: Long = 0
) {
    var timeMillis by remember { mutableStateOf(initialTimeMillis) }

    LaunchedEffect(isStarted) {
        if (isStarted) {
            while (true) {
                delay(1000)
                timeMillis += 1000
            }
        }
    }

    val hours = timeMillis / 3600000
    val minutes = (timeMillis % 3600000) / 60000
    val seconds = (timeMillis % 60000) / 1000

    val timeStr = "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFF666666), fontSize = 16.sp)) {
            append("训练计时: ")
        }
        append(timeStr)
    }

    Text(text = annotatedString, modifier = modifier)
}

@Preview
@Composable
fun TimekeeperTextViewPreview() {
    TimekeeperTextView(isStarted = true)
}

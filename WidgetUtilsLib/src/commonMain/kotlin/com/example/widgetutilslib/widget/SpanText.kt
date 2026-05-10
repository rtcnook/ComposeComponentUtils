package com.example.widgetutilslib.widget

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SpanText(
    month: Int,
    year: Int,
    modifier: Modifier = Modifier
) {
    val annotatedString = buildAnnotatedString {
        val ms = "${month}月"
        withStyle(style = SpanStyle(fontSize = 16.sp, color = Color(0xFF333333))) {
            append(ms)
        }
        append(year.toString())
    }
    Text(text = annotatedString, modifier = modifier)
}

@Preview
@Composable
fun SpanTextPreview() {
    SpanText(month = 5, year = 2024)
}

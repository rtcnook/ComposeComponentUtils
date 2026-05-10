package com.example.widgetutilslib.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextImageDotView(
    text: String,
    totalDots: Int,
    currentPos: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            color = Color(0xFF333333),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        ImageDotView(
            total = totalDots,
            pos = currentPos,
            modifier = Modifier.height(20.dp)
        )
    }
}

@Preview
@Composable
fun TextImageDotViewPreview() {
    TextImageDotView(
        text = "描述图片",
        totalDots = 3,
        currentPos = 0
    )
}

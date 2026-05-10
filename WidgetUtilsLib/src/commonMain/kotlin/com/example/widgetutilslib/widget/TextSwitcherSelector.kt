package com.example.widgetutilslib.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextSwitcherSelector(
    onSwitcherState: (Boolean, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    firstText: String = "",
    secondText: String = "",
    initialIsLeftSelected: Boolean = true
) {
    var isLeftSelected by remember { mutableStateOf(initialIsLeftSelected) }

    Row(
        modifier = modifier
            .width(IntrinsicSize.Min)
            .height(35.dp)
            .background(Color(0xFFF4F3F3), shape = RoundedCornerShape(5.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Option
        SwitcherOption(
            text = firstText,
            isSelected = isLeftSelected,
            onClick = {
                if (!isLeftSelected) {
                    isLeftSelected = true
                    onSwitcherState(true, false)
                }
            }
        )

        // Right Option
        SwitcherOption(
            text = secondText,
            isSelected = !isLeftSelected,
            onClick = {
                if (isLeftSelected) {
                    isLeftSelected = false
                    onSwitcherState(false, true)
                }
            }
        )
    }
}

@Composable
private fun SwitcherOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundModifier = if (isSelected) {
        Modifier.background(
            brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFFFCD45), Color(0xFFFF7F2D))
            ),
            shape = RoundedCornerShape(5.dp)
        )
    } else {
        Modifier
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .then(backgroundModifier)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color(0xFF999999),
            fontSize = 15.sp
        )
    }
}

@Preview
@Composable
fun TextSwitcherSelectorPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        TextSwitcherSelector(
            onSwitcherState = { _, _ -> },
            firstText = "定时",
            secondText = "手动"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextSwitcherSelector(
            onSwitcherState = { _, _ -> },
            firstText = "模拟",
            secondText = "考核",
            initialIsLeftSelected = false
        )
    }
}

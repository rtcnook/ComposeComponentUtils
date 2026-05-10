package com.example.widgetutilslib.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DateView(
    modifier: Modifier = Modifier,
    initialYear: Int = 2024,
    initialMonth: Int = 5,
    initialDay: Int = 10,
    onDateSelected: (Int, Int, Int) -> Unit = { _, _, _ -> }
) {
    var year by remember { mutableStateOf(initialYear) }
    var month by remember { mutableStateOf(initialMonth) }
    var day by remember { mutableStateOf(initialDay) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "选择日期",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Year Picker
            PickerView(
                items = (2000..2030).map { "${it}年" },
                initialIndex = year - 2000,
                onItemSelected = { index ->
                    year = 2000 + index
                    onDateSelected(year, month, day)
                },
                modifier = Modifier.weight(1f)
            )

            // Month Picker
            PickerView(
                items = (1..12).map { "${it}月" },
                initialIndex = month - 1,
                onItemSelected = { index ->
                    month = index + 1
                    onDateSelected(year, month, day)
                },
                modifier = Modifier.weight(1f)
            )

            // Day Picker
            val daysInMonth = getDaysInMonth(year, month)
            PickerView(
                items = (1..daysInMonth).map { "${it}日" },
                initialIndex = (day - 1).coerceIn(0, daysInMonth - 1),
                onItemSelected = { index ->
                    day = index + 1
                    onDateSelected(year, month, day)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun getDaysInMonth(year: Int, month: Int): Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
        else -> 30
    }
}

@Preview
@Composable
fun DateViewPreview() {
    DateView()
}

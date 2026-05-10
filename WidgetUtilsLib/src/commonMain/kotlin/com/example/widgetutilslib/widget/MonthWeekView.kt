package com.example.widgetutilslib.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.widgetutilslib.Res
import com.example.widgetutilslib.month_pre
import com.example.widgetutilslib.month_next
import com.example.widgetutilslib.wd_0
import com.example.widgetutilslib.wd_1
import com.example.widgetutilslib.wd_2
import com.example.widgetutilslib.wd_3
import com.example.widgetutilslib.wd_4
import com.example.widgetutilslib.wd_5
import com.example.widgetutilslib.wd_6

@Composable
fun MonthWeekView(
    onDayClick: (year: Int, month: Int, day: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentYear by remember { mutableStateOf(2024) }
    var currentMonth by remember { mutableStateOf(5) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.month_pre),
                contentDescription = "Previous",
                modifier = Modifier
                    .clickable {
                        if (currentMonth == 1) {
                            currentMonth = 12
                            currentYear--
                        } else {
                            currentMonth--
                        }
                    }
                    .padding(16.dp)
            )

            Text(
                text = "${currentMonth}月  $currentYear",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Color(0xFF333333),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(Res.drawable.month_next),
                contentDescription = "Next",
                modifier = Modifier
                    .clickable {
                        if (currentMonth == 12) {
                            currentMonth = 1
                            currentYear++
                        } else {
                            currentMonth++
                        }
                    }
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(Color(0xFFDEDEDE)))

        val weekdays = listOf(
            stringResource(Res.string.wd_0),
            stringResource(Res.string.wd_1),
            stringResource(Res.string.wd_2),
            stringResource(Res.string.wd_3),
            stringResource(Res.string.wd_4),
            stringResource(Res.string.wd_5),
            stringResource(Res.string.wd_6)
        )
        Row(modifier = Modifier.fillMaxWidth().padding(top = 14.dp)) {
            weekdays.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF999999),
                    fontSize = 12.sp
                )
            }
        }

        val days = remember(currentYear, currentMonth) {
            calculateDays(currentYear, currentMonth)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth().height(240.dp)
        ) {
            items(days.size) { index ->
                val dayInfo = days[index]
                Text(
                    text = "${dayInfo.day}",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clickable(enabled = dayInfo.isCurrentMonth) {
                            if (dayInfo.isCurrentMonth) {
                                onDayClick(currentYear, currentMonth, dayInfo.day)
                            }
                        }
                        .wrapContentSize(Alignment.Center),
                    color = if (dayInfo.isCurrentMonth) Color(0xFF2951AC) else Color(0xFFDEDEDE),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun MonthWeekViewPreview() {
    MonthWeekView(onDayClick = { _, _, _ -> })
}

private data class DayInfo(val day: Int, val isCurrentMonth: Boolean)

private fun calculateDays(year: Int, month: Int): List<DayInfo> {
    val daysInMonth = getDaysInMonth(year, month)
    val firstDayOfWeek = getFirstDayOfWeek(year, month)
    
    val prevMonth = if (month == 1) 12 else month - 1
    val prevYear = if (month == 1) year - 1 else year
    val daysInPrevMonth = getDaysInMonth(prevYear, prevMonth)
    
    val result = mutableListOf<DayInfo>()
    for (i in 0 until firstDayOfWeek) {
        result.add(DayInfo(daysInPrevMonth - firstDayOfWeek + i + 1, false))
    }
    for (i in 1..daysInMonth) {
        result.add(DayInfo(i, true))
    }
    val remaining = 42 - result.size
    for (i in 1..remaining) {
        result.add(DayInfo(i, false))
    }
    return result
}

private fun getDaysInMonth(year: Int, month: Int): Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
        else -> 0
    }
}

private fun getFirstDayOfWeek(year: Int, month: Int): Int {
    var y = year
    var m = month
    if (m < 3) {
        m += 12
        y -= 1
    }
    val k = y % 100
    val j = y / 100
    val h = (1 + (13 * (m + 1)) / 5 + k + k / 4 + j / 4 + 5 * j) % 7
    return (h + 5) % 7
}

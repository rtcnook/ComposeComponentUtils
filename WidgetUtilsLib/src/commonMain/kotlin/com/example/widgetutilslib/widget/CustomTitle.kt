package com.example.widgetutilslib.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.DrawableResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.widgetutilslib.Res
import com.example.widgetutilslib.arrow_left
import com.example.widgetutilslib.screen_projection
import com.example.widgetutilslib.record_time

@Composable
fun CustomTitle(
    title: String,
    hasBack: Boolean = true,
    showScreenCasting: Boolean = false,
    showRecordTime: Boolean = false,
    recordTimeIcon: DrawableResource? = null,
    onBack: () -> Unit = {},
    onTitleHandle: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .background(Color(0x66000000))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (hasBack) {
            Image(
                painter = painterResource(Res.drawable.arrow_left),
                contentDescription = "Back",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { onBack() }
                    .padding(20.dp)
            )
        }

        Text(
            text = title,
            color = Color.White,
            fontSize = 25.sp,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 90.dp)
        )

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showScreenCasting) {
                Row(
                    modifier = Modifier.clickable { onTitleHandle() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(Res.drawable.screen_projection),
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = stringResource(Res.string.screen_projection),
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
            }

            if (showRecordTime) {
                Image(
                    painter = painterResource(recordTimeIcon ?: Res.drawable.record_time),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onTitleHandle() }
                        .padding(start = 20.dp, end = 20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun CustomTitlePreview() {
    Column {
        CustomTitle(title = "默认标题")
        Spacer(Modifier.height(8.dp))
        CustomTitle(
            title = "带投屏标题",
            showScreenCasting = true
        )
        Spacer(Modifier.height(8.dp))
        CustomTitle(
            title = "带录制标题",
            showRecordTime = true
        )
    }
}

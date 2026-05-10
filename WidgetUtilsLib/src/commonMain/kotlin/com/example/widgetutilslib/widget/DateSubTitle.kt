package com.example.widgetutilslib.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.widgetutilslib.Res
import com.example.widgetutilslib.pre_drw
import com.example.widgetutilslib.next_drw
import com.example.widgetutilslib.next_ue_drw

@Composable
fun DateSubTitle(
    currentDateText: String,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    isNextEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .background(Color(0xFFE3EDEA), shape = RoundedCornerShape(10.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.pre_drw),
            contentDescription = "Previous",
            modifier = Modifier
                .clickable { onPreviousClick() }
                .padding(4.dp)
        )

        Text(
            text = currentDateText,
            modifier = Modifier.padding(horizontal = 30.dp),
            color = Color(0xFF2951AC),
            fontSize = 20.sp
        )

        Image(
            painter = painterResource(if (isNextEnabled) Res.drawable.next_drw else Res.drawable.next_ue_drw),
            contentDescription = "Next",
            modifier = Modifier
                .clickable(enabled = isNextEnabled) { onNextClick() }
                .padding(4.dp)
        )
    }
}

@Preview
@Composable
fun DateSubTitlePreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        DateSubTitle(
            currentDateText = "2024-05-10",
            onPreviousClick = {},
            onNextClick = {},
            isNextEnabled = false
        )
        Spacer(Modifier.height(8.dp))
        DateSubTitle(
            currentDateText = "2024-05-09",
            onPreviousClick = {},
            onNextClick = {},
            isNextEnabled = true
        )
    }
}

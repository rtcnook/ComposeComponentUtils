package com.example.widgetutilslib.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TagText(
    text: String,
    spanStart: Int,
    modifier: Modifier = Modifier,
    tagColor: Color = Color(0x99000000),
    textColor: Color = Color.Black
) {
    val annotatedString = buildAnnotatedString {
        if (spanStart > 0 && spanStart <= text.length) {
            withStyle(style = SpanStyle(color = tagColor)) {
                append(text.substring(0, spanStart))
            }
            withStyle(style = SpanStyle(color = textColor)) {
                append(text.substring(spanStart))
            }
        } else {
            append(text)
        }
    }
    Text(text = annotatedString, modifier = modifier)
}

@Preview
@Composable
fun TagTextPreview() {
    Column {
        TagText(text = "标签: 内容内容", spanStart = 3)
        TagText(text = "重要通知: 请注意查收", spanStart = 5, tagColor = Color.Red)
    }
}

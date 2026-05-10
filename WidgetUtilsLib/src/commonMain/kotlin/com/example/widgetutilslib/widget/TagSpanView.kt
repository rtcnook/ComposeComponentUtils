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
fun TagSpanView(
    text: String,
    tagLen: Int,
    modifier: Modifier = Modifier,
    tagColor: Color = Color.White,
    tagSize: Int = 16,
    spanType: Int = 1 // 1 for color, 2 for size
) {
    val annotatedString = buildAnnotatedString {
        if (text.isNotEmpty() && tagLen > 0 && tagLen <= text.length) {
            val style = if (spanType == 2) {
                SpanStyle(fontSize = tagSize.sp)
            } else {
                SpanStyle(color = tagColor)
            }
            
            withStyle(style = style) {
                append(text.substring(0, tagLen))
            }
            append(text.substring(tagLen))
        } else {
            append(text)
        }
    }
    Text(text = annotatedString, modifier = modifier)
}

@Preview
@Composable
fun TagSpanViewPreview() {
    TagSpanView(text = "Tag: Content", tagLen = 4, tagColor = Color.Blue)
}

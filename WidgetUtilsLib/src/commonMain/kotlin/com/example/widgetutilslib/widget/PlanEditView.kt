package com.example.widgetutilslib.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PlanEditView(
    onFirstResult: (String) -> Unit,
    onSecondResult: (String) -> Unit,
    modifier: Modifier = Modifier,
    nm1: String = "",
    nm2: String = "",
    ns1: String = "",
    ns2: String = "",
    l1: Int = 5,
    l2: Int = 5,
    num: Int = 4,
    initialText1: String = "",
    initialText2: String = ""
) {
    var text1 by remember { mutableStateOf(initialText1) }
    var text2 by remember { mutableStateOf(initialText2) }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TagSpanView(
                text = nm1,
                tagLen = 1,
                tagColor = Color(0xFFDB3C3C),
                modifier = Modifier.weight(1f)
            )

            if (num != 3) {
                Spacer(modifier = Modifier.width(10.dp))
                TagSpanView(
                    text = nm2,
                    tagLen = 1,
                    tagColor = Color(0xFFDB3C3C),
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            // First Input Box
            PlanEditInput(
                value = text1,
                onValueChange = {
                    val filtered = if (l1 > 0 && it.length > l1) it.take(l1) else it
                    text1 = filtered
                    onFirstResult(filtered)
                },
                unit = ns1,
                modifier = Modifier.weight(1f)
            )

            if (num != 3) {
                Spacer(modifier = Modifier.width(10.dp))
                // Second Input Box
                PlanEditInput(
                    value = text2,
                    onValueChange = {
                        val filtered = if (l2 > 0 && it.length > l2) it.take(l2) else it
                        text2 = filtered
                        onSecondResult(filtered)
                    },
                    unit = ns2,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun PlanEditInput(
    value: String,
    onValueChange: (String) -> Unit,
    unit: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .heightIn(min = 60.dp)
            .background(Color(0xFFF4F3F3), shape = RoundedCornerShape(10.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 15.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (value.isEmpty()) {
                Text(
                    text = "请输入",
                    color = Color(0xFF999999),
                    fontSize = 18.sp
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    color = Color(0xFF333333),
                    fontSize = 18.sp
                ),
                singleLine = true
            )
        }

        Text(
            text = unit,
            modifier = Modifier.padding(horizontal = 15.dp),
            color = Color(0xFF666666),
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun PlanEditViewPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        PlanEditView(
            onFirstResult = {},
            onSecondResult = {},
            nm1 = "*次数",
            ns1 = "组",
            nm2 = "*时间",
            ns2 = "min"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PlanEditView(
            onFirstResult = {},
            onSecondResult = {},
            nm1 = "*次数",
            ns1 = "组",
            num = 3
        )
    }
}

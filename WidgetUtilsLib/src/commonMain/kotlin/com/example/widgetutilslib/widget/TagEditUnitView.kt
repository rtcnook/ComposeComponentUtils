package com.example.widgetutilslib.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TagEditUnitView(
    onEditResult: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    tagNm: String = "",
    unitNm: String = "",
    unitNs: String = "",
    doubleEdit: Boolean = false,
    unitInputType: Int = 0, // 1: numberDecimal, 2: number, 0: text
    input1Length: Int = 5,
    input2Length: Int = 5,
    defaultPrice: String = "",
    showCompleteTip: Boolean = false
) {
    var text1 by remember { mutableStateOf(defaultPrice) }
    var text2 by remember { mutableStateOf(defaultPrice) }

    val keyboardType = when (unitInputType) {
        1 -> KeyboardType.Decimal
        2 -> KeyboardType.Number
        else -> KeyboardType.Text
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = tagNm,
            modifier = Modifier
                .width(96.dp)
                .padding(top = 18.dp),
            textAlign = TextAlign.End,
            color = Color(0xFF333333),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                // First Input Box
                EditUnitInput(
                    value = text1,
                    onValueChange = {
                        val filtered = if (input1Length > 0 && it.length > input1Length) it.take(input1Length) else it
                        text1 = filtered
                        onEditResult(filtered, if (doubleEdit) text2 else "")
                    },
                    unit = unitNm,
                    keyboardType = keyboardType,
                    showError = showCompleteTip,
                    modifier = Modifier.weight(1f)
                )

                if (doubleEdit) {
                    Spacer(modifier = Modifier.width(10.dp))
                    // Second Input Box
                    EditUnitInput(
                        value = text2,
                        onValueChange = {
                            val filtered = if (input2Length > 0 && it.length > input2Length) it.take(input2Length) else it
                            text2 = filtered
                            onEditResult(text1, filtered)
                        },
                        unit = unitNs,
                        keyboardType = keyboardType,
                        showError = showCompleteTip,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (showCompleteTip) {
                Text(
                    text = "请完善信息", // From @string/item_tip
                    color = Color(0xFFDB3C3C),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 12.dp, top = 4.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun EditUnitInput(
    value: String,
    onValueChange: (String) -> Unit,
    unit: String,
    keyboardType: KeyboardType,
    showError: Boolean,
    modifier: Modifier = Modifier
) {
    val borderColor = if (showError) Color(0xFFDB3C3C) else Color.Transparent
    
    Row(
        modifier = modifier
            .heightIn(min = 60.dp)
            .background(Color(0xFFF4F3F3), shape = RoundedCornerShape(10.dp))
            .border(1.dp, borderColor, shape = RoundedCornerShape(10.dp)),
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
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
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
fun TagEditUnitViewPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        TagEditUnitView(
            onEditResult = { _, _ -> },
            tagNm = "身高",
            unitNm = "cm"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TagEditUnitView(
            onEditResult = { _, _ -> },
            tagNm = "价格",
            unitNm = "元",
            unitNs = "元",
            doubleEdit = true,
            unitInputType = 1,
            showCompleteTip = true
        )
    }
}

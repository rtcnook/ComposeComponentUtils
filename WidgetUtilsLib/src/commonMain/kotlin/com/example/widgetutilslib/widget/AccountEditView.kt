package com.example.widgetutilslib.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.graphics.painter.ColorPainter
import com.example.widgetutilslib.Res
import com.example.widgetutilslib.account_ds

@Composable
fun AccountEditView(
    onContentChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    inputHint: String = "",
    ems: Int = 0,
    isPassword: Boolean = false,
    initialText: String = ""
) {
    var text by remember { mutableStateOf(initialText) }
    var hintText by remember { mutableStateOf(inputHint) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .background(Color(0xFFF4F3F3), shape = RoundedCornerShape(10.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (LocalInspectionMode.current) {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(24.dp)
                    .background(Color.Gray)
            )
        } else {
            Image(
                painter = painterResource(Res.drawable.account_ds),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(24.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .padding(start = 20.dp)
                .width(2.dp)
                .height(30.dp)
                .background(Color(0xFFDEDEDE))
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (text.isEmpty()) {
                Text(
                    text = hintText,
                    color = Color(0xFF999999),
                    fontSize = 18.sp
                )
            }

            BasicTextField(
                value = text,
                onValueChange = { newValue ->
                    // Logic from Java: only allow numbers
                    val filtered = newValue.filter { it.isDigit() }
                    if (filtered != newValue) {
                        hintText = "只能输入数字"
                    }
                    
                    val finalValue = if (ems > 0 && filtered.length > ems) {
                        filtered.take(ems)
                    } else {
                        filtered
                    }
                    
                    text = finalValue
                    onContentChange(finalValue)
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    color = Color(0xFF333333),
                    fontSize = 18.sp
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isPassword) KeyboardType.NumberPassword else KeyboardType.Number
                ),
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                singleLine = true
            )
        }
    }
}

@Preview
@Composable
fun AccountEditViewPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        AccountEditView(
            onContentChange = {},
            inputHint = "请输入账号",
            ems = 11
        )
        Spacer(modifier = Modifier.height(16.dp))
        AccountEditView(
            onContentChange = {},
            inputHint = "请输入密码",
            isPassword = true,
            ems = 6
        )
    }
}

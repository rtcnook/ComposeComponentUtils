package com.example.widgetutilslib.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.DrawableResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EditTipView(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "请输入",
    isEditable: Boolean = true,
    endIcon: DrawableResource? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEditable) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text(hint) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                )
            )
        } else {
            Text(
                text = value.ifEmpty { hint },
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                color = if (value.isEmpty()) Color.Gray else Color.Black
            )
        }

        if (endIcon != null) {
            Image(
                painter = painterResource(endIcon),
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun EditTipViewPreview() {
    Column {
        EditTipView(
            value = "正在编辑的内容",
            onValueChange = {},
            isEditable = true
        )
        Spacer(Modifier.height(8.dp))
        EditTipView(
            value = "只读内容",
            onValueChange = {},
            isEditable = false
        )
    }
}

package com.example.widgetutilslib.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.widgetutilslib.Res
import com.example.widgetutilslib.loading_img
import com.example.widgetutilslib.is_loading

@Composable
fun LoadingDialog(
    onDismissRequest: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        LoadingDialogContent()
    }
}

@Composable
fun LoadingDialogContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 116.dp)
                .background(Color(0x7F000000), shape = RoundedCornerShape(10.dp)) 
                .widthIn(min = 320.dp)
                .heightIn(min = 100.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.loading_img),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            
            Spacer(modifier = Modifier.width(20.dp))
            
            Text(
                text = stringResource(Res.string.is_loading),
                color = Color.White,
                fontSize = 19.sp
            )
        }
    }
}

@Preview
@Composable
fun LoadingDialogPreview() {
    Surface(color = Color.Gray) {
        LoadingDialogContent()
    }
}

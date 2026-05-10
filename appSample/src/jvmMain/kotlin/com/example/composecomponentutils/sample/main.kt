package com.example.composecomponentutils.sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ComposeComponentUtils",
    ) {
        App()
    }
}

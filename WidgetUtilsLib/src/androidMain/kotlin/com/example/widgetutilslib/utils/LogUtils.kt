package com.example.widgetutilslib.utils

object LogUtils {
    var TAG: String = "YOONUU-LOG"
    var logOut: Boolean = true

    fun d(message: String?) {
        if (logOut) {
            println("$TAG [D]: ${message ?: "NULL"}")
        }
    }

    fun i(message: String?) {
        if (logOut) {
            println("$TAG [I]: ${message ?: "NULL"}")
        }
    }

    fun e(message: String?) {
        if (logOut) {
            println("$TAG [E]: ${message ?: "NULL"}")
        }
    }
}

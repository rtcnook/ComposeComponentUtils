package com.example.widgetutilslib.utils

import android.os.Environment
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by Lxm on 2024/8/14.
 */
object LogcatToFileUtil {
    private var isLogging = false

    fun startLogging(projectName: String) {
        if (isLogging) return

        isLogging = true
        Thread {
            try {
                // 创建文件并准备写入日�?
                val files = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "$projectName/logcat"
                )
                if (!files.exists()) {
                    files.mkdirs()
                }
                val file = File(files, "log.txt")
                val writer = FileWriter(file, true) // true 表示以追加模式写�?

                // 运行 logcat 命令
                val process = Runtime.getRuntime().exec("logcat")

                val inputStream = process.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))

                var line: String? = bufferedReader.readLine()
                while (isLogging && line != null) {
                    writer.append(line).append("\n")
                    writer.flush() // 刷新缓冲�?
                    line = bufferedReader.readLine()
                }

                writer.close()
                bufferedReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("LogcatUtil", "startLogging: " + e.message)
            }
        }.start()
    }

    fun stopLogging() {
        isLogging = false
    }
}

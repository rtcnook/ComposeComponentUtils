package com.example.widgetutilslib.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(
    private val diskIO: Executor,
    private val networkIo: Executor,
    private val mainThread: Executor
) {

    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(4),
        MainThreadExecutor()
    )

    fun diskIO(): Executor = diskIO

    fun networkIo(): Executor = networkIo

    fun mainThread(): Executor = mainThread

    private class MainThreadExecutor : Executor {
        private val main = Handler(Looper.getMainLooper())

        override fun execute(runnable: Runnable) {
            main.post(runnable)
        }
    }

    companion object {
        @JvmStatic
        var executors: AppExecutors? = null
            private set

        @JvmStatic
        fun initInstance() {
            if (executors == null) {
                synchronized(AppExecutors::class.java) {
                    if (executors == null) {
                        executors = AppExecutors()
                    }
                }
            }
        }
    }
}

package com.example.widgetutilslib.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author qlw
 **/
object DateUtils {

    fun timeFilePath(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA)
        return sdf.format(Date())
    }

    @Throws(ParseException::class)
    fun timeP1(f1: String): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        return sdf.parse(f1)?.time ?: 0L
    }

    @Throws(ParseException::class)
    fun timeP2(f1: String): Date? {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        return sdf.parse(f1)
    }

    @Throws(ParseException::class)
    fun timeP3(f1: String): Date? {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        return sdf.parse(f1)
    }

    @Throws(ParseException::class)
    fun timeP4(f1: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        val date = parser.parse(f1) ?: return ""

        // 2. 格式化为 "MM-dd"
        val formatter = SimpleDateFormat("MM-dd", Locale.CHINA)
        return formatter.format(date)
    }

    fun timeF1(date: Date): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.CHINA)
        return sdf.format(date)
    }

    fun timeF1(ds: Date, de: Date): String {
        return "${timeF1(ds)} - ${timeF1(de)}"
    }

    fun timeF1(tims: Long): String {
        return timeF1(Date(tims))
    }

    fun timeF2(tims: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA)
        return sdf.format(Date(tims))
    }

    fun timeF3(tims: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        return sdf.format(Date(tims))
    }

    fun timeF3(tims: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        return sdf.format(tims)
    }

    fun timeF4(tims: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        return sdf.format(tims)
    }

    fun timeF5(tims: Date): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.CHINA)
        return sdf.format(tims)
    }

    fun timeF6(tims: Date): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.CHINA)
        return sdf.format(tims)
    }

    fun dayStart(): Long {
        return dayStart(Date())
    }

    fun dayStart(date: Date): Long {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun dayEnd(): Long {
        return dayEnd(Date())
    }

    fun dayEnd(date: Date): Long {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}

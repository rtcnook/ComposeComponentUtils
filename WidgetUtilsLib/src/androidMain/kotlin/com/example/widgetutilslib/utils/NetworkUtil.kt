package com.example.widgetutilslib.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.io.IOException
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.UnknownHostException
import java.util.Enumeration

/**
 * @author qlw
 **/
object NetworkUtil {

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetworkInfo
        return network != null && network.isConnected
    }

    fun isReachable(ip: String): Boolean {
        var result = false
        try {
            val address = InetAddress.getByName(ip)
            result = if (address is Inet4Address || address is Inet6Address) {
                address.isReachable(5000)
            } else {
                false
            }
        } catch (e: UnknownHostException) {
            result = false
        } catch (e: IOException) {
            result = false
        }
        return result
    }

    fun isOffline(context: Context): Boolean {
        return !isOnline(context)
    }

    fun getDeviceIP(context: Context): String {
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress
                        && !inetAddress.isAnyLocalAddress
                        && inetAddress is Inet4Address
                    ) {
                        return inetAddress.hostAddress ?: "0.0.0.0"
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "0.0.0.0"
    }

    private fun int2ip(ip: Int): String {
        return (ip and 0xff).toString() + "." +
                (ip shr 8 and 0xff) + "." +
                (ip shr 16 and 0xff) + "." +
                (ip shr 24 and 0xff)
    }
}

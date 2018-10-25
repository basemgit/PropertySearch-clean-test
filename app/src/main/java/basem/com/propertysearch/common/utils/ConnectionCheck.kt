package basem.com.propertysearch.common.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import android.util.Log

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

import android.content.Context.CONNECTIVITY_SERVICE



class ConnectionCheck {

    private var isconnected: Boolean = false
    fun isInternetAvailable(context: Context?): Boolean {
        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    @Throws(IOException::class)
    fun isNetworkAvailable(context: Context): Boolean {
        if (isInternetAvailable(context)) {
            try {

                val urlc = URL("http://www.google.com").openConnection() as HttpURLConnection
                urlc.setRequestProperty("User-Agent", "Test")
                urlc.setRequestProperty("Connection", "close")
                urlc.connectTimeout = 1500
                urlc.connect()
                isconnected = true
            } catch (e: IOException) {
                Log.e("LOG_TAG ", "Error checking internet connection", e)
                isconnected = false
            }

        } else {
            isconnected = false
        }
        return isconnected
    }

    companion object {

        private var TYPE_WIFI = 1
        private var TYPE_MOBILE = 0
        var TYPE_NOT_CONNECTED = -1

        fun getConnectivityStatus(context: Context): Int {
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (null != activeNetwork) {
                when {
                    activeNetwork.type == ConnectivityManager.TYPE_WIFI -> return TYPE_WIFI
                    activeNetwork.type == ConnectivityManager.TYPE_MOBILE -> return TYPE_MOBILE
                    else -> {
                        context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                        return TYPE_NOT_CONNECTED
                    }
                }
            }
            return -1

        }
    }


}

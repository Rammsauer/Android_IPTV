package com.rammsauer.tv

import android.util.Log
import okhttp3.*
import java.io.IOException

class Network {
    private val client = OkHttpClient()

    fun getList(callback: (String) -> Unit){
        val request: Request = Request.Builder()
            .url(gistUrl)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback("")
            }

            override fun onResponse(call: Call, response: Response) {
                callback(response.body!!.string())
            }
        })
    }

    fun checkStatus(
        url: String,
        callback: (Int) -> Unit
    ) {
        if (!url.contains(HTTP_REGEX)) callback(0)

        val request: Request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(0)
            }

            override fun onResponse(call: Call, response: Response) {
                when (response.message) {
                    "OK" ->             callback(1)
                    "Bad Request" ->    callback(0)
                    "Not Found" ->      callback(0)
                    else ->             callback(0)
                }
            }

        })
    }
}
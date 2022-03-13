package com.rammsauer.tv.data

import com.rammsauer.tv.HTTP_REGEX
import com.rammsauer.tv.gistUrl
import okhttp3.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URL

class Network {
    private val client = OkHttpClient()

    suspend fun getList(callback: (String) -> Unit){
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

    fun getImage(
        url: URL,
        callback: (ByteArray?) -> Unit
    ) {
        try {
            url.openStream().use { stream ->
                val buffer = ByteArray(4096)
                val output = ByteArrayOutputStream()

                while (true) {
                    val bytesRead: Int = stream.read(buffer)
                    if (bytesRead < 0) {
                        break
                    }
                    output.write(buffer, 0, bytesRead)
                }

                callback(output.toByteArray())
            }
        }
        catch (e: Exception) {
            callback(null)
        }
    }
}
package com.lmkhant.ichat

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Test
import java.io.IOException
import java.nio.charset.Charset

class ChatGPT {
    //val mediaType: MediaType? = MediaType.("application/json")

    val client = OkHttpClient()

    @Throws(IOException::class)
    fun post(url: String, json: String): String {
        val body: RequestBody = json.toRequestBody(null)
        val request: Request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()
        client.newCall(request).execute().use { response -> return response.body!!.string() }
    }


    @Test
    @Throws(IOException::class)
    fun test() {
        val url = "https://chatgpt-api.shn.hk/v1/"
        val jddata = "{\n  \"model\": \"gpt-3.5-turbo\",\n  \"messages\": [{\"role\": \"user\", \"content\": \"Hello, how are you?\"}]\n}"
        val jdata = "{\"messages\":[{\"role\":\"user\",\"content\":\"Hello, how are you?\"}],\"model\":\"gpt-3.5-turbo\"}".trim()
        val jsonBody = JSONObject()
        val messageArray = JSONArray()
        messageArray.put(JSONObject().put("role","user").put("content","Hello, how are you?"))

        jsonBody.put("model", "gpt-3.5-turbo")

        jsonBody.put("messages", messageArray)

        println(jsonBody.toString())
        println(jdata)
        //assert(jdata == jsonBody.toString())
        val jsonResult = post(url,jddata)
        println(jsonResult)
    }
}
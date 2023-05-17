package com.lmkhant.ichat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lmkhant.ichat.databinding.ActivityMainBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

    companion object{
        val mediaType = "application/json".toMediaType()
    }

    private lateinit var binding: ActivityMainBinding


    lateinit var chatAdapter: ChatAdapter
    lateinit var chatRecyclerView: RecyclerView
    lateinit var viewModel: ChatViewModel

    val client: OkHttpClient = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        chatAdapter = ChatAdapter(viewModel.messageList)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true

        chatRecyclerView = binding.rvChat
        chatRecyclerView.layoutManager = linearLayoutManager
        chatRecyclerView.adapter = chatAdapter

        binding.sendButton.setOnClickListener {
            runOnUiThread {
                viewModel.addMessage(binding.etChat.text.toString(), Message.SEND_BY_ME)
                chatRecyclerView.smoothScrollToPosition(viewModel.messageList.size)
                chatAdapter.notifyDataSetChanged()
                binding.etChat.setText("")
                callAPI(binding.etChat.text.toString())
            }
        }
    }

    fun addResponse(response: String) {
        viewModel.addMessage(response, Message.SEND_BY_OTHER)
    }


    fun callAPI(question: String) {

        val jsonBody = JSONObject()
        val messageArray = JSONArray()
        messageArray.put(JSONObject().put("role","user").put("content",question))

        jsonBody.put("model", "gpt-3.5-turbo")

        jsonBody.put("messages", messageArray)

        val body = jsonBody.toString().toRequestBody(null)
        val request = Request.Builder()
            .url("https://chatgpt-api.shn.hk/v1/")
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                addResponse("Failed to load call due to " + e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val jsonObject = JSONObject(response.body?.string() ?: "")
                    val jsonArray = jsonObject.getJSONArray("choices")
                    val firstChoiceObject = jsonArray.getJSONObject(0)
                    val messageObject = firstChoiceObject.getJSONObject("message")
                    val content = messageObject.getString("content")

                    addResponse(content.trim())
                } else {
                    Log.d("ERROR", "${response.code}, ${response.networkResponse}}")
                    response.body?.let { Log.d("MainActivity", it.string()) }
                    addResponse("Failed to load response due to " + response.body.toString())
                }
            }
        })
    }

}
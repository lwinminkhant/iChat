package com.lmkhant.ichat

import androidx.lifecycle.ViewModel

class ChatViewModel: ViewModel() {
    private val _messageList = mutableListOf<Message>()
    val messageList : List<Message> get() = _messageList

    fun addMessage(string: String, sendBy: String){
        _messageList.add(Message(string, sendBy))
    }

}
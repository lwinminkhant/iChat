package com.lmkhant.ichat

data class Message(val message: String, val sendBy: String) {

    companion object{
        val SEND_BY_ME = "me"
        val SEND_BY_OTHER = "bot"
    }
}

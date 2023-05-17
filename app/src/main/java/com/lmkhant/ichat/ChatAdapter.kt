package com.lmkhant.ichat

import android.icu.util.Measure
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.system.measureNanoTime

class ChatAdapter(private val messageList: List<Message>) : RecyclerView.Adapter<ChatAdapter.MyChatViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyChatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val chatLayout = layoutInflater.inflate(R.layout.chat_item,parent,false)
        return MyChatViewHolder(chatLayout)
    }

    override fun onBindViewHolder(holder: MyChatViewHolder, position: Int) {
        val message = messageList[position]
        if (message.sendBy == "bot"){
            holder.leftChatView.visibility = View.VISIBLE
            holder.rightChatView.visibility = View.GONE
            holder.leftTextView.text = message.message
        }else{
            holder.leftChatView.visibility = View.GONE
            holder.rightChatView.visibility = View.VISIBLE
            holder.rightTextView.text = message.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class MyChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val leftChatView = itemView.findViewById< LinearLayout>(R.id.left_chatView);
        val rightChatView = itemView.findViewById< LinearLayout>(R.id.right_chatView);
        val leftTextView = itemView.findViewById<TextView>(R.id.tv_left_item)
        val rightTextView = itemView.findViewById<TextView>(R.id.tv_right_item)


    }
}
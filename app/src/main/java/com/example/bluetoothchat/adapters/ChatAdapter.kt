package com.example.bluetoothchat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.builders.bluetoothchat.R
import com.example.bluetoothchat.models.MessageModel

class ChatAdapter(var context: Context, var chatList: ArrayList<MessageModel>) :

    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    fun setArrayList(chatList: ArrayList<MessageModel>) {
        this.chatList = chatList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var singleChat = chatList.get(position)
        if (singleChat.byMe) {
            holder.setnMessageLL.visibility = View.VISIBLE
            holder.sentMessageTv.text = singleChat.message
        } else {
            holder.receivedMessageLL.visibility = View.VISIBLE
            holder.receivedMessageTv.text = singleChat.message
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessageTv = itemView.findViewById<TextView>(R.id.msg_sent_tv)
        val receivedMessageTv = itemView.findViewById<TextView>(R.id.msg_received_tv)
        val receivedMessageLL = itemView.findViewById<LinearLayout>(R.id.msg_received_ll)
        val setnMessageLL = itemView.findViewById<LinearLayout>(R.id.msg_sent_ll)
    }
}
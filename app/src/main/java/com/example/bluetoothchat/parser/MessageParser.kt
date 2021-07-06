package com.example.bluetoothchat.parser

import android.util.Log
import com.example.bluetoothchat.AppController
import com.example.bluetoothchat.constants.MessageConstants
import com.example.bluetoothchat.models.MessageModel

class MessageParser {

    private val TAG = "MessageParser"

    fun parseMessage(hashMap: HashMap<String, String>): MessageModel {
        var messageModel: MessageModel
        val message = hashMap[MessageConstants.MESSAGE]
        val uuid = hashMap[MessageConstants.UUID]

        messageModel = MessageModel(message!!, uuid == AppController.deviceUUID)
        return messageModel
    }
}
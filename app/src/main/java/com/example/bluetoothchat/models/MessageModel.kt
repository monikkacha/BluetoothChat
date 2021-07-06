package com.example.bluetoothchat.models

class MessageModel {

    var message: String = ""
    var byMe: Boolean = false

    constructor(message: String, byMe: Boolean) {
        this.message = message
        this.byMe = byMe
    }
}
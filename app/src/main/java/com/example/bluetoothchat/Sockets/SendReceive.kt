package com.example.bluetoothchat.Sockets

import android.bluetooth.BluetoothSocket
import android.util.Log
import com.example.bluetoothchat.callbacks.MessageCallbacks
import java.io.InputStream
import java.io.OutputStream

class SendReceive {

    companion object {

        private val TAG = "SendReceive"

        private lateinit var inputStream: InputStream
        private lateinit var outputStream: OutputStream
        private var bluetoothSocket: BluetoothSocket? = null


        fun init(socket: BluetoothSocket?) {
            if (socket != null) {
                bluetoothSocket = socket!!
                inputStream = socket?.inputStream
                outputStream = socket?.outputStream
            } else {
                Log.e(TAG, "init: socket is null")
            }
        }

        fun listenForOnMessageReceived(receiver: MessageCallbacks) {
            if (bluetoothSocket == null) {
                receiver.onMessageReceived("socket is null")
                return
            }

            var buffer = ByteArray(1024)
            var bytes : Int = 0

            while (true) {
                bytes = inputStream.read(buffer)
                var message = String(buffer, 0, bytes)
                receiver.onMessageReceived(message)
            }
        }

        fun sendMessage(message: String) {
            if (bluetoothSocket == null) {
                Log.e(TAG, "sendMessage: , bluetoothSocket is null")
                return
            }
            outputStream.write(message.toByteArray())
        }
    }
}
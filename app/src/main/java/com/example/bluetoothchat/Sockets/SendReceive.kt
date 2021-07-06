package com.example.bluetoothchat.Sockets

import android.bluetooth.BluetoothSocket
import android.util.Log
import android.widget.Toast
import com.example.bluetoothchat.AppController
import com.example.bluetoothchat.callbacks.MessageCallbacks
import java.io.InputStream
import java.io.OutputStream

class SendReceive {

    companion object {

        private val TAG = "SendReceive"

        private var inputStream: InputStream? = null
        private var outputStream: OutputStream? = null
        private var bluetoothSocket: BluetoothSocket? = null
        private var isReadMessage: Boolean = false

        fun init(socket: BluetoothSocket?) {
            if (socket != null) {
                bluetoothSocket = socket!!
                inputStream = socket?.inputStream
                outputStream = socket?.outputStream
                isReadMessage = true
            } else {
                Log.e(TAG, "init: socket is null")
            }
        }

        fun registerForMessageListening(receiver: MessageCallbacks) {
            if (bluetoothSocket == null) {
                receiver.onMessageReceived("socket is null")
                return
            }

            var buffer = ByteArray(1024)
            var bytes: Int = 0

            while (isReadMessage) {
                bytes = inputStream?.read(buffer)!!
                var message = String(buffer, 0, bytes)
                receiver.onMessageReceived(message)
            }
        }

        fun sendMessage(message: String) {
            if (bluetoothSocket == null) {
                Log.e(TAG, "sendMessage: , bluetoothSocket is null")
                return
            }
            outputStream?.write(message.toByteArray())
        }

        fun unregisterForMessageListening() {
            isReadMessage = false
            inputStream = null;
            outputStream = null;
        }
    }
}
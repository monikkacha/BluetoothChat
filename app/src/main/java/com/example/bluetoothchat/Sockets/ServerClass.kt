package com.example.bluetoothchat.Sockets

import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import com.builders.bluetoothchat.R
import com.example.bluetoothchat.AppController
import com.example.bluetoothchat.callbacks.ServerSocketCallBack
import com.example.bluetoothchat.constants.Constants.Companion.STATE_CONNECTED
import com.example.bluetoothchat.constants.Constants.Companion.STATE_CONNECTING
import com.example.bluetoothchat.constants.Constants.Companion.STATE_LISTENING
import java.lang.Exception
import java.util.*

class ServerClass {

    companion object {

        private lateinit var APP_NAME: String
        private lateinit var UUID: UUID
        private var serverSocket: BluetoothServerSocket? = null
        private var bluetoothSocket: BluetoothSocket? = null

        suspend fun initServer(stateChange: ServerSocketCallBack) {
            setConstants()
            serverSocket =
                AppController.bluetoothAdapter.listenUsingRfcommWithServiceRecord(
                    APP_NAME,
                    UUID
                )

            while (bluetoothSocket == null) {
                try {
                    stateChange.onStateChanged(STATE_CONNECTING)
                    bluetoothSocket = serverSocket?.accept()
                    SendReceive.init(bluetoothSocket)
                } catch (e: Exception) {
                    e.printStackTrace();
                }

                if (bluetoothSocket != null) {
                    stateChange.onStateChanged(STATE_CONNECTED)
                    break
                }
            }
        }

        private fun setConstants() {
            APP_NAME = AppController.context.resources.getString(R.string.app_name)
            UUID =
                java.util.UUID.fromString(AppController.context.resources.getString(R.string.uuid))
        }
    }
}
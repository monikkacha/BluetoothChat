package com.example.bluetoothchat.Sockets

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import com.builders.bluetoothchat.R
import com.example.bluetoothchat.AppController
import com.example.bluetoothchat.callbacks.ServerSocketCallBack
import com.example.bluetoothchat.constants.Constants
import java.lang.Exception
import java.util.*

class ClientClass {
    companion object {
        private lateinit var device: BluetoothDevice
        private lateinit var socket: BluetoothSocket
        private lateinit var uuid: UUID

        suspend fun connectToDevice(requestedDevice: BluetoothDevice , stateChange: ServerSocketCallBack) {
            try {
                initConstants()

                device = AppController.bluetoothAdapter.getRemoteDevice(requestedDevice.address)

                stateChange.onStateChanged(Constants.STATE_CONNECTING)

                socket = device.createRfcommSocketToServiceRecord(uuid)
                socket.connect()


                stateChange.onStateChanged(Constants.STATE_CONNECTED)

                SendReceive.init(socket)

            } catch (e: Exception) {
                e.printStackTrace()
                stateChange.onStateChanged(Constants.STATE_FAILED)
            }
        }

        private fun initConstants() {
            uuid = UUID.fromString(AppController.context.resources.getString(R.string.uuid))
        }
    }
}
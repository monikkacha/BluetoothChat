package com.example.bluetoothchat.utils

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.example.bluetoothchat.AppController

class BluetoothUtils {
    companion object {
        fun initAdapter() {
            AppController.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        }

        fun getListOfPairedDevices(): List<BluetoothDevice> {
            return if (AppController.bluetoothAdapter != null) {
                AppController.bluetoothAdapter.bondedDevices.toList()
            } else {
                ArrayList<BluetoothDevice>()
            }
        }


        fun disable() {
            AppController.bluetoothAdapter.disable()
        }
    }
}
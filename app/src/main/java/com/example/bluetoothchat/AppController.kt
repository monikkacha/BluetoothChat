package com.example.bluetoothchat

import android.app.Application
import android.bluetooth.BluetoothAdapter
import com.example.bluetoothchat.utils.BluetoothUtils

class AppController : Application() {

    companion object {
        lateinit var bluetoothAdapter: BluetoothAdapter
        var isBluetoothOn = false
    }

    override fun onCreate() {
        super.onCreate()
        BluetoothUtils.initAdapter()
    }


}
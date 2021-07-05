package com.example.bluetoothchat

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.example.bluetoothchat.utils.BluetoothUtils

class AppController : Application() {

    companion object {
        lateinit var bluetoothAdapter: BluetoothAdapter
        var isBluetoothOn = false
        lateinit var context : Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        BluetoothUtils.initAdapter()
    }
}
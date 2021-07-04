package com.example.bluetoothchat

import android.app.Application
import android.bluetooth.BluetoothAdapter

class AppController : Application() {

    companion object {
        lateinit var bluetoothAdapter: BluetoothAdapter
    }

    override fun onCreate() {
        super.onCreate()
    }

}
package com.example.bluetoothchat

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.example.bluetoothchat.utils.BluetoothUtils
import com.example.bluetoothchat.utils.PrefUtils

class AppController : Application() {

    companion object {
        var deviceUUID = ""
        lateinit var context: Context
        lateinit var bluetoothAdapter: BluetoothAdapter
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        BluetoothUtils.initAdapter()
        setUUID()
    }

    private fun setUUID() {
        PrefUtils.init()
        deviceUUID = PrefUtils.getDeviceUUID()
    }
}
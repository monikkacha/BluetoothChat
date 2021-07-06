package com.example.bluetoothchat

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.example.bluetoothchat.models.UserModel
import com.example.bluetoothchat.utils.BluetoothUtils
import com.example.bluetoothchat.utils.PrefUtils

class AppController : Application() {

    companion object {
        var deviceUUID = ""
        lateinit var context: Context
        lateinit var bluetoothAdapter: BluetoothAdapter
        var currentUser: UserModel? = null
        var currentConnectedUser: UserModel? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        BluetoothUtils.initAdapter()
        setUUID()
        setUserModel()
    }

    private fun setUserModel() {
        var isUserSet = PrefUtils.isUserSet()
        if (isUserSet) {
            val userName = PrefUtils.getUserName()
            val userColor = PrefUtils.getUserColor()
            val userColorText = PrefUtils.getUserTextColor()
            val userQuote = PrefUtils.getUserQuote()

            currentUser = UserModel(userName, userColor, userColorText, userQuote, deviceUUID)
        }
    }

    private fun setUUID() {
        PrefUtils.init()
        deviceUUID = PrefUtils.getDeviceUUID()
    }
}
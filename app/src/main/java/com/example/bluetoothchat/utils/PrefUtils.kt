package com.example.bluetoothchat.utils

import android.content.Context
import android.content.SharedPreferences
import com.builders.bluetoothchat.R
import com.example.bluetoothchat.AppController
import com.example.bluetoothchat.constants.PrefConstants
import java.util.*

class PrefUtils {

    companion object {
        private var preference: SharedPreferences? = null
        private var preferenceEditor: SharedPreferences.Editor? = null

        fun init() {
            if (preference == null) {
                var fileName = AppController.context.resources.getString(R.string.app_name)
                var mode = Context.MODE_PRIVATE
                preference = AppController.context.getSharedPreferences(fileName, mode)
                preferenceEditor = preference?.edit()
            }
        }

        private fun saveString(key: String, value: String) {
            preferenceEditor?.putString(key, value)
            preferenceEditor?.apply()
            preferenceEditor?.commit()
        }

        private fun getString(key: String): String = preference?.getString(key, "")!!

        fun getDeviceUUID(): String {
            var value = getString(PrefConstants.DEVICE_UUID)
            if (value.isEmpty()) {
                value = UUID.randomUUID().toString()
                saveString(PrefConstants.DEVICE_UUID, value)
            }
            return value
        }
    }
}
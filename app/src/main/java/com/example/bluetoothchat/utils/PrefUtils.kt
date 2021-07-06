package com.example.bluetoothchat.utils

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
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

        private fun saveInt(key: String, value: Int) {
            preferenceEditor?.putInt(key, value)
            preferenceEditor?.apply()
            preferenceEditor?.commit()
        }

        private fun getInt(key: String): Int = preference?.getInt(key, -1)!!

        private fun saveBoolean(key: String, value: Boolean) {
            preferenceEditor?.putBoolean(key, value)
            preferenceEditor?.apply()
            preferenceEditor?.commit()
        }

        private fun getBoolean(key: String): Boolean = preference?.getBoolean(key, false)!!

        fun getDeviceUUID(): String {
            var value = getString(PrefConstants.DEVICE_UUID)
            if (value.isEmpty()) {
                value = UUID.randomUUID().toString()
                saveString(PrefConstants.DEVICE_UUID, value)
            }
            return value
        }

        fun isUserSet(): Boolean = getBoolean(PrefConstants.USER_SET)

        fun setUser() = saveBoolean(PrefConstants.USER_SET, true)

        fun getUserName(): String = getString(PrefConstants.USER_NAME)

        fun setUserName(value: String) = saveString(PrefConstants.USER_NAME, value)

        fun getUserColor(): Int {
            var value: Int = getInt(PrefConstants.USER_COLOR)
            if (value == -1) {
                value = AppController.context.resources.getColor(R.color.sky)
            }
            return value
        }

        fun setUserColor(value: Int) = saveInt(PrefConstants.USER_COLOR, value)

        fun getUserTextColor(): Int {
            var value: Int = getInt(PrefConstants.USER_COLOR_TEXT)
            if (value == -1) {
                value = Color.WHITE
            }
            return value
        }

        fun setUserTextColor(value: Int) = saveInt(PrefConstants.USER_COLOR_TEXT, value)

        fun getUserQuote(): String = getString(PrefConstants.USER_QUOTE)

        fun setUserQuote(value: String) = saveString(PrefConstants.USER_QUOTE, value)

    }
}
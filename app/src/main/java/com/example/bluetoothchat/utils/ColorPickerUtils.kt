package com.example.bluetoothchat.utils

import android.content.Context
import android.util.Log
import com.example.bluetoothchat.callbacks.ColorPickerCallBack
import yuku.ambilwarna.AmbilWarnaDialog

class ColorPickerUtils(var context: Context) {

    private val TAG = "ColorPickerUtils"

    private var defaultColor = -1

    @JvmName("setDefaultColor1")
    fun setDefaultColor(color: Int) {
        defaultColor = color
    }

    fun pickColor(callBack: ColorPickerCallBack) {
        AmbilWarnaDialog(context, defaultColor, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {
                Log.e(TAG, "onCancel: +no Color selected")
            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                callBack.onColorPick(color)
            }

        }).show()
    }

}
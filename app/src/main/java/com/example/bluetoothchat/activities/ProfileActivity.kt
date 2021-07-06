package com.example.bluetoothchat.activities

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.builders.bluetoothchat.MainActivity
import com.builders.bluetoothchat.R
import com.example.bluetoothchat.AppController
import com.example.bluetoothchat.callbacks.ColorPickerCallBack
import com.example.bluetoothchat.models.UserModel
import com.example.bluetoothchat.utils.ColorPickerUtils
import com.example.bluetoothchat.utils.PrefUtils
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    private val TAG = "ProfileActivity"
    private var defaultTextColor: Int = 0
    private var defaultBackgroundColor: Int = 0
    private lateinit var colorPickerUtils: ColorPickerUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setDefaultColor()
        setOnClick()
        setEditTextWatcher()
    }

    private fun setEditTextWatcher() {
        user_name_et.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                name_initial_tv.setText("" + s.get(0))
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    private fun setDefaultColor() {
        colorPickerUtils = ColorPickerUtils(this)
        defaultTextColor = PrefUtils.getUserTextColor()
        defaultBackgroundColor = PrefUtils.getUserColor()
        setColor()
    }

    private fun setColor() {
        text_color_ll.background.setColorFilter(defaultTextColor, PorterDuff.Mode.SRC_ATOP)
        background_ll.background.setColorFilter(defaultBackgroundColor, PorterDuff.Mode.SRC_ATOP)

        avatar_ll.background.setColorFilter(defaultBackgroundColor, PorterDuff.Mode.SRC_ATOP)
        name_initial_tv.setTextColor(defaultTextColor)
    }

    private fun setOnClick() {
        text_color_ll.setOnClickListener {
            changeTextColor()
        }
        background_ll.setOnClickListener {
            changeBackgroundColor()
        }
        complete_btn.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        var userName = user_name_et.text.toString()
        if (userName.isEmpty()) {
            Toast.makeText(this, "User name can not be empty", Toast.LENGTH_SHORT).show()
            return
        }

        var bio = bio_et.text.toString()
        var userModel = UserModel(
            userName,
            defaultBackgroundColor,
            defaultTextColor,
            bio,
            AppController.deviceUUID
        )
        AppController.currentUser = userModel

        PrefUtils.setUserName(userName)
        PrefUtils.setUserColor(defaultBackgroundColor)
        PrefUtils.setUserTextColor(defaultTextColor)
        PrefUtils.setUserQuote(bio)
        PrefUtils.setUser()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun changeBackgroundColor() {
        colorPickerUtils.setDefaultColor(defaultBackgroundColor)
        colorPickerUtils.pickColor(object : ColorPickerCallBack {
            override fun onColorPick(color: Int) {
                Log.e(TAG, "onColorPick: " + color)
                defaultBackgroundColor = color
                setColor()
            }
        })
    }

    private fun changeTextColor() {
        colorPickerUtils.setDefaultColor(defaultTextColor)
        colorPickerUtils.pickColor(object : ColorPickerCallBack {
            override fun onColorPick(color: Int) {
                Log.e(TAG, "onColorPick: " + color)
                defaultTextColor = color
                setColor()
            }
        })
    }
}
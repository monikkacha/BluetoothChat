package com.example.bluetoothchat.parser

import com.example.bluetoothchat.constants.PrefConstants
import com.example.bluetoothchat.models.UserModel

class UserParser {

    fun parseUser(hashMap: HashMap<String, String>): UserModel {
        var userModel: UserModel

        var userName = hashMap[PrefConstants.USER_NAME]
        var color = hashMap[PrefConstants.USER_COLOR]
        var textColor = hashMap[PrefConstants.USER_COLOR_TEXT]
        var uuid = hashMap[PrefConstants.DEVICE_UUID]
        var quote = hashMap[PrefConstants.USER_QUOTE]

        userModel = UserModel(userName, color?.toInt(), textColor?.toInt(), quote, uuid)

        return userModel
    }

}
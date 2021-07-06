package com.example.bluetoothchat.utils

import java.lang.StringBuilder

class HashMapUtils {

    companion object {

        fun stringTohHashMap(value: String): HashMap<String, String> {
            var hashMap = HashMap<String, String>()
            var keyValuePairs = value.split(",")
            for (pair in keyValuePairs) {
                var keyValuePairArray = pair.split("=")
                hashMap.put(keyValuePairArray.get(0).trim(), keyValuePairArray.get(1).trim())
            }
            return hashMap
        }

        fun hashMapToString(map: HashMap<String, String>): String {
            var isFirst = true
            var stringBuilder = StringBuilder()
            for ((key, value) in map) {
                if (isFirst) {
                    stringBuilder.append("$key = $value")
                    isFirst = false
                } else {
                    stringBuilder.append(", $key = $value")
                }
            }
            return stringBuilder.toString()
        }
    }
}
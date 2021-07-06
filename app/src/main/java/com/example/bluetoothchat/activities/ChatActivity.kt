package com.example.bluetoothchat.activities

import android.graphics.PorterDuff
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.builders.bluetoothchat.R
import com.example.bluetoothchat.AppController
import com.example.bluetoothchat.Sockets.SendReceive
import com.example.bluetoothchat.adapters.ChatAdapter
import com.example.bluetoothchat.callbacks.MessageCallbacks
import com.example.bluetoothchat.constants.MessageConstants
import com.example.bluetoothchat.constants.PrefConstants
import com.example.bluetoothchat.models.MessageModel
import com.example.bluetoothchat.parser.MessageParser
import com.example.bluetoothchat.parser.UserParser
import com.example.bluetoothchat.utils.HashMapUtils
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.avatar_ll
import kotlinx.android.synthetic.main.activity_chat.name_initial_tv
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ChatActivity : AppCompatActivity() {

    lateinit var chatList: ArrayList<MessageModel>
    var chatAdapter: ChatAdapter? = null
    var messageParser = MessageParser()
    var userParser = UserParser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        onClick()
        setToolbar()

        GlobalScope.launch {
            sendUserData()
            listenForMessage()
        }
    }

    private fun sendUserData() {
        var hashMap = HashMap<String, String>()
        hashMap.put(MessageConstants.MESSAGE_TYPE, MessageConstants.MESSAGE_TYPE_INFO)

        hashMap.put(PrefConstants.USER_NAME, AppController.currentUser?.name!!)
        hashMap.put(
            PrefConstants.USER_COLOR_TEXT,
            AppController.currentUser?.colorText.toString()!!
        )
        hashMap.put(PrefConstants.USER_COLOR, AppController.currentUser?.color.toString()!!)
        hashMap.put(PrefConstants.USER_QUOTE, AppController.currentUser?.quote!!)
        hashMap.put(PrefConstants.DEVICE_UUID, AppController.deviceUUID)


        var processedMessage = HashMapUtils.hashMapToString(hashMap)
        SendReceive.sendMessage(processedMessage)
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun listenForMessage() {
        chatList = ArrayList()
        SendReceive.registerForMessageListening(object : MessageCallbacks {
            override fun onMessageReceived(msg: String) {
                GlobalScope.launch(Dispatchers.Main) {
                    processMessage(msg)
                    makeABeep()
                }
            }
        })
    }

    private fun makeABeep() {
        val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        toneGenerator.startTone(ToneGenerator.TONE_SUP_CONFIRM, 150)
    }

    private fun processMessage(msg: String) {
        var hashMap = HashMapUtils.stringTohHashMap(msg)
        if (hashMap[MessageConstants.MESSAGE_TYPE] == MessageConstants.MESSAGE_TYPE_INFO) {
            AppController.currentConnectedUser = userParser.parseUser(hashMap)
            setInfo()
        } else {
            var messageModel = messageParser.parseMessage(hashMap)
            chatList.add(messageModel)
            initRecyclerView()
        }
    }

    private fun setInfo() {
        var initial = AppController.currentConnectedUser?.name?.toUpperCase()?.get(0)
        var textColor = AppController.currentConnectedUser?.colorText!!
        var backgroundColor = AppController.currentConnectedUser?.color!!
        var userName = AppController.currentConnectedUser?.name!!

        name_initial_tv.text = "$initial"
        name_initial_tv.setTextColor(textColor)
        second_user_name.setText(userName)
        avatar_ll.background.setColorFilter(backgroundColor, PorterDuff.Mode.SRC_ATOP)
    }

    private fun initRecyclerView() {
        var layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.stackFromEnd = true

        recycler_view.layoutManager = layoutManager
        recycler_view.setHasFixedSize(false)
        chatAdapter = ChatAdapter(this, chatList)
        recycler_view.adapter = chatAdapter
    }

    private fun onClick() {
        send_btn.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        var message = message_et.text.toString()
        if (message.isEmpty()) {
            Toast.makeText(this, "message can not be empty", Toast.LENGTH_SHORT).show()
            return
        }

        var hashMap = HashMap<String, String>()
        hashMap.put(MessageConstants.MESSAGE_TYPE, MessageConstants.MESSAGE_TYPE_CHAT)
        hashMap.put(MessageConstants.UUID, AppController.deviceUUID)
        hashMap.put(MessageConstants.MESSAGE, message)

        var processedMessage = HashMapUtils.hashMapToString(hashMap)
        SendReceive.sendMessage(processedMessage)

        message_et.setText("")

        processMessage(processedMessage)
    }

    override fun onDestroy() {
        super.onDestroy()
        SendReceive.unregisterForMessageListening()
    }
}
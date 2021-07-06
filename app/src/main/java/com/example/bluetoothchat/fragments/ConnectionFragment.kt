package com.example.bluetoothchat.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.builders.bluetoothchat.R
import com.example.bluetoothchat.Sockets.ServerClass
import com.example.bluetoothchat.activities.ChatActivity
import com.example.bluetoothchat.adapters.ConnectionAdapter
import com.example.bluetoothchat.callbacks.ServerSocketCallBack
import com.example.bluetoothchat.utils.BluetoothUtils
import com.example.bluetoothchat.constants.Constants
import kotlinx.android.synthetic.main.fragment_connection.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConnectionFragment : Fragment(), ServerSocketCallBack {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_connection, container, false)
    }


    override fun onResume() {
        super.onResume()
        initAdapter()
        startServer()
    }

    private fun initAdapter() {
        var pairedDeviceList = BluetoothUtils.getListOfPairedDevices()
        recycler_view.layoutManager = LinearLayoutManager(activity?.baseContext)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = ConnectionAdapter(pairedDeviceList, activity?.baseContext!!, this)
    }

    private fun startServer() {
        GlobalScope.launch {
            ServerClass.initServer(object : ServerSocketCallBack {
                override fun onStateChanged(state: String) {
                    GlobalScope.launch(Dispatchers.Main) {
                        stateUpdate(state)
                    }
                }
            })
        }
    }

    fun stateUpdate(state: String) {
        when (state) {
            Constants.STATE_CONNECTED -> {
                setConnected()
            }
            Constants.STATE_CONNECTING -> current_state.text = "Connecting..."
            Constants.STATE_FAILED -> current_state.text = "Failed"
            Constants.STATE_SCANNING -> current_state.text = "Scanning..."
            Constants.STATE_LISTENING -> current_state.text = "Listening..."
            else -> current_state.text = "Failed"
        }
    }

    private fun setConnected() {
        GlobalScope.launch(Dispatchers.Main) {
            current_state.text = "Connected"
            context?.startActivity(Intent(context, ChatActivity::class.java))
        }
    }

    override fun onStateChanged(state: String) {

        GlobalScope.launch(Dispatchers.Main) {
            stateUpdate(state)
        }
    }
}
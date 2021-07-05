package com.example.bluetoothchat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.builders.bluetoothchat.R
import com.example.bluetoothchat.Sockets.ServerClass
import com.example.bluetoothchat.adapters.ConnectionAdapter
import com.example.bluetoothchat.callbacks.ServerSocketCallBack
import com.example.bluetoothchat.utils.BluetoothUtils
import com.example.bluetoothchat.utils.Constants
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
                    stateUpdate(state)
                }
            })
        }
    }

    fun stateUpdate(state: String) {
        when (state) {
            Constants.STATE_CONNECTED -> current_state.text = "Connected"
            Constants.STATE_CONNECTING -> current_state.text = "Connecting..."
            Constants.STATE_FAILED -> current_state.text = "Failed"
            Constants.STATE_SCANNING -> current_state.text = "Scanning..."
            else -> current_state.text = "Failed"
        }
    }

    override fun onStateChanged(state: String) {

        GlobalScope.launch(Dispatchers.Main) {
            stateUpdate(state)
        }
    }
}
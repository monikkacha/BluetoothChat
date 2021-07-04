package com.example.bluetoothchat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.builders.bluetoothchat.R
import com.example.bluetoothchat.adapters.ConnectionAdapter
import com.example.bluetoothchat.utils.BluetoothUtils
import kotlinx.android.synthetic.main.fragment_connection.*

class ConnectionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_connection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        var pairedDeviceList = BluetoothUtils.getListOfPairedDevices()

        recycler_view.layoutManager = LinearLayoutManager(activity?.baseContext)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = ConnectionAdapter(pairedDeviceList, activity?.baseContext!!)
    }
}
package com.example.bluetoothchat.adapters

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.builders.bluetoothchat.R
import com.example.bluetoothchat.Sockets.ClientClass
import com.example.bluetoothchat.callbacks.ServerSocketCallBack
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConnectionAdapter(
    var list: List<BluetoothDevice>,
    var mContext: Context,
    var onStateChange: ServerSocketCallBack
) :
    RecyclerView.Adapter<ConnectionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.connection_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var singleDevice = list.get(position)
        holder.bluetoothDeviceName.text = singleDevice.name
        holder.itemView.setOnClickListener {
            connectToDevice(singleDevice)
        }
    }

    private fun connectToDevice(device: BluetoothDevice) {
        GlobalScope.launch {
            ClientClass.connectToDevice(device, object : ServerSocketCallBack {
                override fun onStateChanged(state: String) {
                    updateState(state)
                }
            })
        }
    }

    private fun updateState(state: String) {
        onStateChange.onStateChanged(state)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bluetoothIcon = itemView.findViewById<ImageView>(R.id.bluetooth_icon_iv)
        var bluetoothDeviceName = itemView.findViewById<TextView>(R.id.bluetooth_device_name_tv)
    }
}
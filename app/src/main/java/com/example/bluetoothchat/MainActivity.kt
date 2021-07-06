package com.builders.bluetoothchat

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bluetoothchat.AppController
import com.example.bluetoothchat.adapters.HomeTabAdapter
import com.example.bluetoothchat.utils.BluetoothUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val REQUEST_ENABLE_BT = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        initTabs()
        checkBluetooth()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initTabs() {
//        tab_layout.addTab(tab_layout.newTab().setText(resources.getString(R.string.recent)))
        tab_layout.addTab(tab_layout.newTab().setText(resources.getString(R.string.connection)))
//        tab_layout.addTab(tab_layout.newTab().setText(resources.getString(R.string.media)))
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        var tabAdapter = HomeTabAdapter(supportFragmentManager)
        view_pager.adapter = tabAdapter

        view_pager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tab_layout))

        tab_layout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun checkBluetooth() {
        if (!AppController.bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.bluetooth_warning),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BluetoothUtils.disable()
    }
}
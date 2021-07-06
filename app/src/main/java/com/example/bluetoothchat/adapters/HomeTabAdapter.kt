package com.example.bluetoothchat.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.bluetoothchat.fragments.ConnectionFragment
import com.example.bluetoothchat.fragments.RecentChatFragment
import com.example.bluetoothchat.fragments.SharedMediaFragment

class HomeTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ConnectionFragment()
            1 -> ConnectionFragment()
            2 -> SharedMediaFragment()
            else -> RecentChatFragment()
        }
    }
}
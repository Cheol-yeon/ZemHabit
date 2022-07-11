package com.example.zemhabit.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zemhabit.Fragment.CompletionFragment
import com.example.zemhabit.Fragment.ProceedFragment
import com.example.zemhabit.Fragment.WaitingFragment

class SectionsPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return ProceedFragment()
            1 -> return CompletionFragment()
            2 -> return WaitingFragment()
        }
        return ProceedFragment()
    }

    override fun getItemCount(): Int {
        return 3
    }
}
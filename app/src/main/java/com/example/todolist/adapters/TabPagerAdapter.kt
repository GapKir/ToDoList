package com.example.todolist.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todolist.screens.MainScreen

class TabPagerAdapter(
    fragment: FragmentActivity
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> MainScreen.newInstance(IN_PROGRESS)
            1 -> MainScreen.newInstance(DONE)
            else -> MainScreen.newInstance(DELETED)
        }
    }



    companion object{
        const val IN_PROGRESS = "IN PROGRESS"
        const val DONE = "DONE"
        const val DELETED = "DELETED"
    }
}
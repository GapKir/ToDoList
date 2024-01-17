package com.example.todolist.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todolist.base_abstracts.BaseScreen
import com.example.todolist.screens.MainScreen

class TabPagerAdapter(
    fragment: FragmentActivity
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> BaseScreen.createInstance<MainScreen>(BaseScreen.SCREENS.IN_PROGRESS)
            1 -> BaseScreen.createInstance<MainScreen>(BaseScreen.SCREENS.DONE)
            else -> BaseScreen.createInstance<MainScreen>(BaseScreen.SCREENS.DELETED)
        }
    }
}
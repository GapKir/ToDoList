package com.example.todolist.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todolist.base_abstracts.BaseFragment
import com.example.todolist.base_abstracts.TaskCategories
import com.example.todolist.fragments.MainFragment

class TabPagerAdapter(
    fragment: FragmentActivity
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> BaseFragment.createInstance<MainFragment>(TaskCategories.IN_PROGRESS)
            1 -> BaseFragment.createInstance<MainFragment>(TaskCategories.DONE)
            else -> BaseFragment.createInstance<MainFragment>(TaskCategories.DELETED)
        }
    }
}
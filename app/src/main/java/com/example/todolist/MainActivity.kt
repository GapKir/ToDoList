package com.example.todolist

import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.todolist.adapters.TabPagerAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.screens.ProfileScreen
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TabPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TabPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager){tab, position ->
            tab.text = when(position){
                0 -> TabPagerAdapter.IN_PROGRESS
                1 -> TabPagerAdapter.DONE
                else -> TabPagerAdapter.DELETED
            }
        }.attach()

        with(binding){
            appbar.setNavigationOnClickListener {
                drawer.openDrawer(GravityCompat.START)
                supportFragmentManager.beginTransaction()
                    .add(R.id.frame_layout, ProfileScreen())
                    .commit()

            }
        }
    }

}
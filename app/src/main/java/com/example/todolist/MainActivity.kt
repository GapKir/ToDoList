package com.example.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.todolist.adapters.TabPagerAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.base_abstracts.BaseScreen
import com.example.todolist.base_abstracts.DialogListener
import com.example.todolist.screens.ProfileScreen
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TabPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initDrawerLayout()

        binding.fab.setOnClickListener {
            fabClick()
        }
    }

    private fun initAdapter() {
        adapter = TabPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> BaseScreen.SCREENS.IN_PROGRESS.name
                1 -> BaseScreen.SCREENS.DONE.name
                else -> BaseScreen.SCREENS.DELETED.name
            }
        }.attach()
    }

    private fun initDrawerLayout() {
        with(binding) {
            appbar.setNavigationOnClickListener {
                drawer.openDrawer(GravityCompat.START)
                supportFragmentManager.beginTransaction()
                    .add(R.id.frame_layout, BaseScreen.createInstance<ProfileScreen>())
                    .commit()
            }
        }
    }

    private fun fabClick() {
        val currentFragment = supportFragmentManager
            .findFragmentByTag("f" + adapter.getItemId(binding.viewPager.currentItem)) as? DialogListener
        currentFragment?.let { it.fabClick() }
    }
}
package com.example.todolist

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.todolist.adapters.TabPagerAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.screens.AddTaskDialog
import com.example.todolist.screens.MainScreen
import com.example.todolist.screens.ProfileScreen
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.runBlocking

typealias DialogListener = (type: String, title: String, desc: String?, uri: Uri?) -> Unit

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
            createDialog()
        }
    }

    private fun createDialog() {
        AddTaskDialog(listener).show(supportFragmentManager, null)
    }

    private fun initAdapter() {
        adapter = TabPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> TabPagerAdapter.IN_PROGRESS
                1 -> TabPagerAdapter.DONE
                else -> TabPagerAdapter.DELETED
            }
        }.attach()
    }

    private fun initDrawerLayout() {
        with(binding) {
            appbar.setNavigationOnClickListener {
                drawer.openDrawer(GravityCompat.START)
                supportFragmentManager.beginTransaction()
                    .add(R.id.frame_layout, ProfileScreen())
                    .commit()
            }
        }
    }

    private val listener: DialogListener = { type, title, desc, uri ->
        val currentFragment = supportFragmentManager
            .findFragmentByTag("f" + adapter.getItemId(binding.viewPager.currentItem)) as? MainScreen
        currentFragment?.let { it.listener(type, title, desc, uri) }
    }
}
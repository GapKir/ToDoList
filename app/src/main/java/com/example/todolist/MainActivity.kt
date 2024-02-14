package com.example.todolist

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.todolist.adapters.TabPagerAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.base_abstracts.BaseFragment
import com.example.todolist.base_abstracts.DialogListener
import com.example.todolist.base_abstracts.TaskCategories
import com.example.todolist.fragments.ProfileFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TabPagerAdapter
    private val requestPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initDrawerLayout()
        getPermissions()

        binding.fab.setOnClickListener {
            fabClick()
        }
    }

    private fun getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.SCHEDULE_EXACT_ALARM))
        }
    }

    private fun initAdapter() {
        adapter = TabPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> TaskCategories.IN_PROGRESS.name
                1 -> TaskCategories.DONE.name
                else -> TaskCategories.DELETED.name
            }
        }.attach()
    }

    private fun initDrawerLayout() {
        with(binding) {
            appbar.setNavigationOnClickListener {
                drawer.openDrawer(GravityCompat.START)
                supportFragmentManager.beginTransaction()
                    .add(R.id.frame_layout, BaseFragment.createInstance<ProfileFragment>())
                    .commit()
            }
        }
    }

    private fun fabClick() {
        val currentFragment = supportFragmentManager
            .findFragmentByTag("f" + adapter.getItemId(binding.viewPager.currentItem)) as? DialogListener
        currentFragment?.fabClick()
    }
}
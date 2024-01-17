package com.example.todolist.screens

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapters.TaskAdapter
import com.example.todolist.base_abstracts.BaseScreen
import com.example.todolist.base_abstracts.DialogListener
import com.example.todolist.databinding.FragmentInProgressBinding
import com.example.todolist.viewmodels.MainScreenViewModel

class MainScreen : BaseScreen<MainScreenViewModel>(MainScreenViewModel::class.java),
    DialogListener {
    private lateinit var binding: FragmentInProgressBinding
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInProgressBinding.inflate(layoutInflater, container, false)
        createAdapter()
        initViewModel()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTasks(super.currentScreenName)
    }

    override fun dialogListener(type: SCREENS, title: String, desc: String?, uri: Uri?) {
        super.viewModel.addTask(type =  type, title = title, desc = desc, uri = uri)
    }

    private fun initViewModel() {
        super.viewModel.tasks.observe(viewLifecycleOwner){
            adapter.updateData(it)
        }
    }

    private fun createAdapter() {
        adapter = TaskAdapter(emptyList())
        val layoutManager = LinearLayoutManager(requireContext())

        binding.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        }
    }

}
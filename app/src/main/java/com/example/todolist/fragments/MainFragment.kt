package com.example.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapters.TaskAdapter
import com.example.todolist.base_abstracts.BaseFragment
import com.example.todolist.base_abstracts.DialogListener
import com.example.todolist.base_abstracts.TaskCategories
import com.example.todolist.databinding.FragmentInProgressBinding
import com.example.todolist.viewmodels.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment(),
    DialogListener {
    private lateinit var binding: FragmentInProgressBinding
    private lateinit var adapter: TaskAdapter
    override val viewModel: MainScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInProgressBinding.inflate(layoutInflater, container, false)
        createAdapter()
        initViewModel()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getTasks(super.currentScreenName)
    }

    override fun dialogListener(type: TaskCategories, title: String, desc: String?, uri: String?) {
        viewModel.addTask(
            type = type,
            title = title,
            desc = desc,
            uri = uri,
            super.currentScreenName
        )
    }

    private fun initViewModel() {
        viewModel.tasks.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }

        viewModel.shouldShowProgress.observe(viewLifecycleOwner) { showProgress ->
            if (showProgress) {
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
            }
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
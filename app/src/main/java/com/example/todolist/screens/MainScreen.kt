package com.example.todolist.screens

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapters.TabPagerAdapter
import com.example.todolist.adapters.TaskAdapter
import com.example.todolist.databinding.FragmentInProgressBinding
import com.example.todolist.viewmodels.MainScreenViewModel
import com.example.todolist.viewmodels.ViewModelFactory

typealias DialogListener = (type: String, title: String, desc: String?, uri: Uri?) -> Unit

class MainScreen : Fragment() {
    private lateinit var binding: FragmentInProgressBinding
    private lateinit var adapter: TaskAdapter

    private val screenName: String by lazy { arguments?.getString(KEY_ARG) ?: TabPagerAdapter.IN_PROGRESS }
    private val viewModel: MainScreenViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(requireContext()))[MainScreenViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInProgressBinding.inflate(layoutInflater, container, false)

        createAdapter()
        initViewModel()

        binding.fab.setOnClickListener {
            AddTaskDialog(listener).show(parentFragmentManager, null)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTasks(screenName)
    }

    private fun initViewModel() {
        viewModel.tasks.observe(viewLifecycleOwner){
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

    private val listener: DialogListener = { type, title, desc, uri ->
        viewModel.addTask(type =  type, title = title, desc = desc, uri = uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            MainScreen().apply {
                arguments = Bundle().apply {
                    putString(KEY_ARG, param)

                }
            }

        private const val KEY_ARG = "KEY_ARG"
    }

}
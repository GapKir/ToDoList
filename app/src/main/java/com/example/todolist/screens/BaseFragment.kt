package com.example.todolist.screens

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
import com.example.todolist.viewmodels.BaseViewModel

class BaseFragment : Fragment() {
    private lateinit var binding: FragmentInProgressBinding
    private lateinit var adapter: TaskAdapter

    private val screenName: String by lazy { arguments?.getString(KEY_ARG) ?: TabPagerAdapter.IN_PROGRESS }
    private val viewModel: BaseViewModel by lazy { ViewModelProvider(this)[BaseViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInProgressBinding.inflate(layoutInflater, container, false)

        createAdapter()
        initViewModel()

        return binding.root
    }

    private fun initViewModel() {
        viewModel.getTasks(screenName)
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

    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            BaseFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ARG, param)

                }
            }

        private const val KEY_ARG = "KEY_ARG"
    }
}
package com.example.todolist.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.DialogListener
import com.example.todolist.adapters.TabPagerAdapter
import com.example.todolist.adapters.TaskAdapter
import com.example.todolist.databinding.FragmentInProgressBinding
import com.example.todolist.viewmodels.MainScreenViewModel
import com.example.todolist.viewmodels.ViewModelFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainScreen : Fragment() {
    private lateinit var binding: FragmentInProgressBinding
    private lateinit var adapter: TaskAdapter
    private val disposableBag = CompositeDisposable()

    private val screenName: String by lazy { arguments?.getString(KEY_ARG) ?: TabPagerAdapter.IN_PROGRESS }
    private val viewModel: MainScreenViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(requireContext(), disposableBag))[MainScreenViewModel::class.java]
    }

    val listener: DialogListener = { type, title, desc, uri ->
        viewModel.addTask(type =  type, title = title, desc = desc, uri = uri)
    }

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
        viewModel.getTasks(screenName)
    }

    override fun onDestroy() {
        disposableBag.clear()
        super.onDestroy()
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

    companion object {
        fun newInstance(param: String) =
            MainScreen().apply {
                arguments = Bundle().apply {
                    putString(KEY_ARG, param)
                }
            }

        private const val KEY_ARG = "KEY_ARG"
    }

}
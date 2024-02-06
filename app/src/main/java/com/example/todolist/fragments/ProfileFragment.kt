package com.example.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.todolist.R
import com.example.todolist.base_abstracts.BaseFragment
import com.example.todolist.databinding.ProfilePageBinding
import com.example.todolist.viewmodels.ProfileViewModel


class ProfileFragment: BaseFragment<ProfileViewModel>(ProfileViewModel::class.java) {
    private lateinit var binding: ProfilePageBinding

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { galleryUri ->
        galleryUri?.let {
            binding.profilePhoto.setImageURI(it)
            viewModel.setUserPhoto(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfilePageBinding.inflate(inflater, container, false)
        initViewModel()
        return binding.root
    }

    private fun initViewModel() {
        viewModel.isEditMode.observe(viewLifecycleOwner) { isEditMode ->
            updateUI(isEditMode)
            updateListeners(isEditMode)
        }

        viewModel.userName.observe(viewLifecycleOwner) { userName ->
            binding.profileName.setText(userName)
        }

        viewModel.userPhoto.observe(viewLifecycleOwner) { userPhoto ->
            binding.profilePhoto.setImageURI(userPhoto)
        }
    }


    private fun updateListeners(isEditMode: Boolean) {
        with(binding) {
            if (isEditMode) {
                profilePhoto.setOnClickListener {
                    galleryLauncher.launch("image/*")
                }
                profileButton.setOnClickListener {
                    val userName = binding.profileName.text.toString()
                    viewModel.setUserName(userName)
                    viewModel.setEditMode(false)
                }
            } else {
                profileButton.setOnClickListener {
                    viewModel.setEditMode(true)
                }
            }
        }

    }

    private fun updateUI(isEditMode: Boolean) {
        with(binding) {
            if (isEditMode) {
                profilePhoto.isEnabled = true
                profileName.isEnabled = true
                profileButton.text = getText(R.string.save_info)
            } else {
                profilePhoto.isEnabled = false
                profileName.isEnabled = false
                profileButton.text = getText(R.string.change_info)
            }
        }
    }


}
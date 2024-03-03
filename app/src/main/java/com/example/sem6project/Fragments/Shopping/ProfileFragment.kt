package com.example.sem6project.Fragments.Shopping

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sem6project.R
import com.example.sem6project.activities.LoginActivity
import com.example.sem6project.databinding.FragmentProfileBinding
import com.example.sem6project.viewmodel.ProfileViewModel

class ProfileFragment:Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    val viewModel by viewModels<ProfileViewModel> ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.linearLogOut.setOnClickListener {
            viewModel.logout()
            val intent=Intent(requireContext(),LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
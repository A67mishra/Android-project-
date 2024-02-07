package com.example.sem6project.Fragments.LoginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sem6project.R
import com.example.sem6project.databinding.FragmentAccountOptionBinding

class AccountoptionsFragment:Fragment(R.layout.fragment_account_option) {
    private lateinit var binding: FragmentAccountOptionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAccountOptionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.RegisterAccountoption.setOnClickListener{
            findNavController().navigate(R.id.action_accountoptionsFragment_to_registerFragment)
        }
        binding.loginAccountoption.setOnClickListener{
            findNavController().navigate(R.id.action_accountoptionsFragment_to_loginFragment)
        }
    }
}
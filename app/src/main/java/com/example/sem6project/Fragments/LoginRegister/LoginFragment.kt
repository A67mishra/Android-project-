package com.example.sem6project.Fragments.LoginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sem6project.R
import com.example.sem6project.activities.ShoppingActivity
import com.example.sem6project.databinding.FragmentLoginBinding
import com.example.sem6project.dialog.setupBottomDialog
import com.example.sem6project.util.Resource
import com.example.sem6project.viewmodel.LoginViewmodel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment:Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding


    private val viewModel by viewModels<LoginViewmodel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.Donthaveaccount.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.forgotpasswordLogin.setOnClickListener {
            setupBottomDialog { email->
                viewModel.resetpassword(email)
            }
        }

        binding.apply {
            loginbutton.setOnClickListener{
                val email= emaillogin.text.toString().trim()
                val password=passwordlogin.text.toString()
                viewModel.login(email,password)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.login.collect{
                when(it){
                    is Resource.Loading->{
                        binding.loginbutton.startAnimation()
                    }
                    is Resource.Success->{
                        binding.loginbutton.revertAnimation()
                        Intent(requireActivity(), ShoppingActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    is Resource.Error->{
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                        binding.loginbutton.revertAnimation()
                    }
                    else -> {
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect{
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        Snackbar.make(requireView(),"Reset link was sent to your email", Snackbar.LENGTH_LONG).show()
                    }
                    is Resource.Error -> {
                        Snackbar.make(requireView(),"Error: ${it.message}", Snackbar.LENGTH_LONG).show()
                    }
                    else -> Unit

                }
            }
        }
    }
}

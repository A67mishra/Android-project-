
package com.example.sem6project.Fragments.LoginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sem6project.R
import com.example.sem6project.data.User
import com.example.sem6project.databinding.FragmentRegisterBinding
import com.example.sem6project.util.RegisterValidation
import com.example.sem6project.util.Resource
import com.example.sem6project.viewmodel.RegisterViewmodel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext


private val TAG="RegisterFragment"

@AndroidEntryPoint
class RegisterFragment:Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private  val viewModel by viewModels<RegisterViewmodel  >()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            registerbutton.setOnClickListener(){
                val user=User(
                    redit1.text.toString().trim(),
                    redit2.text.toString().trim(),
                    redit3.text.toString().trim(),
                )
                val  password=redit4.text.toString()
                viewModel.createAccountWithEmailAndPassword(user,password)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.register.collect{
                when(it){
                    is Resource.Loading->{
                        binding.registerbutton.startAnimation()
                    }
                    is Resource.Success -> {
                        Log.d("test",it.data.toString())
                        binding.registerbutton.revertAnimation()
                        Toast.makeText(requireContext(),"Registered Successfully!",Toast.LENGTH_LONG).show()

                    }
                    is Resource.Error -> {
                        Log.e(TAG,it.message.toString())
                        binding.registerbutton.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect{ validation->
                if(validation.email is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.redit3.apply {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }

                if (validation.password is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.redit4.apply {
                            requestFocus()
                            error= validation.password.message
                        }
                    }
                }
            }

        }


        binding.alreadyhaveaccount.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}
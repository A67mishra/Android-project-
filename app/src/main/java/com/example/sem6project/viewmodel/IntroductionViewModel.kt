package com.example.sem6project.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sem6project.R
import com.example.sem6project.util.Constants.INTRODUCTION_KEY
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth
)
    :ViewModel()  {

    private val _navigate= MutableStateFlow(0)
    val navigate :StateFlow<Int> =_navigate

//Companion object serves as a way to define static methods and properties for a class
    companion object{
        const val SHOPPING_ACTIVITY=17
        const val ACCOUNT_OPTION_FRAGMENT= R.id.action_introductionFragment_to_accountoptionsFragment
    }

    init {
        val isButtonClick= sharedPreferences.getBoolean(INTRODUCTION_KEY,false)
        val user=firebaseAuth.currentUser
        if (user!=null){
            viewModelScope.launch {
                _navigate.emit(SHOPPING_ACTIVITY)
            }
        }else if(isButtonClick){
            viewModelScope.launch {
                _navigate.emit(ACCOUNT_OPTION_FRAGMENT)
            }
        }else{
            Unit
        }
    }
    fun startButtonClick(){
        sharedPreferences.edit().putBoolean(INTRODUCTION_KEY,true).apply()
    }
}
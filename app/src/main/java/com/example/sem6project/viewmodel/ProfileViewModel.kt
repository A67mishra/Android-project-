package com.example.sem6project.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sem6project.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
):ViewModel() {

    private val _user= MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val user=_user.asStateFlow()

    fun logout(){
        auth.signOut()
    }
}
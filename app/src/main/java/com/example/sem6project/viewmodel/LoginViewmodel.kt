package com.example.sem6project.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sem6project.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
):ViewModel() {

// we use mutablesharedflow when we want to send an event only once into the UI.

    private val _login = MutableSharedFlow<Resource<FirebaseUser>>()
    val login=_login.asSharedFlow()

    private val _resetPassword= MutableSharedFlow<Resource<String>>()
    val resetPassword= _resetPassword.asSharedFlow()

    fun login(email:String,password:String){
        viewModelScope.launch{_login.emit(Resource.Loading())}
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
            viewModelScope.launch {
                it.user?.let {
                    _login.emit(Resource.Success(it))
                }
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                _login.emit(Resource.Error(it.message.toString()))
            }
        }
    }
//viewModelScope is used to manage the lifecycle of the coroutine within a Viewmodel.
    fun resetpassword(email:String){
        viewModelScope.launch {
            _resetPassword.emit(Resource.Loading())
        }
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    viewModelScope.launch {
                        _resetPassword.emit(Resource.Success(email))
                    }

                }.addOnFailureListener{
                    viewModelScope.launch {
                        _resetPassword.emit(Resource.Error(it.message.toString()))
                    }
                }
        }
    }
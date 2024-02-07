package com.example.sem6project.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sem6project.data.User
import com.example.sem6project.util.*
import com.example.sem6project.util.Constants.USER_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewmodel @Inject constructor(

    private val firebaseauth: FirebaseAuth,
    private val db: FirebaseFirestore
):ViewModel()
{
//Mutablestateflow is a part of the Kotlin Flow library, which is a framework for building asynchronous and stream-based data pipelines

    private val _register= MutableStateFlow<Resource<User>>(Resource.Unspecified())
     val register:Flow<Resource<User>> = _register

// channel does not take any initial value it is the main difference between the MutableStateFlow and channel

    private val _validation= Channel<RegisterFieldsState>()
    val validation = _validation.receiveAsFlow()


    fun createAccountWithEmailAndPassword(user:User,password:String){
        if (checkValidation(user, password)){
            runBlocking {
                _register.emit(Resource.Loading())
            }
            firebaseauth.createUserWithEmailAndPassword(user.email,password).
            addOnSuccessListener {
                it.user?.let {
                    saveUserInfo(it.uid,user)
                }

            }.addOnFailureListener {
                _register.value=Resource.Error(it.message.toString())
            }
        }
        else{
            val registerfieldsState=RegisterFieldsState(
                ValidateEmail(user.email),ValidatePassword(password)
            )
            runBlocking {
                _validation.send(registerfieldsState)
            }
        }
    }

    private fun saveUserInfo(userUid:String,user:User) {
        db.collection(USER_COLLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                _register.value=Resource.Success(user)
            }.addOnFailureListener {
                _register.value=Resource.Error(it.message.toString())
            }
    }

    private fun checkValidation(user: User, password: String): Boolean {
        val emailValidation = ValidateEmail(user.email)
        val passwordValidation = ValidatePassword(password)
        val shouldRegister =
            emailValidation is RegisterValidation.Success && passwordValidation is RegisterValidation.Success
        return shouldRegister
    }
}
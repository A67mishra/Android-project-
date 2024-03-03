package com.example.sem6project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sem6project.data.Product
import com.example.sem6project.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) :ViewModel() {

    private val _specialProduct= MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val specialProduct:StateFlow<Resource<List<Product>>> =_specialProduct

    //init blocks are used to initialize the properties of a class.They are executed when an instance of the class is created
    init {
        fetchSpecialProduct()
    }


    fun fetchSpecialProduct(){
        viewModelScope.launch {
            _specialProduct.emit(Resource.Loading())
        }

        firestore.collection("Products").whereEqualTo("category","Special Product").get().addOnSuccessListener {result->
            val specialProductList=result.toObjects(Product::class.java)
            viewModelScope.launch {
                _specialProduct.emit(Resource.Success(specialProductList))
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                _specialProduct.emit(Resource.Error(it.message.toString()))
            }
        }
    }
}
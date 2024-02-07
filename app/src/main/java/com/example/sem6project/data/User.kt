package com.example.sem6project.data

data class User(
    var firstname:String,
    var lastname:String,
    var email:String,
    var imagepath:String=""
){
//    to deal with firebase we need an empty constructor.

    constructor(): this("","","","")
}

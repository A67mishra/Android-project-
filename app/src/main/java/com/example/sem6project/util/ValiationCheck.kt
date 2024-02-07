package com.example.sem6project.util

import android.util.Patterns

//To increase the coe readability and we donot have to write the same coe again for the login page we are creating a separate file for validation.
fun ValidateEmail(email:String): RegisterValidation {
    if (email.isEmpty()){
        return RegisterValidation.Failed("Email cannot be empty")
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Wrong email format")
    return RegisterValidation.Success
}

fun ValidatePassword(password:String):RegisterValidation{
    if (password.isEmpty()){
        return RegisterValidation.Failed("Password cannot be empty")
    }
    if (password.length < 8)
        return RegisterValidation.Failed("Password should contain at least 8 characters")
    return RegisterValidation.Success
}
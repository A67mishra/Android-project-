package com.example.sem6project.dialog

import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.sem6project.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setupBottomDialog(
    onSendClick: (String) -> Unit
){
    val dialog=BottomSheetDialog(requireContext())
    val view=layoutInflater.inflate(R.layout.resetpswd_dialogbox,null)
    dialog.setContentView(view)
    dialog.behavior.state=BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val edemail=view.findViewById<EditText>(R.id.resetpasswordedittext)
    val btnsend=view.findViewById<Button>(R.id.btnSendpasswordreset)
    val btncancel=view.findViewById<Button>(R.id.btnCancelpasswordreset)

    btnsend.setOnClickListener{
        val email=edemail.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()
    }
    btncancel.setOnClickListener{
        dialog.dismiss()
    }

}
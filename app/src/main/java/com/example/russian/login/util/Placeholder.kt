package com.example.remotelogin.util

import androidx.compose.runtime.MutableState
import com.example.remotelogin.wrappers.placeholder.AuthProcessPlaceholder

fun changePlaceholder(processPlaceholder: MutableState<AuthProcessPlaceholder>){

    processPlaceholder.value.let {
        when(it){
            is AuthProcessPlaceholder.Error -> processPlaceholder.value = AuthProcessPlaceholder.None
            else -> {}
        }
    }

}
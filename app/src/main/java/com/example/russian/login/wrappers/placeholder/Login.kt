package com.example.remotelogin.wrappers.placeholder

sealed class AuthProcessPlaceholder{
    data object None: AuthProcessPlaceholder()
    data object Loading: AuthProcessPlaceholder()
    data class Error(val error: String): AuthProcessPlaceholder()
}
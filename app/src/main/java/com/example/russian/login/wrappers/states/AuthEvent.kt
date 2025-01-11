package com.example.remotelogin.wrappers.states

sealed class AuthEvent {

    data object Shimmer: AuthEvent()

    data object LoginLoading: AuthEvent()
    data object CreateLoading: AuthEvent()

    data object LoginSuccess: AuthEvent()
    data object CreateSuccess: AuthEvent()

    data class LoginDeny(val error: String): AuthEvent()
    data class CreateDeny(val error: String): AuthEvent()

}
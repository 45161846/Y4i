package com.example.remotelogin.wrappers.states

import kotlinx.serialization.Serializable

sealed class AuthDestination {
    @Serializable
    data object Loading : AuthDestination()

    @Serializable
    data object Login : AuthDestination()

    @Serializable
    data object NewAccount : AuthDestination()

    @Serializable
    data object ForgetPassword : AuthDestination()
}
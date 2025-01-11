package com.example.remotelogin.wrappers

sealed class FieldDescription{

    data object NoDescription: FieldDescription()
    data object Email: FieldDescription()
    data object Login: FieldDescription()
    data object Password: FieldDescription()

}
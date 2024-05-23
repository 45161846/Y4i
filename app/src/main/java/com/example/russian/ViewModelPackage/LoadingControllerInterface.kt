package com.example.russian.ViewModelPackage

interface LoadingControllerInterface {

    fun start(){
        onStart()
        execute()
        onComplete()
    }

    fun onStart()
    fun execute()
    fun onComplete()

}
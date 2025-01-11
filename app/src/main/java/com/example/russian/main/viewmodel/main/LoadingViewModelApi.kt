package com.example.russian.main.viewmodel.main

import com.example.russian.main.application.MyApplication

interface LoadingViewModelApi {

    fun startLoadingIfNeeded(application: MyApplication)

}
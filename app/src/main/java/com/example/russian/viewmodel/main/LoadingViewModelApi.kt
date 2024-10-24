package com.example.russian.viewmodel.main

import com.example.russian.application.MyApplication

interface LoadingViewModelApi {

    fun startLoadingIfNeeded(application: MyApplication)

}
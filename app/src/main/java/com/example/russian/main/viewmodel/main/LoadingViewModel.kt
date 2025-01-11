package com.example.russian.main.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russian.main.back.initialloading.impl.InitialLoadingExecutor
import com.example.russian.main.application.MyApplication
import kotlinx.coroutines.launch

class LoadingViewModel: LoadingViewModelApi, ViewModel() {

    override fun startLoadingIfNeeded(
        application: MyApplication
    ) {
        viewModelScope.launch {
            InitialLoadingExecutor(
                application.loadingDao(),
                application.assets,
            ).execute()
        }
    }

}
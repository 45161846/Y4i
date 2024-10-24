package com.example.russian.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russian.application.MyApplication
import com.example.russian.back.initialloading.impl.InitialLoadingExecutor
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
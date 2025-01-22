package com.example.russian.game.back

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russian.game.back.data.dao.LoadingDao
import com.example.russian.game.back.initialloading.impl.InitialLoadingExecutor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val dao: LoadingDao,
    private val assets: AssetManager
): ViewModel() {

    fun loading() {
        viewModelScope.launch {
            InitialLoadingExecutor(
                dao, assets
            ).execute()
        }
    }

}
package com.example.russian.architectured.prac.remote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PracRemoteScreen(

){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(6, key = {it}){
            RemotePlaylistPreview()
        }
    }
}
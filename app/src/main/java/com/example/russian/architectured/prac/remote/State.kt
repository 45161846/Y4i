package com.example.russian.architectured.prac.remote

import androidx.compose.ui.graphics.Color


data class RemotePlaylistUiState(
    val title: String,
    val description: String,
    val backColor: Color,
    val rating: Float,
    val taskCount: Int,
)
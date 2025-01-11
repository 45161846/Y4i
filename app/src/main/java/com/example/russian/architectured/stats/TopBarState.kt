package com.example.russian.architectured.stats

sealed class TopBarState{

    data object Hide: TopBarState()

    data class Show(
        val search: (String) -> Unit,
        val navigateToFilter: () -> Unit
    ): TopBarState()

}
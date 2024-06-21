package com.example.russian.mainScreenPackage.screenDrawers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import com.example.russian.mainScreenPackage.WordsLocalMainScreenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DefaultScaffold(
    selectedItemIndex: Int,
    displayableUI: @Composable (padding: PaddingValues) -> Unit,
    changeSelectedItemIndex: (newIndex: Int) -> Unit
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            displayableUI(it)
        },
        bottomBar = {
            DrawNavigationBarBottom(
                selectedItemIndex,
                changeSelectedItemIndex
            )
        }
    )
}


@Composable
fun StatsScaffold(
    selectedItemIndex: Int,
    scope: CoroutineScope,
    repo: WordsLocalMainScreenRepository,
    owner: LifecycleOwner,
    onSearch: (pref: String) -> Unit,
    onClear: () -> Unit,
    changeSelectedItemIndex: (newIndex: Int) -> Unit,
){

    val listState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DrawTopBar(
                selectedItemIndex = selectedItemIndex,
                onSearch = { onSearch(it) },
                onClear = { onClear() }
            )
        },
        content = {

            DrawStatsContent(paddingValues = it, listState = listState, repo = repo, owner = owner)

        },
        bottomBar = {
            DrawNavigationBarBottom(
                selectedItemIndex,
                changeSelectedItemIndex
            )
        },
        floatingActionButton = {
            DrawToTopButton(
                listState = listState,
                selectedItemIndex = selectedItemIndex,
                onClick = {
                    scope.launch {
                        listState.scrollToItem(0)
                    }
                }
            )
        }
    )
}
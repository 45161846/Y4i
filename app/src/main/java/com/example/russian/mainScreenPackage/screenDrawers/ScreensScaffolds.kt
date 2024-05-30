package com.example.russian.mainScreenPackage.screenDrawers

import android.content.Context
import android.view.Window
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.russian.database.Word
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
    window: Window,
    context: Context,
    scope: CoroutineScope,
    contentList: List<Word>,
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
                window = window,
                context = context,
                onSearch = { onSearch(it) },
                onClear = { onClear() }
            )
        },
        content = {
            DrawStatsContent(listOfWords = contentList, paddingValues = it, listState = listState)
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
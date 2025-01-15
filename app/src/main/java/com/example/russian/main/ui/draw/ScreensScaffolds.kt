package com.example.russian.main.ui.draw

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.russian.architectured.MainNavDestinations
import com.example.russian.architectured.MainNavigationActions
import com.example.russian.architectured.NavigationBarBottom
import com.example.russian.architectured.stats.DrawLoading
import com.example.russian.architectured.stats.DrawNoWordsFound
import com.example.russian.architectured.stats.DrawStatContent
import com.example.russian.main.ui.state.StatsFirstScreenState
import kotlinx.coroutines.launch

@Composable
fun DefaultScaffold(
    selectedItemIndex: MainNavDestinations,
    topBar: @Composable () -> Unit,
    displayableUI: @Composable (padding: PaddingValues) -> Unit,
    changeSelectedItemIndex: (newIndex: MainNavDestinations) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            displayableUI(it)
        },
        bottomBar = {

        },
        topBar ={

        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StatsScaffold(
    listStats: State<StatsFirstScreenState>,
    changeSelectedItemIndex: (newIndex: MainNavDestinations) -> Unit,
    onFilter: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val listStats = listStats.value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DrawTopBar(
                onSearch = listStats.onSearch,
                onFilterClick = onFilter
            )
        },
        content = { _ ->

            when (listStats) {
                is StatsFirstScreenState.Success -> {
//                    DrawStatContent(
//                        listStats,
//                        paddingValues,
//                        listState
//                    )
                }

                is StatsFirstScreenState.Loading -> DrawLoading()
                is StatsFirstScreenState.NothingFound -> DrawNoWordsFound()
            }
        },
        bottomBar = {

        },
        floatingActionButton = {

            if (listStats is StatsFirstScreenState.Success) {

                DrawToTopButton(
                    listState = listState,
                    onClick = {
                        scope.launch {
                            listState.scrollToItem(0)
                        }
                    }
                )
            }
        }
    )
}
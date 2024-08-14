package com.example.russian.architecture2.ui.draw

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.russian.MyEnumClasses.ScreenFilters
import com.example.russian.architecture2.ui.draw.stats.DrawLoading
import com.example.russian.architecture2.ui.draw.stats.DrawNoWordsFound
import com.example.russian.architecture2.ui.draw.stats.DrawStatContent
import com.example.russian.architecture2.ui.state.StatsScreenState
import kotlinx.coroutines.launch

@Composable
fun DefaultScaffold(
    selectedItemIndex: Int,
    displayableUI: @Composable (padding: PaddingValues) -> Unit,
    changeSelectedItemIndex: (newIndex: Int) -> Unit
) {
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
    listStats: State<StatsScreenState>,
    navController: NavController,
    changeSelectedItemIndex: (newIndex: Int) -> Unit
) {

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val listStats = listStats.value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DrawTopBar(
                onSearch = {
                    if (listStats is StatsScreenState.Success) listStats.onSearch(it)
                },
                onFilterClick = {
                    if (listStats is StatsScreenState.Success) navController.navigate(ScreenFilters)
                }
            )
        },
        content = { paddingValues ->

            when (listStats) {
                is StatsScreenState.Success -> DrawStatContent(
                    listStats,
                    paddingValues,
                    listState
                )

                is StatsScreenState.Loading -> DrawLoading(paddingValues)
                is StatsScreenState.NothingFound -> DrawNoWordsFound(paddingValues)
            }
        },
        bottomBar = {
            DrawNavigationBarBottom(
                selectedItemIndex = 2,
                changeSelectedItemIndex
            )
        },
        floatingActionButton = {

            if (listStats is StatsScreenState.Success) {

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

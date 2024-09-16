package com.example.russian.ui.draw

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.russian.enums.ScreenFilters
import com.example.russian.ui.draw.practice.DrawPracticeContent
import com.example.russian.ui.draw.stats.DrawLoading
import com.example.russian.ui.draw.stats.DrawNoWordsFound
import com.example.russian.ui.draw.stats.DrawStatContent
import com.example.russian.ui.state.StatsFirstScreenState
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
    listStats: State<StatsFirstScreenState>,
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
                onSearch = listStats.onSearch,
                onFilterClick = {
                    if (listStats is StatsFirstScreenState.Success) navController.navigate(ScreenFilters)
                }
            )
        },
        content = { paddingValues ->

            when (listStats) {
                is StatsFirstScreenState.Success -> DrawStatContent(
                    listStats,
                    paddingValues,
                    listState
                )

                is StatsFirstScreenState.Loading -> DrawLoading(paddingValues)
                is StatsFirstScreenState.NothingFound -> DrawNoWordsFound(paddingValues)
            }
        },
        bottomBar = {
            DrawNavigationBarBottom(
                selectedItemIndex = 2,
                changeSelectedItemIndex
            )
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


@Preview
@Composable
private fun Preview(){
    DefaultScaffold(1, displayableUI = {
        DrawPracticeContent(it) { }
    }) {

    }
}
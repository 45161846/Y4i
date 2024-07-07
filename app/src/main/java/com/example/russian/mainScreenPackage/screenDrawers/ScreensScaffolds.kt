package com.example.russian.mainScreenPackage.screenDrawers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.russian.MyEnumClasses.ScreenFilters
import com.example.russian.architecture.data.entity.Word
import com.example.russian.mainScreenPackage.repository.WordsLocalMainScreenRepository
import com.example.russian.mainScreenPackage.repository.WordsLocalTestRepository
import com.example.russian.mainScreenPackage.screenDrawers.stats.DrawContentNoRepo
import com.example.russian.mainScreenPackage.screenDrawers.stats.DrawStatsContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
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
    navController: NavController,
    scope: CoroutineScope?,
    repo: WordsLocalMainScreenRepository,
    owner: LifecycleOwner?,
    onSearch: (pref: String) -> Unit,
    changeSelectedItemIndex: (newIndex: Int) -> Unit,
){

    val listState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DrawTopBar(
                selectedItemIndex = selectedItemIndex,
                onSearch = { onSearch(it) },
                onFilterClick = {
                    navController.navigate(ScreenFilters)
                }
            )
        },
        content = {

            DrawStatsContent(paddingValues = it, listState = listState, repo = repo,
                owner = owner
            )

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
                    scope?.launch {
                        listState.scrollToItem(0)
                    }
                }
            )
        }
    )
}

@Composable
fun StatsScaffoldNoRepo(
    contentListFlow: StateFlow<List<Word>>,
    navController: NavController,
    scope: CoroutineScope?,
    onSearch: (pref: String) -> Unit,
    changeSelectedItemIndex: (newIndex: Int) -> Unit,
){
    val listState = rememberLazyListState()
    val contentListState = contentListFlow.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DrawTopBar(
                selectedItemIndex = 2,
                onSearch = { onSearch(it) },
                onFilterClick = {
                    navController.navigate(ScreenFilters)
                }
            )
        },
        content = {paddingValues ->
                  DrawContentNoRepo(
                      contentListState = contentListState,
                      paddingValues = paddingValues,
                      listState = listState
                  )
        },
        bottomBar = {
            DrawNavigationBarBottom(
                selectedItemIndex = 2,
                changeSelectedItemIndex
            )
        },
        floatingActionButton = {
            DrawToTopButton(
                listState = listState,
                selectedItemIndex = 2,
                onClick = {
                    scope?.launch {
                        listState.scrollToItem(0)
                    }
                }
            )
        }
    )
}

@Preview
@Composable
private fun ScreensPreview(){
    StatsScaffold(
        selectedItemIndex = 2,
        navController = rememberNavController(),
        scope = null,
        repo = WordsLocalTestRepository(),
        owner = null,
        onSearch = {}
    ) {

    }
}


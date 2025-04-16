package com.example.russian.main.wrappers

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.isScrolledToTheEnd(
    loadedAmount: Int
) = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) >= loadedAmount - 1
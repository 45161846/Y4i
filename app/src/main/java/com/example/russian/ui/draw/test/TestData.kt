package com.example.russian.ui.draw.test

import com.example.russian.back.data.entity.NewWord
import com.example.russian.back.data.entity.Statistics
import com.example.russian.back.data.entity.WordStatistics
import com.example.russian.enums.SortTypeMode
import com.example.russian.ui.draw.stats.comp.PlaylistState
import com.example.russian.ui.draw.stats.comp.PlaylistViewState
import com.example.russian.ui.draw.stats.comp.SortFilterState
import com.example.russian.ui.draw.stats.comp.SortFilterViewState
import com.example.russian.ui.draw.stats.comp.UnansweredViewState
import com.example.russian.ui.draw.stats.screen.FilterScreenActions

fun testDataWordsList(): List<WordStatistics> {

    val words = listOf(
        NewWord(1L, "word 1", 2),
        NewWord(2L, "word 2", 1),
        NewWord(3L, "word 3", 0),
        NewWord(4L, "word 4", 1),
        NewWord(5L, "word 5", 1),
        NewWord(6L, "word 6", 0),
        NewWord(7L, "word 7", 2),
        NewWord(8L, "word 8", 0),
        NewWord(9L, "word 9", 2),
        NewWord(10L, "word 10", 0),
        NewWord(11L, "word 11", 1),
        NewWord(12L, "word 12", 0),
        NewWord(13L, "word 13", 2),
        NewWord(14L, "word 14", 0),
        NewWord(15L, "word 15", 1),
        NewWord(16L, "word 16", 0),
        NewWord(15L, "word 15", 1),
        NewWord(16L, "word 16", 0),
    )

    val stats = listOf(
        Statistics(1L, 1L, 15, 7, "word 1"),
        Statistics(2L, 2L, 3, 3, "word 2"),
        Statistics(3L, 3L, 0, 0, "word 3"),
        Statistics(4L, 4L, 1, 0, "word 4"),
        Statistics(5L, 5L, 8, 7, "word 5"),
        Statistics(6L, 6L, 100, 7, "word 6"),
        Statistics(7L, 7L, 100, 0, "word 7"),
        Statistics(8L, 8L, 13, 7, "word 8"),
        Statistics(9L, 9L, 15, 7, "word 9"),
        Statistics(10L, 10L, 15, 7, "word 10"),
        Statistics(11L, 11L, 15, 3, "word 11"),
        Statistics(12L, 12L, 22, 7, "word 12"),
        Statistics(13L, 13L, 3, 3, "word 9"),
        Statistics(14L, 14L, 15, 7, "word 10"),
        Statistics(15L, 15L, 120, 7, "word 11"),
        Statistics(16L, 16L, 15, 2, "word 12"),
        Statistics(15L, 15L, 120, 7, "word 11"),
        Statistics(16L, 16L, 15, 2, "word 12"),
    )

    return List(words.size) {
        WordStatistics(words[it], stats[it])
    }
}


fun testPlaylistState() = PlaylistViewState(
    listOf(
        PlaylistState("title 1", true),
        PlaylistState("title 2", true),
        PlaylistState("title 3", false)
    )
)

fun testShowUnansweredState() = UnansweredViewState(true)

fun testSortState() = SortFilterViewState(
    listOf(
        SortFilterState("sort type 1", SortTypeMode.DIRECT, false),
        SortFilterState("sort type 2", SortTypeMode.UNSPECIFIED, false),
        SortFilterState("sort type last", SortTypeMode.UNSPECIFIED, true)
    )
)

fun testActions() = FilterScreenActions(
    {}, {}, {}, {}, {}
)
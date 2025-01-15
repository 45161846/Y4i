package com.example.russian.architectured.stats.comp.filter


import androidx.compose.runtime.Immutable
import com.example.russian.main.back.data.entity.Statistics
import com.example.russian.main.ui.state.MarkedPlaylist


data class FilterState(
    val markedPlaylists: List<MarkedPlaylist>,
    val sortType: SortType,
    val showUnanswered: Boolean,
    val bounds: PercentageBounds
)

@Immutable
class PercentageBounds(
    bottom: Int,
    top: Int = 100
){
    val minValue by lazy {
        bottom.coerceAtLeast(0)
    }

    val maxValue by lazy {
        top.coerceAtMost(100)
    }
}
fun Statistics.match(bounds: PercentageBounds): Boolean{
    val wr = (this.correct) / (this.attempts) * 100
    return bounds.minValue <= wr && wr <= bounds.maxValue
}


@Immutable
sealed class SortType(open val direction: SortDirection){

    @Immutable
    data class BY_WIN_RATE(override val direction: SortDirection): SortType(direction)

    @Immutable
    data class ALPHABETICAL(override val direction: SortDirection): SortType(direction)
}
fun SortDirection.reverse(): SortDirection {
    return when(this){
        SortDirection.UP -> SortDirection.DOWN
        SortDirection.DOWN -> SortDirection.UP
        SortDirection.UNSPECIFIED -> SortDirection.UP
    }
}

enum class SortDirection{
    UP, DOWN, UNSPECIFIED;
}


data class FilterScreenActions(
    val onResetClick: () -> Unit,
    val onPlaylistClick: (MarkedPlaylist) -> Unit,
    val onSortTypeClick: (SortType) -> Unit,
    val onShowUnansweredClick: () -> Unit,
    val onSaveClick: () -> Unit
)
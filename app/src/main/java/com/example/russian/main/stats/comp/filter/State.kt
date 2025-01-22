package com.example.russian.main.stats.comp.filter


import android.util.Range
import androidx.compose.runtime.Immutable
import com.example.russian.game.back.data.entity.Statistics
import com.example.russian.game.back.data.entity.playlist.Playlist
import kotlin.math.min

data class MarkedPlaylist(
    val playlist: Playlist,
    var marked: Boolean
)

data class FilterState(
    val markedPlaylists: MarkedPlaylistVariant,
    val sortType: SortType,
    val showUnanswered: Boolean,
    val bounds: PercentageBounds
){
    companion object{
        val default = FilterState(
            markedPlaylists = MarkedPlaylistVariant.All,
            sortType = SortType.ALPHABETICAL(SortDirection.UP),
            showUnanswered = true,
            bounds = PercentageBounds(0, 100)
        )
    }
}

sealed class MarkedPlaylistVariant{
    data object All: MarkedPlaylistVariant()

    data class Partial(
        val markedPlaylists: List<MarkedPlaylist>,
    ) : MarkedPlaylistVariant()
}

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

    fun toFloatRange(): ClosedFloatingPointRange<Float> {
        return minValue.toFloat()..maxValue.toFloat()
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
        SortDirection.UNSPECIFIED -> SortDirection.DOWN
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
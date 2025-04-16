package com.example.russian.main.prac.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.russian.main.custom.FullScreenColumn
import com.example.russian.main.stats.CardOfStats
import com.valentinilk.shimmer.shimmer

@Composable
fun LocalDetailsContent(
    state: PlaylistContent.Local,
    listState: LazyListState,
    modifier: Modifier
) {


    val cardModifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(8.dp, 3.dp, 8.dp, 3.dp)
        .background(
            MaterialTheme.colorScheme.surfaceVariant,
            RoundedCornerShape(5.dp)
        )

    val shimmerModifier = cardModifier
        .shimmer()

    val lineHeightDp: Dp = with(LocalDensity.current) {
        MaterialTheme.typography.bodyMedium.fontSize.toDp()
    }

    val shimmerColor = MaterialTheme.colorScheme.onSurface.copy(
        alpha = 0.38F
    )

    val simmerShape = RoundedCornerShape(30)

    FullScreenColumn (
        state = listState,
        modifier = modifier
    ){

        when (state) {
            is PlaylistContent.Local.Loading -> {
                items(30, { it }) {

                    ShimmerStats(
                        state.displaySetting,
                        lineHeightDp,
                        shimmerColor,
                        simmerShape,
                        shimmerModifier
                    )
                }
            }

            is PlaylistContent.Local.Content -> {

                items(state.cards.size, { it }) {
                    CardOfStats(
                        state = state.cards[it],
                        modifier = cardModifier,
                        onLongClick = {
                            //TODO add to favorite
                        }
                    )
                }
            }
        }
    }
}
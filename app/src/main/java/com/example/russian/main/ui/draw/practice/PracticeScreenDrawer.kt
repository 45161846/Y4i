package com.example.russian.main.ui.draw.practice

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.architectured.Id
import com.example.russian.main.back.data.entity.playlist.Playlist
import com.example.russian.main.ui.state.PracScreenStage
import com.example.russian.main.ui.theme.family
import org.burnoutcrew.reorderable.ItemPosition
import org.burnoutcrew.reorderable.NoDragCancelledAnimation
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyGridState
import org.burnoutcrew.reorderable.reorderable
import kotlin.math.abs

@Composable
fun DrawPracticeContent(
    state: PracScreenStage,
    onClick: (Id) -> Unit,
    onMove: (ItemPosition, ItemPosition) -> Unit
) {

    when (state) {
        is PracScreenStage.Local.Loading -> return
        is PracScreenStage.Local.Data -> {

            VerticalReorderList(
                state, modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                onPlaylistClick = onClick,
                onMove = onMove
            )
        }

        else -> throw RuntimeException("Unknown screen type")
    }
}

@Composable
fun VerticalReorderList(
    playlistData: PracScreenStage.Local.Data,
    onMove: (ItemPosition, ItemPosition) -> Unit,
    onPlaylistClick: (Id) -> Unit,
    modifier: Modifier = Modifier,
) {

    val state =
        rememberReorderableLazyGridState(
            dragCancelledAnimation = NoDragCancelledAnimation(),
            onMove = onMove
        )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = state.gridState,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .reorderable(state)
    ) {
        items(items = playlistData.playlists, key = { item ->
            item.id.value
        }) { item ->

            ReorderableItem(state, item.id.value) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 32.dp else 16.dp, label = "")
                PlaylistCard(
                    Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .detectReorderAfterLongPress(state)
                        .aspectRatio(1f)
                        .shadow(elevation.value)
                    ,
                    item,
                    onClick = onPlaylistClick
                )

            }
        }
    }
}


@Composable
fun PlaylistCard(
    modifier: Modifier,
    playlist: Playlist,
    onClick: (Id) -> Unit
) {
    val cardColor = cardColor(playlist.title)
    val subTextColor = Color.Black
    val reduction = 2.5f
    val mainTextColor = Color(
        cardColor.red / reduction,
        cardColor.green / reduction,
        cardColor.blue / reduction
    )

    Card(
        modifier = modifier
        ,
        onClick = {
            onClick(playlist.id)
        },
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp), horizontalArrangement = Arrangement.Center
            ) {
                val taskColor = Color(
                    red = mainTextColor.red,
                    green = mainTextColor.green,
                    blue = mainTextColor.blue,
                    alpha = mainTextColor.alpha / reduction
                )

                Text(
                    text = "${playlist.capacity} заданий",
                    color = taskColor,
                    fontSize = 12.sp
                )
            }


            Text(
                playlist.shortName(),
                fontFamily = family,
                fontSize = 72.sp,
                color = mainTextColor
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 4.dp),
                text = playlist.title,
                fontFamily = family,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = subTextColor
            )
        }
    }
}

data class PracScreenActions(
    val onPlaylistMove: (ItemPosition, ItemPosition) -> Unit,
)

private fun cardColor(text: String): Color {
    var code = abs(text.hashCode())
    val colorfulness = 97
    val red = 255 - code % colorfulness; code /= colorfulness
    val blue = 255 - code % colorfulness; code /= colorfulness
    val green = 255 - code % colorfulness
    return Color(red, green, blue)
}

@Preview
@Composable
private fun PracticePreview() {
    PlaylistCard(
        Modifier
            .size(180.dp), Playlist(title = "Не с частями речи", capacity = 256)
    ) {}
}
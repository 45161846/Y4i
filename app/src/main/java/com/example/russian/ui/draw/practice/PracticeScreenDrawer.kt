package com.example.russian.ui.draw.practice

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.russian.back.data.entity.playlist.Playlist
import com.example.russian.ui.state.PracScreenStage
import com.example.russian.ui.theme.GameButtonThirdColor
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.family
import com.example.russian.ui.theme.onSecondaryTransparentBright
import com.example.russian.ui.theme.onSecondaryTransparentDark
import org.burnoutcrew.reorderable.ItemPosition
import org.burnoutcrew.reorderable.NoDragCancelledAnimation
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyGridState
import org.burnoutcrew.reorderable.reorderable
import java.util.Collections.min
import kotlin.math.abs

@Composable
fun DrawPracticeContent(
    paddingValues: PaddingValues,
    state: PracScreenStage,
    pracActions: PracScreenActions
) {

    when (state) {
        is PracScreenStage.Loading -> return
        is PracScreenStage.PracScreenState -> {
            val actions by remember(1) {
                mutableStateOf(pracActions)
            }

            VerticalReorderList(
                state, actions, Modifier
                    .fillMaxSize()
                    .background(PrimaryBackground)
            )
        }
    }
}

@Composable
fun VerticalReorderList(
    playlistData: PracScreenStage.PracScreenState,
    playlistListActions: PracScreenActions,
    modifier: Modifier = Modifier,
) {
    val actions = remember {
        playlistListActions
    }

    val state =
        rememberReorderableLazyGridState(
            dragCancelledAnimation = NoDragCancelledAnimation(),
            onMove = actions.onPlaylistMove
        )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = state.gridState,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryBackground)
            .reorderable(state)
    ) {
        items(items = playlistData.playlists, key = { item ->
            item.id
        }) { item ->

            ReorderableItem(state, item.id) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 8.dp else 0.dp)
                PlaylistCard(
                    Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .detectReorderAfterLongPress(state)
                        .shadow(elevation.value)
                        .aspectRatio(1f)

                    ,
                    item,
                    onClick = actions.onPlaylistClick ?: {}
                )

            }
        }
    }
}


@Composable
fun PlaylistCard(
    modifier: Modifier,
    playlist: Playlist,
    onClick: (Long) -> Unit
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
        modifier = modifier,
        onClick = { onClick.invoke(playlist.id) },
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(Modifier.weight(1F))

            Text(
                playlist.shortName(),
                fontFamily = family,
                fontSize = 72.sp,
                color = mainTextColor
            )
            Text(
                modifier = Modifier.weight(1F),
                text =  playlist.title,
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
    var onPlaylistClick: ((Long) -> Unit)? = null,
    val onPlaylistMove: (ItemPosition, ItemPosition) -> Unit
)

private fun cardColor(text: String): Color{
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
            .size(180.dp)
        , Playlist(title = "Не с частями речи", capacity = 256, )
    ) {}
}
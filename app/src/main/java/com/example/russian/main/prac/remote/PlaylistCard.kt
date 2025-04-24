package com.example.russian.main.prac.remote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.main.custom.CustomOverflowText
import com.example.russian.main.custom.StarRating
import com.example.russian.main.data.remote.RemotePlaylist
import com.example.russian.main.theme.AppThemes
import com.example.russian.main.theme.RussianTheme

@Composable
fun RemotePlaylistCard(
    state: RemotePlaylist,
    onClick: () -> Unit
) {

    val textColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
        alpha = 0.83f
    )

    Card(
        modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(30)),
        onClick = onClick
    ) {
        Text(
            text = state.title,
            style = MaterialTheme.typography.headlineLarge.copy(
                color = textColor
            ),
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,

            )

        CustomOverflowText(
            text = state.description,
            style = MaterialTheme.typography.titleMedium.copy(
                color = textColor
            ),
            modifier = Modifier
                .padding(8.dp),
            overflowSuffix = "... Читать далее",
            maxLines = 2
        )

        for (task in state.previewTasks
            .slice(0 until state.previewTasks.size.coerceAtMost(3))
        ) {
            Text(
                task,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = textColor.copy(alpha = 0.63f)
                ),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp)
//                    .weight(1F)
                ,
                text = "${state.capacity} Заданий",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = textColor.copy(alpha = 0.6f)
                )
            )

            StarRating(
                rating = state.rating,
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
//                    .weight(1F)
                ,
                starModifier = Modifier.size(28.dp),
                color = textColor.copy(alpha = 0.83f)
            )
        }

    }
}

@Preview(
    showBackground = true
)
@Composable
fun RemotePlaylistPreview() {
    RussianTheme(AppThemes.DefaultLight) {
        PracRemoteScreen(testRemotePlaylistState(), {}, {}, {})
    }
}

@Preview(
    showBackground = true
)
@Composable
fun RemotePlaylistPreviewDark() {
    RussianTheme(AppThemes.DefaultDark) {
        PracRemoteScreen(testRemotePlaylistState(), {}, {}, {})
    }
}
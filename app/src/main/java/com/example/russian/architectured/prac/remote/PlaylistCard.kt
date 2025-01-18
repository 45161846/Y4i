package com.example.russian.architectured.prac.remote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.example.russian.architectured.custom.CustomOverflowText
import com.example.russian.architectured.custom.StarRating
import com.example.russian.main.ui.theme.RussianTheme

@Composable
fun RemotePlaylistCard(
    state: RemotePlaylistUiState
){
    Card(
        modifier = Modifier
            .padding(8.dp)
            .background(state.backColor, RoundedCornerShape(30))
        ,
        onClick = {

        }
    ){
        Text(
            text = state.title,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(horizontal = 8.dp)
            , maxLines = 2,
            overflow = TextOverflow.Ellipsis,

        )


        CustomOverflowText(
            text = state.description,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(8.dp),
            overflowSuffix = "... Читать далее",
            maxLines = 2
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp)
//                    .weight(1F)
                ,
                text = "${state.taskCount} Заданий",
                style = MaterialTheme.typography.labelMedium
            )

            StarRating(
                rating = state.rating,
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
//                    .weight(1F)
                ,
                starModifier = Modifier.size(28.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

    }
}

@Preview(
    showBackground = true
)
@Composable
fun RemotePlaylistPreview(){

    val state1 = RemotePlaylistUiState(
        title = "Топ-50 ошибок 2025/2024",
        description = "Скорее забирай набор главных ловушек ФИПИ. 90% НЕ СДАСТ егэ именно поэтому.",
        backColor = MaterialTheme.colorScheme.surfaceVariant,
        rating = 2.65F,
        taskCount = 49
    )

    val state2 = RemotePlaylistUiState(
        title = "Ни за что не угадаешь, что поджидает тебя в 28 задании",
        description = "У нас набор эксклюзивных сливов, всего за 27 тугриков ты можешь получить полное демо ФИПИ",
        backColor = MaterialTheme.colorScheme.surfaceVariant,
        rating = 1.2F,
        taskCount = 3
    )

    RussianTheme {
        Column {
            RemotePlaylistCard(state1)
            RemotePlaylistCard(state2)
        }

    }


}
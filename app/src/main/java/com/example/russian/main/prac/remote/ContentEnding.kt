package com.example.russian.main.prac.remote

import android.graphics.Paint.Align
import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.russian.main.custom.StarRating


@Composable
fun NothingMore(){

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        textAlign = TextAlign.Center,
        text = "Задания закончились"
    )

}

@Composable
fun ErrorEnding(
    message: String
){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        textAlign = TextAlign.Center,
        text = "Пу-пу-пу... $message"
    )
}

@Composable
fun RemotePlaylistShimmer(
    modifier: Modifier = Modifier
) {

    val alpha = 0.3f

    Card(
        modifier = modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(30))
    ){
        Text(
            text = "\n\n",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                .background(
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(
                    alpha = alpha
                ), RoundedCornerShape(4.dp)
                )
            , maxLines = 2,
            overflow = TextOverflow.Ellipsis,

            )


        Text(
            text = "\n\n",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                    alpha = 0.93F
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(
                    alpha = alpha
                ), RoundedCornerShape(4.dp)
                )
            ,
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
                    .width(56.dp)
                    .background(
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha =alpha
                    ), RoundedCornerShape(4.dp)
                    )
//                    .weight(1F)
                ,
                text = " ",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = alpha
                    )
                )
            )

            StarRating(
                rating = 5F,
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
//                    .weight(1F)
                ,
                starModifier = Modifier.size(28.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                    alpha = alpha
                )
            )
        }

    }

}
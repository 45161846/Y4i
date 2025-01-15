package com.example.russian.architectured.stats.comp.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.russian.R

@Composable
fun SortBy(
    initialSort: SortType,
    modifier: Modifier,
    onSortItemClick: (SortType) -> Unit
) {
    var sortType1 by remember {
        mutableStateOf(
            SortType.ALPHABETICAL(
                if (initialSort is SortType.ALPHABETICAL) initialSort.direction
                else SortDirection.UNSPECIFIED
            )
        )
    }
    var sortType2 by remember {
        mutableStateOf(
            SortType.BY_WIN_RATE(
                if (initialSort is SortType.BY_WIN_RATE) initialSort.direction
                else SortDirection.UNSPECIFIED
            )
        )
    }

    Column(modifier) {
        Card(
            sortType1,
            Modifier
                .fillMaxWidth()
        ) {
            sortType1 = sortType1.copy(direction = sortType1.direction.reverse())
            sortType2 = sortType2.copy(direction = SortDirection.UNSPECIFIED)
            onSortItemClick(sortType1)
        }
        Card(
            sortType2,
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp
                )
        ) {
            sortType2 = sortType2.copy(direction = sortType2.direction.reverse())
            sortType1 = sortType1.copy(direction = SortDirection.UNSPECIFIED)
            onSortItemClick(sortType2)
        }
    }
}

@Composable
private fun Card(state: SortType, modifier: Modifier, onSortItemClick: () -> Unit) {

    val image = ImageVector.vectorResource(R.drawable.arrow_right)
    val textColor = if (state.direction == SortDirection.UNSPECIFIED) MaterialTheme.colorScheme.onSurfaceVariant
    else MaterialTheme.colorScheme.onPrimary

    Button(
        onClick = onSortItemClick,
        colors = ButtonDefaults.buttonColors(
            containerColor =
            if (state.direction == SortDirection.UNSPECIFIED) MaterialTheme.colorScheme.surfaceVariant
            else MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(20),
        modifier = modifier
    ) {

        Text(
            text = state.label(),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = textColor,
            )
        )

        Icon(
            imageVector = image,
            contentDescription = null,
            modifier = Modifier
                .rotate(
                    when(state.direction){
                        SortDirection.UP -> -90F
                        SortDirection.DOWN -> 90F
                        SortDirection.UNSPECIFIED -> 0F
                    }
                )
            , tint = textColor
        )
    }
}

@Composable
private fun SortType.label(): String{
    return when(this){
        is SortType.ALPHABETICAL -> stringResource(R.string.filter_label_alphabet)
        is SortType.BY_WIN_RATE -> stringResource(R.string.filter_label_win_rate)
    }
}
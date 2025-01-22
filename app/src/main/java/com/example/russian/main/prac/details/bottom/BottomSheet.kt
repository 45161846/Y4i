package com.example.russian.main.prac.details.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.main.stats.comp.filter.BoolButton
import com.example.russian.main.stats.comp.filter.ParagraphView
import com.example.russian.main.stats.comp.filter.SortBy
import com.example.russian.main.theme.RussianTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailsBottomFilter(
    sheetState: SheetState,
    state: BottomFilterState,
    actions: BottomFilterActions,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        modifier = Modifier
            .wrapContentHeight(),
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = Color.Transparent,
        contentWindowInsets = { WindowInsets(0, 0, 0, 0) }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
                )
                .windowInsetsPadding(WindowInsets.navigationBars),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ParagraphView("Сортировать по") {
                val modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()

                SortBy(
                    state.sortType,
                    modifier,
                    actions.onSortTypeClick
                )
            }

            var sliderPosition by remember {
                mutableStateOf(state.bounds.toFloatRange())
            }
            RangeSlider(
                valueRange = 0f..100f,
                value = state.bounds.toFloatRange(),
                onValueChange = {range ->
                    sliderPosition = range
                },
                startThumb = {
                    Text(
                        text = sliderPosition.start.roundToInt().toString()
                    )
                },
                endThumb = {
                    Text(
                        text = sliderPosition.endInclusive.roundToInt().toString()
                    )
                }
            )

            Spacer(
                modifier = Modifier
                    .size(16.dp)
            )

            BoolButton(
                state.showUnanswered,
                onClick =  actions.onShowUnanswered
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewBottomFilter() {

    RussianTheme {
        PlaylistDetailsBottomFilter(
            rememberModalBottomSheetState(), BottomFilterState.test(), BottomFilterActions.test()) { }
    }

}
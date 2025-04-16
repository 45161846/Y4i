package com.example.russian.main.prac.details.bottom

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.main.stats.comp.filter.BoolButton
import com.example.russian.main.stats.comp.filter.ParagraphView
import com.example.russian.main.stats.comp.filter.PercentageBounds
import com.example.russian.main.stats.comp.filter.SortBy
import com.example.russian.main.theme.RussianTheme
import kotlinx.coroutines.launch
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
            .fillMaxWidth(),
//            .windowInsetsPadding(WindowInsets.navigationBars)
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        tonalElevation = 4.dp,

        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = {
            WindowInsets.navigationBars
                .add(WindowInsets(left = 8.dp, right = 8.dp))
        }
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
            onValueChange = { range ->
                actions.onBoundsChange(
                    PercentageBounds.from(range)
                )
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
            onClick = actions.onShowUnanswered,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewBottomFilter() {

    val state = rememberStandardBottomSheetState(

    )
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            state.show()
        }
    }
    RussianTheme {
        PlaylistDetailsBottomFilter(
            state,
            BottomFilterState.default(),
            BottomFilterActions.test()
        ) { }
    }
}
package com.example.russian.architecture2.ui.draw.stats.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.MyEnumClasses.SortTypeMode
import com.example.russian.R
import com.example.russian.architecture2.ui.draw.stats.MyOptionsText
import com.example.russian.architecture2.ui.draw.test.testSortState
import com.example.russian.ui.theme.SecondaryBackground
import com.example.russian.ui.theme.family

val shape = RoundedCornerShape(4.dp)

@Composable
fun SortFilterView(
    viewState: SortFilterViewState,
    modifier: Modifier,
    onSortItemClick: (Int) -> Unit
) {
    Column(modifier) {
        viewState.states.forEachIndexed { index, state ->
            Card(state) {
                onSortItemClick(index)
            }
        }
    }
}

@Composable
private fun Card(state: SortFilterState, onSortItemClick: () -> Unit) {

    val image = ImageVector.vectorResource(R.drawable.arrow_right)
    val color = if(state.mode == SortTypeMode.UNSPECIFIED) Color.Transparent else colorResource(R.color.third_answer)

    Button(
        onClick = onSortItemClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = if (state.isLast) 12.dp else 0.dp
            )
    ) {

        MyOptionsText(text = state.name)

        Icon(
            imageVector = image,
            contentDescription = null,
            modifier = Modifier
                .rotate(
                    if (state.mode == SortTypeMode.DIRECT) 90F else if (state.mode == SortTypeMode.REVERSED) -90F else 0F
                )
        )
    }
}

data class SortFilterViewState(
    val states: List<SortFilterState>
){
    override fun equals(other: Any?): Boolean {
        if(other !is SortFilterViewState) return false
        if(other.states.size != this.states.size) return false
        var res = true
        states.forEachIndexed{ind, state ->
            res = res && state == other.states[ind]
        }
        return res
    }
}

data class SortFilterState(
    val name: String,
    val mode: SortTypeMode,
    val isLast: Boolean
)

@Preview
@Composable
private fun Preview() {
    SortFilterView(
        testSortState(),
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(SecondaryBackground, shape)
    ) { }
}
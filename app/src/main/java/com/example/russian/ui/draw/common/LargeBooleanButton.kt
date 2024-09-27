package com.example.russian.ui.draw.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.russian.ui.draw.stats.comp.MyFilterOptionText
import com.example.russian.ui.draw.stats.comp.shape
import com.example.russian.ui.draw.test.testShowUnansweredState
import com.example.russian.ui.theme.FiltersScreenButtonActive
import com.example.russian.ui.theme.SecondaryBackground

@Composable
fun LargeBooleanButton(state: SimpleBooleanState, onClick: () -> Unit){
    Button(
        onClick = {
            onClick()
        },
        modifier = state.modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (state.show) FiltersScreenButtonActive else SecondaryBackground
        )
    ) {
        MyFilterOptionText(state.text)
    }
}

data class SimpleBooleanState(
    val show: Boolean,
    val text: String,
    val modifier: Modifier = Modifier
)

@Preview
@Composable
private fun Preview(){

    LargeBooleanButton(testShowUnansweredState()) { }
}
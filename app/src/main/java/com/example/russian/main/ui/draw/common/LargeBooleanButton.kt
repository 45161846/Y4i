package com.example.russian.main.ui.draw.common

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.russian.main.ui.draw.stats.comp.MyFilterOptionText
import com.example.russian.main.ui.draw.stats.comp.shape
import com.example.russian.main.ui.draw.test.testShowUnansweredState

@Composable
fun LargeBooleanButton(state: SimpleBooleanState, onClick: () -> Unit){
    Button(
        onClick = {
            onClick()
        },
        modifier = state.modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (state.show) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondaryContainer
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
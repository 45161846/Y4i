package com.example.russian.ui.draw.stats.comp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.russian.ui.draw.test.testShowUnansweredState
import com.example.russian.ui.theme.FiltersScreenButtonActive
import com.example.russian.ui.theme.SecondaryBackground

@Composable
fun ShowUnansweredView(state: UnansweredViewState, onClick: () -> Unit){
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (state.show) FiltersScreenButtonActive else SecondaryBackground
        )
    ) {
        MyFilterOptionText(text = "Показывать неотвеченные слова")
    }
}

data class UnansweredViewState(
    val show: Boolean
)

@Preview
@Composable
private fun Preview(){

    ShowUnansweredView(testShowUnansweredState()) { }
}
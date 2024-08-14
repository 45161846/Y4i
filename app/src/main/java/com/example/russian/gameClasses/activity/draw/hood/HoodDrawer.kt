package com.example.russian.gameClasses.activity.draw.hood

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.russian.architecture2.ui.state.HoodUIState
import com.example.russian.ui.theme.LightGreen
import com.example.russian.ui.theme.LightRed


class HoodDrawer {

    companion object{
        @Composable
        fun Hood(
            state: HoodUIState,
            modifier: Modifier
        ){
            when(state){
                is HoodUIState.NoTimer -> {
                    HoodNoTimer(
                        state,
                        modifier
                    )
                }
            }
        }

        @Composable
        private fun HoodNoTimer(
            state: HoodUIState.NoTimer,
            modifier: Modifier
        ){
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = modifier
                    .padding(vertical = 32.dp, horizontal = 32.dp)
            ) {
                Counter(text = "Правильно: ${state.correct}", backColor = LightGreen)
                Counter(text = "Ошибок: ${state.incorrect}", backColor = LightRed)
            }
        }
    }

}


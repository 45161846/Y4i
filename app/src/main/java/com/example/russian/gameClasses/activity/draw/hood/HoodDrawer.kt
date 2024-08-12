package com.example.russian.gameClasses.activity.draw.hood

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.russian.gameClasses.activity.draw.HoodUIState
import com.example.russian.ui.theme.LightGreen
import com.example.russian.ui.theme.LightRed
import com.example.russian.ui.theme.PrimaryBackground


class HoodDrawer {

    companion object{
        @Composable
        fun Hood(state: HoodUIState){
            when(state){
                is HoodUIState.NoTimer -> {
                    HoodNoTimer(state)
                }
            }
        }

        @Composable
        private fun HoodNoTimer(state: HoodUIState.NoTimer){
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(PrimaryBackground)
                    .padding(vertical = 32.dp, horizontal = 32.dp)
            ) {
                Counter(text = "Правильно: ${state.correct}", backColor = LightGreen)
                Counter(text = "Ошибок: ${state.incorrect}", backColor = LightRed)
            }
        }
    }

}


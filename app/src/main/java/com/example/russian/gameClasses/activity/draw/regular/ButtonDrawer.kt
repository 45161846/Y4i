package com.example.russian.gameClasses.activity.draw.regular

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.russian.architecture2.viewmodel.game.state.ButtonUIState
import com.example.russian.ui.theme.GameButtonTextStyle
import com.example.russian.ui.theme.PrimaryBackground

class ButtonDrawer {

    companion object{

        @Composable
        private fun MyButton(state: ButtonUIState){

            when(state){
                is ButtonUIState.InProgress -> Progress(state)
                is ButtonUIState.ShowAnswer -> Answer(state)
            }

        }

        @Composable
        private fun Answer(state: ButtonUIState.ShowAnswer) {
            DrawButton(text = state.text, backColor = state.backColor, borderColor = state.borderColor, state.onClick)
        }

        
        @Composable
        private fun Progress(state: ButtonUIState.InProgress) {
            DrawButton(text = state.text, backColor = state.backColor, borderColor = state.borderColor, state.onClick)
        }
        
        @Composable
        private fun DrawButton(text: String, backColor: Color, borderColor: Color, onClick: () -> Unit){
            
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = backColor,
                ),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(3.dp, borderColor),
                onClick = onClick
            ){
                Text(
                    modifier = Modifier
                        .padding(24.dp),
                    style = GameButtonTextStyle,
                    text = text,
                    color = borderColor
                )
            }
            
        }

        @Composable
        fun Buttons(states: List<ButtonUIState>){
            LazyColumn(
                userScrollEnabled = false,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(PrimaryBackground)
            ) {
                this.items(states.size){
                    MyButton(states[it])
                }
            }
        }
    }
}
package com.example.russian.main.ui.draw.settings

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.architectured.settings.SettingsScreen
import com.example.russian.architectured.settings.SettingsStatesHolder
import com.example.russian.architectured.todo.fakeSettingActions
import com.example.russian.architectured.todo.testSettingState
import com.example.russian.main.ui.draw.test.testStatsCardState
import com.example.russian.main.ui.theme.RussianTheme
import com.example.russian.main.ui.theme.family

@Composable
fun SettingsParagraph(
    name: String, content: @Composable
        () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp)

    ) {
        Text(
            name,
            fontFamily = family,
            fontSize = 16.sp,
            color = Color.White
        )
        Spacer(Modifier.fillMaxWidth().height(4.dp))

        content.invoke()
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SettingsPreview(
    paddingValues: PaddingValues = PaddingValues()
) {
    RussianTheme {
        SettingsScreen(
            fakeSettingActions,
            testSettingState,
            {},{}
        )
    }

}
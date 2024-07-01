package com.example.russian.mainScreenPackage.screenDrawers

import android.os.Handler
import android.os.Looper
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.russian.MyEnumClasses.MyFilterSettings
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.MyEnumClasses.defaultFilterSettings
import com.example.russian.R
import com.example.russian.ui.theme.family
import java.util.ArrayList


@Composable
fun DrawFilterScreen(
    filter: MyFilterSettings,
    onChangeFilterSettings: (fs: MyFilterSettings) -> Unit,
    navController: NavController,
    expandedState: Boolean = false
) {

    val backgroundColor = colorResource(id = R.color.dark_background)

    var clickable by remember {
        mutableStateOf(false)
    }

    var filterSettings by remember {
        mutableStateOf(filter)
    }

    //need this delay so enter animation could ended correctly. Otherwise navigates back too early
    Handler(Looper.getMainLooper()).postDelayed({ clickable = true }, 700L)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {

        DrawResetButton{
            filterSettings = defaultFilterSettings()
        }

        DrawDescriptionLine(
            description = stringResource(id = R.string.filter_screen_title1),
            expandedState = expandedState,
            content = {
                ThemesContent(filterSettings.topics)
            }
        )


        Spacer(modifier = Modifier.weight(1F))

        DrawBackButton(clickable) {
            onChangeFilterSettings(filterSettings)
            navController.navigateUp()
        }
    }
}

@Composable
fun DrawResetButton(
    onClick: () -> Unit
) {

    val buttonColor = colorResource(id = R.color.reset_button)
    val textColor = colorResource(id = R.color.dark_background_3)

    Button(

        modifier = Modifier
            .padding(vertical = 16.dp)
        ,

        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        )
    ) {
        Text(
            text = stringResource(id = R.string.filter_screen_reset_text),
            fontSize = 20.sp,
            fontFamily = family,
            color = textColor
        )
    }

}

@Composable
fun DrawDescriptionLine(
    description: String,
    content: @Composable () -> Unit,
    expandedState: Boolean = false
) {

    val spacerColor = colorResource(id = R.color.dark_background_3)
    val coloredSpacerWidth = 24.dp
    val transparentSpacerWidth = 16.dp
    val textColor = Color.White
    val textSize = 24.sp
    val fontFamily = FontFamily(
        Font(R.font.open_sans_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.open_sants_regular, FontWeight.Normal, FontStyle.Normal),
    )

    //button
    val shape = RoundedCornerShape(10.dp)
    val backColor = colorResource(id = R.color.dark_background_2)

    var expanded by remember { mutableStateOf(expandedState) }

    Column(
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
            .background(backColor, shape)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = ButtonDefaults.buttonColors(
                containerColor = backColor
            ),
            shape = shape,
            onClick = {
                expanded = !expanded
            }
        ) {
            Row() {
                //colored
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrow_right),
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp)
                        .rotate(if (expanded) 90F else 0F)
                )

                Text(
                    text = description,
                    color = textColor,
                    fontSize = textSize,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic
                )
                //transparent
                Spacer(
                    modifier = Modifier
                        .weight(1F)
                        .height(2.dp)
                        .background(Color.Transparent)
                )

            }
        }

        if (expanded) {
            content()
        }

    }

}

@Composable
private fun ThemesContent(
    chosenThemes: Array<Boolean>
) {

    val optionsFontSize = 18.sp
    val textColor = Color.White

    val themes = TaskTopic().getTopicIntToNameMap()

    val checkStates = remember {
        mutableStateListOf(*chosenThemes)
    }

    /*
    when recomposition is called due to reset of changes remember doesn't
    change value of itself, because it is a recomposition
    */
    chosenThemes.forEachIndexed() {i, b ->
        checkStates[i] = b
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        themes.forEach { element ->

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                        val b = chosenThemes[element.key]

                        checkStates[element.key] = !b
                        chosenThemes[element.key] = !b
                    }
            ) {
                Checkbox(
                    checked = checkStates[element.key],
                    onCheckedChange = { b ->
                        checkStates[element.key] = b
                        chosenThemes[element.key] = b
                    }
                )
                Text(
                    text = element.value,
                    fontSize = optionsFontSize,
                    fontFamily = family,
                    color = textColor
                )
            }


        }

    }
}

@Composable
fun DrawBackButton(
    clickable: Boolean,
    onClick: () -> Unit
) {

    val buttonColor = colorResource(id = R.color.save_button)
    val textColor = colorResource(id = R.color.dark_background_3)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        Button(
            onClick = if (clickable) onClick else {
                {}
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor
            )
        ) {
            Text(
                text = stringResource(id = R.string.filter_screen_save_button_text),
                fontSize = 20.sp,
                fontFamily = family,
                color = textColor
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    val fs = defaultFilterSettings()
    DrawFilterScreen(
        filter = defaultFilterSettings(),
        {},
        rememberNavController(),
        expandedState = true
    )
}
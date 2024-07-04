package com.example.russian.mainScreenPackage.screenDrawers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.MyEnumClasses.StateOfFocus
import com.example.russian.R
import com.example.russian.mainScreenPackage.BottomNavigationItem
import com.example.russian.mainScreenPackage.ScreenTypePractice
import com.example.russian.mainScreenPackage.ScreenTypeSettings
import com.example.russian.mainScreenPackage.ScreenTypeStats
import com.example.russian.ui.theme.FiltersScreenButtonActive
import com.example.russian.ui.theme.OnSecondary1
import com.example.russian.ui.theme.OnSecondary2
import com.example.russian.ui.theme.SecondaryBackground
import com.example.russian.ui.theme.family

@Composable
fun DrawTopBar(
    selectedItemIndex: Int,
    onSearch: (pref: String) -> Unit,
    onFilterClick: () -> Unit
) {
    if(selectedItemIndex == 2){
        DrawSearchFilterRow(
            onSearch = onSearch,
            onFilterClick = onFilterClick
        )
    }

}
@Composable
fun DrawToTopButton(
    listState: LazyListState,
    selectedItemIndex: Int,
    onClick: () -> Unit
) {

    if(selectedItemIndex == 2){

        val showButton by remember{
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            FloatingActionButton(
                onClick = { onClick() },
                modifier = Modifier.myToTopButton(),
                containerColor = colorResource(id = R.color.dark_background_2),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_up),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.light_background)),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun DrawNavigationBarBottom(
    selectedItemIndex: Int,
    changeSelectedItemIndexTo: (newIndex: Int) -> Unit
){

    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            type_of_screen = ScreenTypeSettings,
            selectedImage = ImageVector.vectorResource(id = R.drawable.settings_filled),
            unselectedImage = ImageVector.vectorResource(id = R.drawable.settings_unfilled)
        ),
        BottomNavigationItem(
            type_of_screen = ScreenTypePractice,
            selectedImage = ImageVector.vectorResource(id = R.drawable.brain_outlined),
            unselectedImage = ImageVector.vectorResource(id = R.drawable.brain_black)
        ),
        BottomNavigationItem(
            type_of_screen = ScreenTypeStats,
            selectedImage = ImageVector.vectorResource(id = R.drawable.statistics_colored),
            unselectedImage = ImageVector.vectorResource(id = R.drawable.statistics_black)
        ),
    )

    val navColor = colorResource(id = R.color.dark_background_2)
    NavigationBar(
        containerColor = navColor,
        modifier = Modifier
            .height(50.dp)
            //.padding(5.dp, 0.dp, 5.dp, 5.dp)
            .clip(RoundedCornerShape(40.dp, 40.dp, 0.dp, 0.dp))
    ) {
        bottomNavigationItems.forEachIndexed{index, item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = colorResource(id = R.color.dark_background_3)
                    //indicatorColor = Color.Transparent
                ),
                selected = selectedItemIndex == index,
                onClick = {
                    if(selectedItemIndex != index) {
                        changeSelectedItemIndexTo(index)
                    }
                },
                icon = {
                    val currentIcon = if(index == selectedItemIndex){
                        item.selectedImage
                    }else{
                        item.unselectedImage
                    }
                    Box (
                        modifier = Modifier
                            .size(90.dp, 35.dp)
                            .background(Color.Transparent)
                    ){
                        Image(imageVector = currentIcon,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()

                        )
                    }
                })
        }
    }

}

@Composable
private fun DrawSearchFilterRow(
    onSearch: (pref: String) -> Unit,
    onFilterClick: () -> Unit
){

    val darkColor = OnSecondary2
    val backColor = SecondaryBackground


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                backColor,
                RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp)
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        var text by rememberSaveable {
            mutableStateOf(String())
        }

        var focusState by rememberSaveable {
            mutableStateOf(StateOfFocus.EXIT)
        }

        val focusManager = LocalFocusManager.current
        val style = TextStyle(fontFamily = family)

        var hideKeyboard  by remember { mutableStateOf(false) }
        TextField(
            onValueChange = {
                text = it
                onSearch(text)
            },
            value = text,
            colors = searchFieldColors(),
            modifier = Modifier
                .weight(1F)
                .onFocusEvent {
                    if (it.hasFocus && focusState != StateOfFocus.SEARCH) {
                        focusState = StateOfFocus.SEARCH
                        text = String()
                    }
                }
                .border(
                    2.dp,
                    darkColor,
                    RoundedCornerShape(100)
                )
            ,
            placeholder = {
                          HintText()
                          },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusState = if(text.isEmpty()){
                        StateOfFocus.EXIT
                    }else {
                        StateOfFocus.SEARCH
                    }
                    focusManager.clearFocus()
                }
            ),
            textStyle = TextStyle.Default.copy(
                fontSize = 20.sp,
                fontFamily = family
            ),

            trailingIcon = {

                IconButton(onClick = {
                    focusState = StateOfFocus.EXIT
                    text = String()
                    onSearch(String())
                    focusManager.clearFocus()
                }) {
                    Image(
                        modifier = Modifier
                            .size(28.dp),
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(darkColor)
                    )
                }

            }
        )

        if(hideKeyboard){
            focusManager.clearFocus()
            hideKeyboard = false
        }

        DrawFilterButton(onFilterClick)
    }
}

@Composable
private fun HintText(){
    Text(
        text = "Поиск",
        fontFamily = family,
        fontSize = 20.sp,
        color = OnSecondary2
    )
}

@Composable
private fun DrawFilterButton(
    onFilterClick: () -> Unit
){
    val darkColor = OnSecondary2


    IconButton(
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent
        ),
        onClick = onFilterClick
    ){
        Image(
            modifier = Modifier
                .size(28.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.filter_icon),
            contentDescription = "filter_button",
            colorFilter = ColorFilter.tint(darkColor)
        )
    }
}

@Composable
fun AnimatedSettingsLine(
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
private fun searchFieldColors(): TextFieldColors {
    val darkColor = OnSecondary2

    return TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedTextColor = darkColor,
        disabledTextColor = darkColor,
        errorTextColor = darkColor,
        unfocusedTextColor = darkColor,
        cursorColor = FiltersScreenButtonActive
    )
}
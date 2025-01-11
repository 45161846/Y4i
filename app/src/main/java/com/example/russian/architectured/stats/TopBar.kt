package com.example.russian.architectured.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.R
import com.example.russian.main.enums.StateOfFocus
import com.example.russian.main.ui.theme.TransparentWhite
import com.example.russian.main.ui.theme.family

@Composable
fun SearchFilterRow(
    onSearch: (pref: String) -> Unit, onFilterClick: () -> Unit
) {

    val elementsColor = MaterialTheme.colorScheme.onSecondary
    val backColor = MaterialTheme.colorScheme.secondary


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                backColor, RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp)
            )
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 40.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var text by rememberSaveable {
            mutableStateOf(String())
        }

        var focusState by rememberSaveable {
            mutableStateOf(StateOfFocus.EXIT)
        }

        val focusManager = LocalFocusManager.current

        var hideKeyboard by remember { mutableStateOf(false) }
        TextField(onValueChange = {
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
                    2.dp, elementsColor, RoundedCornerShape(100)
                ),
            placeholder = {
                HintText(TransparentWhite)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                focusState = if (text.isEmpty()) {
                    StateOfFocus.EXIT
                } else {
                    StateOfFocus.SEARCH
                }
                focusManager.clearFocus()
            }),
            textStyle = TextStyle.Default.copy(
                fontSize = 20.sp, fontFamily = family, color = MaterialTheme.colorScheme.onPrimary
            ),

            trailingIcon = {

                IconButton(onClick = {
                    focusState = StateOfFocus.EXIT
                    text = String()
                    onSearch(String())
                    focusManager.clearFocus()
                }) {
                    Image(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(elementsColor)
                    )
                }

            })

        if (hideKeyboard) {
            focusManager.clearFocus()
            hideKeyboard = false
        }

        DrawFilterButton(onFilterClick)
    }
}

@Composable
private fun HintText(
    hintColor: Color
) {
    Text(
        text = "Поиск", fontFamily = family, fontSize = 20.sp, color = hintColor
    )
}

@Composable
private fun DrawFilterButton(
    onFilterClick: () -> Unit
) {
    val darkColor = MaterialTheme.colorScheme.onSecondary

    IconButton(
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent
        ), onClick = onFilterClick
    ) {
        Image(
            modifier = Modifier.size(28.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.filter_icon),
            contentDescription = "filter_button",
            colorFilter = ColorFilter.tint(darkColor)
        )
    }
}

@Composable
private fun searchFieldColors(): TextFieldColors {
    val textColor = MaterialTheme.colorScheme.onSecondary
    val cursorColor = TransparentWhite

    return TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedTextColor = textColor,
        disabledTextColor = textColor,
        errorTextColor = textColor,
        unfocusedTextColor = textColor,
        cursorColor = cursorColor
    )
}
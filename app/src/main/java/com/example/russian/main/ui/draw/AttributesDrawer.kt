package com.example.russian.main.ui.draw

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.R
import com.example.russian.main.enums.StateOfFocus
import com.example.russian.main.ui.modifier.myToTopButton
import com.example.russian.main.ui.theme.TransparentWhite
import com.example.russian.main.ui.theme.family

@Composable
fun DrawTopBar(
    onSearch: (pref: String) -> Unit, onFilterClick: () -> Unit
) {
    DrawSearchFilterRow(
        onSearch = onSearch, onFilterClick = onFilterClick
    )
}

@Composable
fun PracTopBar(
    modifier: Modifier = Modifier,
    onLocalClick: () -> Unit, onRemoteClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    var source: Source by rememberSaveable(
        stateSaver = Saver<Source, Boolean>(
            save = {
                it is Source.Local
            },
            restore = {
                if(it){
                    Source.Local
                }else{
                    Source.Remote
                }
            }
        )
    ) {
        mutableStateOf(Source.Local)
    }

    var startLocal by remember {
        mutableIntStateOf(0)
    }

    var startRemote by remember {
        mutableIntStateOf(0)
    }

    var localLength by remember {
        mutableStateOf<Int?>(null)
    }
    var remoteLength by remember {
        mutableStateOf<Int?>(null)
    }
    var lineLength by remember {
        mutableIntStateOf(0)
    }

    val offset by animateIntOffsetAsState(
        targetValue = if (source is Source.Remote) {
            IntOffset(startRemote, 0)
        } else {
            IntOffset(startLocal, 0)
        }, label = "offset"
    )


    Column(
        modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), verticalArrangement = Arrangement.Center
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(modifier = Modifier
                .weight(1F)
                .padding(horizontal = 24.dp)
                .background(Color.Transparent)
                .clickable(
                    interactionSource = interactionSource, indication = null
                ) {
                    onLocalClick()
                    source = Source.Local
                    lineLength = localLength ?: 0
                }) {
                Text(
                    "Local",
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coord ->
                            startLocal = coord.positionInRoot().x.toInt()
                            if (localLength == null) {
                                lineLength = coord.size.width
                                localLength = coord.size.width
                            }
                            localLength = coord.size.width
                        },
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Box(modifier = Modifier
                .weight(1F)
                .padding(horizontal = 24.dp)
                .background(Color.Transparent)
                .clickable(
                    interactionSource = interactionSource, indication = null
                ) {
                    onRemoteClick()
                    source = Source.Remote
                    remoteLength?.let {
                        lineLength = it
                    } ?: 0
                }) {
                Text(
                    "Remote",
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coord ->
                            startRemote = coord.positionInRoot().x.toInt()
                            remoteLength = coord.size.width
                        },
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }


        Spacer(
            Modifier
                .offset {
                    offset
                }
                .animateContentSize()
                .size(
                    width = (lineLength.toFloat() / LocalDensity.current.density).dp, height = 5.dp
                )
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(100)))

    }


}

sealed class Source {
    data object Local : Source()
    data object Remote : Source()
}

@Composable
fun DrawToTopButton(
    listState: LazyListState, onClick: () -> Unit
) {

    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 10
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
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary),
                modifier = Modifier.size(32.dp)
            )
        }

    }
}


@Composable
private fun DrawSearchFilterRow(
    onSearch: (pref: String) -> Unit, onFilterClick: () -> Unit
) {

    val darkColor = MaterialTheme.colorScheme.onSecondary
    val backColor = MaterialTheme.colorScheme.secondaryContainer


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                backColor, RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp)
            )
            .padding(8.dp),
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
                    2.dp, darkColor, RoundedCornerShape(100)
                ),
            placeholder = {
                HintText()
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
                fontSize = 20.sp, fontFamily = family
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
                        colorFilter = ColorFilter.tint(darkColor)
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
private fun HintText() {
    Text(
        text = "Поиск", fontFamily = family, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSecondary
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
    val darkColor = MaterialTheme.colorScheme.onSecondary

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
        cursorColor = TransparentWhite
    )
}
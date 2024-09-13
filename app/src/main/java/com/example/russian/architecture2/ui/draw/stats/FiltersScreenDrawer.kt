package com.example.russian.architecture2.ui.draw.stats

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.russian.MyEnumClasses.FilterAPI
import com.example.russian.MyEnumClasses.MyFilterSettingsArch
import com.example.russian.MyEnumClasses.SortType
import com.example.russian.MyEnumClasses.SortTypeMode
import com.example.russian.MyEnumClasses.SortTypesEnum
import com.example.russian.MyEnumClasses.TaskTopic
import com.example.russian.MyEnumClasses.changeSortTypesAfterClickOn
import com.example.russian.MyEnumClasses.defaultFilterSettings
import com.example.russian.MyEnumClasses.getDisplayableName
import com.example.russian.MyEnumClasses.getSortTypeModes
import com.example.russian.MyEnumClasses.nextMode
import com.example.russian.R
import com.example.russian.architecture2.backend.data.entity.playlist.Playlist
import com.example.russian.architecture2.ui.state.FilterSettingData
import com.example.russian.architecture2.ui.state.FilterState
import com.example.russian.architecture2.ui.state.MarkedPlaylist
import com.example.russian.ui.theme.FiltersScreenButtonActive
import com.example.russian.ui.theme.OnSecondary2
import com.example.russian.ui.theme.OnSecondary3
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.SecondaryBackground
import com.example.russian.ui.theme.family
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


val shape = RoundedCornerShape(10.dp)

//@Composable
//fun DrawFilterScreen(
//    state: State<FilterState>,
//    navController: NavController,
//    api: FilterAPI
//) {
//    val backgroundColor = PrimaryBackground
//
//    var clickable by remember {
//        mutableStateOf(false)
//    }
//
//    var playlists = api.defaultPlaylists()
//    val sortTypes = api.sortTypes()
//    var sortType = api.defaultSortType()
//    var showUnanswered = api.defaultShowUnanswered()
//
//    when (val info = state.value) {
//        is FilterState.Default -> {
//            playlists = api.defaultPlaylists()
//            sortType = api.defaultSortType()
//            showUnanswered = api.defaultShowUnanswered()
//        }
//
//        is FilterState.Custom -> {
//            playlists = info.filterData.playlists
//            sortType = info.filterData.sortType
//            showUnanswered = info.filterData.showUnanswered
//        }
//    }
//
//    //need this delay so enter animation could ended correctly. Otherwise navigates back too early
//    Handler(Looper.getMainLooper()).postDelayed({ clickable = true }, 700L)
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxSize()
//            .background(backgroundColor)
//            .padding(16.dp)
//    ) {
//
//        DrawResetButton {
//            playlists = api.defaultPlaylists()
//            sortType = api.defaultSortType()
//            showUnanswered = api.defaultShowUnanswered()
//        }
//
//        FilterParagraph(title = stringResource(id = R.string.filter_screen_title1),
//            content = {
//                ThemesContent(playlists) { ind ->
//                    playlists[ind].marked = playlists[ind].marked.not()
//                }
//            }
//        )
//        Spacer(
//            modifier = Modifier
//                .size(16.dp)
//        )
//        FilterParagraph(title = "Сортировать по", content = {
//            SortContent(sortVariants = sortTypes) { ind ->
//                changeSortTypesAfterClickOn(sortTypes, ind)
//                sortTypes.forEach {
//                    if (it.mode != SortTypeMode.UNSPECIFIED) {
//                        sortType = it
//                    }
//                }
//            }
//        })
//
//        Spacer(
//            modifier = Modifier
//                .size(16.dp)
//        )
//
//        ShowUnanswered(showUnanswered) { b ->
//            showUnanswered = b.not()
//        }
//
//        Spacer(modifier = Modifier.weight(1F))
//
//        DrawBackButton(clickable) {
//            api.save(
//                FilterSettingData(
//                    playlists,
//                    sortType,
//                    showUnanswered
//                )
//            )
//            navController.navigateUp()
//        }
//    }
//}

@Composable
fun DrawResetButton(
    onClick: () -> Unit
) {

    val buttonColor = colorResource(id = R.color.reset_button)
    val textColor = SecondaryBackground

    Button(
        modifier = Modifier
            .padding(bottom = 16.dp),

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
fun FilterParagraph(
    title: String,
    content: @Composable () -> Unit
) {
    val backColor = SecondaryBackground

    Column(
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
            .background(backColor, shape)
    ) {
        TitleText(title = title)
        content()
    }
}

@Composable
fun TitleText(title: String) {

    val textColor = OnSecondary2
    val textSize = 24.sp

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Spacer(
            modifier = Modifier
                .weight(1F)
        )

        Text(
            text = title,
            color = textColor,
            fontSize = textSize,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        )
        Spacer(
            modifier = Modifier
                .weight(1F)
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(OnSecondary3)
            .padding(horizontal = 10.dp)
    )
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemesContent(
    playlistsList: List<MarkedPlaylist>,
    onClick: (Int) -> Unit
) {

    val playlists = remember {
        mutableStateListOf(*playlistsList.toTypedArray())
    }

    /*
    when recomposition is called due to reset of changes remember doesn't
    change value of itself, because it is a recomposition
    */
    playlistsList.forEachIndexed { i, b ->
        playlists[i] = b
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp)
    ) {

        playlists.forEachIndexed { ind, playlist ->

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        onClick(ind)
                        playlist.marked = playlist.marked.not()
                    }
            ) {

                MyOptionsText(text = playlist.playlist.title)

                Spacer(modifier = Modifier.weight(1F))
                CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                    Checkbox(
                        modifier = Modifier.scale(1.2F),
                        checked = playlist.marked,
                        onCheckedChange = {
                            onClick(ind)
                            playlist.marked = playlist.marked.not()
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = FiltersScreenButtonActive
                        )
                    )
                }

            }

        }
        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Composable
fun MyOptionsText(text: String) {

    val optionsFontSize = 18.sp
    val textColor = Color.White

    Text(
        text = text,
        fontSize = optionsFontSize,
        fontFamily = family,
        color = textColor
    )
}


@Composable
private fun SortContent(
    sortVariants: Array<SortType>,
    onClick: (Int) -> Unit
) {

    val buttonModes = remember {
        mutableStateListOf(*getSortTypeModes(sortVariants.toList()))
    }

    buttonModes.forEachIndexed{i, _ ->
        buttonModes[i] = sortVariants[i].mode
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        sortVariants.forEachIndexed { ind, it ->
            SortButton(
                text = getDisplayableName(it.type),
                thisSortType = SortType(it.type, buttonModes[ind]),
                onClick = {
                    onClick(ind)
                    changeSortTypesAfterClickOn(sortVariants, ind)
                    for (i in buttonModes.indices){
                        buttonModes[i] = sortVariants[i].mode
                    }
                },
                isLast = sortVariants.lastIndex == ind
            )
        }
    }

}

@Composable
private fun SortButton(
    text: String,
    thisSortType: SortType,
    onClick: () -> Unit,
    isLast: Boolean = false
) {

    val imageVector = ImageVector.vectorResource(id = R.drawable.arrow_right)

    var mode by remember {
        mutableStateOf(thisSortType.mode)
    }

    val backColor = when (mode) {
        SortTypeMode.UNSPECIFIED -> {
            Color.Transparent
        }

        SortTypeMode.DIRECT -> {
            FiltersScreenButtonActive
        }

        SortTypeMode.REVERSED -> {
            FiltersScreenButtonActive
        }
    }

    Button(
        onClick = {
            onClick()
            mode = nextMode(mode)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = backColor
        ),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 12.dp, end = 12.dp,
                bottom = if (isLast) 12.dp else 0.dp
            )
    ) {

        MyOptionsText(text = text)

        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier
                .rotate(
                    if (mode == SortTypeMode.DIRECT) 90F else if (mode == SortTypeMode.REVERSED) -90F else 0F
                )
        )
    }
}

@Composable
private fun ShowUnanswered(
    show: Boolean,
    onClick: (Boolean) -> Unit
) {

    var checked by remember {
        mutableStateOf(show)
    }

    Button(
        onClick = {
            onClick(checked)
            checked = checked.not()
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (checked) FiltersScreenButtonActive else SecondaryBackground
        )
    ) {
        MyOptionsText(text = "Показывать неотвеченные слова")
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

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun Preview() {
    val state = mutableStateOf(FilterState.Default)

//    DrawFilterScreen(
//        state,
//        rememberNavController(),
//        object : FilterAPI {
//            override fun save(filter: FilterSettingData) {
//
//            }
//
//            override fun defaultPlaylists(): List<MarkedPlaylist> {
//
//                val playlists = listOf(
//                    Playlist(title = "Наречия", capacity = 20)
//                )
//                return playlists.map {
//                    MarkedPlaylist(it, true)
//                }
//            }
//
//            override fun sortTypes(): Array<SortType> {
//                return arrayOf(
//                    SortType(
//                        SortTypesEnum.ALPHABETICAL,
//                        SortTypeMode.DIRECT
//                    ),
//                    SortType(
//                        SortTypesEnum.WIN_RATE,
//                        SortTypeMode.UNSPECIFIED
//                    )
//                )
//            }
//
//            override fun defaultSortType(): SortType {
//                return SortType(
//                    SortTypesEnum.ALPHABETICAL,
//                    SortTypeMode.DIRECT
//                )
//            }
//
//            override fun defaultShowUnanswered(): Boolean {
//                return true
//            }
//        }
//    )
}
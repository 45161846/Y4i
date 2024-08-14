package com.example.russian.mainScreenPackage.screenDrawers.stats

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import com.example.russian.MyEnumClasses.ExceptionsTexts
import com.example.russian.R
import com.example.russian.architecture.data.olddata.entity.Word
import com.example.russian.mainScreenPackage.repository.WordsLocalMainScreenRepository
import com.example.russian.toolPackage.WordToTaskMapper
import com.example.russian.ui.theme.OnSecondary2
import com.example.russian.ui.theme.ThirdBackground
import com.example.russian.ui.theme.family
import kotlinx.coroutines.flow.StateFlow

@Composable
private fun DrawLoading(paddingValues: PaddingValues) { //TODO add shimmer
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = myModifier(paddingValues = paddingValues)
    ) {
        Text(
            text = "loading...",
            color = colorResource(id = R.color.light_background),
            fontSize = 30.sp
        )
    }
}

@Composable
fun DrawNoWordsFound(paddingValues: PaddingValues) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = myModifier(paddingValues = paddingValues)
    ) {
        Text(
            text = ExceptionsTexts().NO_WORDS_FOUND(),
            color = colorResource(id = R.color.light_background),
            fontSize = 30.sp
        )
    }
}


@Composable
fun DrawStatsContent(
    repo: WordsLocalMainScreenRepository,
    owner: LifecycleOwner?,
    paddingValues: PaddingValues,
    listState: LazyListState
) {

    var listOfWords by remember {
        mutableStateOf(emptyList<Word>())
    }

    var found by remember {
        mutableStateOf(true)
    }


    //can only be null in test repository for preview
    owner?.let { own ->
        repo.currentWords.observe(own) { list ->
            listOfWords = list
        }
        repo.hasWordsAfterSearch.observe(own) {
            found = it
        }
    } ?: run {
        listOfWords = repo.allWords
        found = true

    }

    if (!found) {
        DrawNoWordsFound(paddingValues = paddingValues)
        return
    }

    if (listOfWords.isEmpty() || repo.isLoadingInProcess) {
        DrawLoading(paddingValues)
    } else {
        DrawNormal(listOfWords, paddingValues, listState)
    }
}

@Composable
fun DrawContentNoRepo(
    contentListState: State<List<Word>>,
    paddingValues: PaddingValues,
    listState: LazyListState
){
    if(contentListState.value.isEmpty()){
        DrawNoWordsFound(paddingValues = paddingValues)
        return
    }

    DrawNormal(
        listOfWords = contentListState.value,
        paddingValues = paddingValues,
        listState = listState
    )
}

@Composable
private fun DrawNormal(
    listOfWords: List<Word>,
    paddingValues: PaddingValues,
    listState: LazyListState
) {

    LazyColumn(
        modifier = myModifier(paddingValues),
        state = listState
    ) {
        items(
            items = listOfWords,
            key = {
                it.id
            }
        ) {
            CardOfStats(w = it)
        }
    }
}

@Composable
private fun myModifier(paddingValues: PaddingValues): Modifier {
    return Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.dark_background))
        .padding(paddingValues)
}

@SuppressLint("DefaultLocale")
@Composable
private fun CardOfStats(w: Word) {

    val displayableText = w.displayableText
    val winRate = (w.gotItRight.toFloat() / w.attempts)

    val backColor = ThirdBackground

    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp, 3.dp, 8.dp, 3.dp)
            .background(
                backColor,
                RoundedCornerShape(5.dp)
            )
    ) {
        Text(
            text = displayableText,
            fontSize = 20.sp,
            color = OnSecondary2,
            fontFamily = family,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp)
                .width(200.dp)
        )

        Spacer(
            modifier = Modifier
                .weight(1F)
        )

        if (winRate >= 0) {
            Text(
                color = calculateColor(winRate),
                text = (winRate * 100).toInt().toString() + "%",
                modifier = Modifier
                    .padding(10.dp, 0.dp)
            )

        }

        Spacer(
            modifier = Modifier
                .background(calculateColor(winRate), RoundedCornerShape(100))
                .size(70.dp, 5.dp)
        )
        Spacer(
            modifier = Modifier
                .background(Color.Transparent)
                .size(10.dp, 2.dp)
        )

    }
}

private fun calculateColor(winRate: Float): Color {
    if (winRate < 0) {
        return Color(152, 152, 152)
    }
    return Color((1F - winRate) * 2, winRate * 2, 0F, alpha = 1F)
}
package com.example.russian.main.custom

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle

@Composable
fun CustomOverflowText(
    text: String,
    style: TextStyle,
    overflowSuffix: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
){

    var l by remember {
        mutableIntStateOf(0)
    }
    var r by remember {
        mutableIntStateOf(text.length)
    }
    val mid by remember (l, r){
        mutableIntStateOf((l + r) / 2)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val shortText by remember(mid) {
        mutableStateOf(
            if(mid < text.length) text.subSequence(0 until mid).toString() + overflowSuffix
            else text
        )
    }
    val displayableText by remember(expanded, shortText) {
        mutableStateOf(
            if(expanded) text
            else shortText
        )
    }


    Text(
        text = displayableText,
        style = style.copy(
                //Чтобы пока бин-поиск работает ничего не показывало
//            color = if(mid != r) Color.Transparent else style.color
        ),
        modifier = modifier
            .animateContentSize()
            .clickable(
                interactionSource = null, indication = null
            ){
                expanded = expanded.not()
            },
        onTextLayout = { textLayoutResult: TextLayoutResult ->
            if (!expanded && textLayoutResult.lineCount > maxLines - 1) {
                if (textLayoutResult.isLineEllipsized(maxLines - 1) || textLayoutResult.lineCount > maxLines){
                    r = mid - 1
                }else{
                    l = mid + 1
                }
            }
        }
    )
}
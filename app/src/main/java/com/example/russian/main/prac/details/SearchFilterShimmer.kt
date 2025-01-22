package com.example.russian.main.prac.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.R
import com.example.russian.main.theme.RussianTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun SearchFilterShimmer(
    modifier: Modifier = Modifier
) {

    val elementsColor = MaterialTheme.colorScheme.onSecondary

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondary
            )
            .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
            .shimmer(),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val focusManager = LocalFocusManager.current
        TextField(
            onValueChange = {},
            value = "t",
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Transparent
            ),
            modifier = Modifier
                .weight(1F)
                .background(
                    MaterialTheme.colorScheme.secondary,
                )
                .clip(
                    RoundedCornerShape(100)
                )
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.onSecondary,
                    RoundedCornerShape(100)
                )
                .onFocusEvent {
                    focusManager.clearFocus()
                },
            trailingIcon = {
                Image(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Outlined.Close,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(elementsColor)
                )
            }
        )

        val darkColor = MaterialTheme.colorScheme.onSecondary

        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent
            ), onClick = {}
        ) {
            Image(
                modifier = Modifier.size(28.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.filter_icon),
                contentDescription = "filter_button",
                colorFilter = ColorFilter.tint(darkColor)
            )
        }

    }

}

@Preview
@Composable
private fun PreviewSearchFilterShimmer() {
    RussianTheme {
        SearchFilterShimmer()
    }
}
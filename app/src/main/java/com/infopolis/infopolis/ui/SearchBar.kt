package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchBar(
    input: String,
    onChange: (String) -> Unit,
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember {
        FocusRequester()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                ),
                shape = CircleShape
            )
            .shadow(
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.secondary,
                elevation = if (isFocused) 15.dp else 0.dp,
                clip = true,
                shape = CircleShape
            ),
        shape = CircleShape
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(color = MaterialTheme.colorScheme.onPrimary)
        ) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(35.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon",
                tint = MaterialTheme.colorScheme.primary,
            )

            BasicTextField(
                value = input,
                onValueChange = onChange,
                interactionSource = interactionSource,
                textStyle = TextStyle(
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 20.sp
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSecondary),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .offset(y = 10.dp)
                    .focusRequester(focusRequester),
            )
        }
    }
}